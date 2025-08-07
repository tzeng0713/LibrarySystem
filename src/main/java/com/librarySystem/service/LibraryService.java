package com.librarySystem.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.librarySystem.dto.BookDisplayDTO;
import com.librarySystem.dto.BorrowingRecordDTO;
import com.librarySystem.entity.Book;
import com.librarySystem.entity.BorrowingRecord;
import com.librarySystem.entity.Inventory;
import com.librarySystem.entity.User;
import com.librarySystem.repository.BookRepository;
import com.librarySystem.repository.BorrowingRecordRepository;
import com.librarySystem.repository.InventoryRepository;
import com.librarySystem.repository.UserRepository;

@Service
public class LibraryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;

    /**
     * 借書功能：
     * 1. 檢查該庫存是否存在且可借閱（狀態為 available）
     * 2. 檢查使用者是否存在
     * 3. 檢查是否已有未歸還的借閱紀錄
     * 4. 更新庫存狀態為 borrowed
     * 5. 新增一筆借閱紀錄
     */
    @Transactional
    public void borrowBook(Integer userId, String isbn) {
    	// 查詢第一筆可借的庫存
        Inventory inventory = inventoryRepository
                .findFirstByBookIsbnAndStatus(isbn, Inventory.Status.available);

        if (inventory == null) {
            throw new RuntimeException("目前無可借閱的庫存");
        }

        // 查詢使用者
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("查無使用者");
        }

        // 檢查是否已有尚未歸還的紀錄
        boolean hasUnreturnedSameBook = borrowingRecordRepository
                .existsByUser_UserIdAndInventory_Book_IsbnAndReturnTimeIsNull(userId, isbn);

        if (hasUnreturnedSameBook) {
            throw new RuntimeException("你已借閱過此書，尚未歸還");
        }


        // 更新庫存狀態為 borrowed
        inventory.setStatus(Inventory.Status.borrowed);
        inventoryRepository.save(inventory);

        // 新增借閱紀錄
        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setInventory(inventory);
        record.setBorrowingTime(LocalDateTime.now());
        borrowingRecordRepository.save(record);
    }

    /**
     * 還書功能：
     * 1. 查詢使用者與庫存
     * 2. 查詢尚未歸還的借閱紀錄
     * 3. 更新歸還時間
     * 4. 將書籍狀態更新為 processing（整理中）
     */
    @Transactional
    public void returnBook(Integer userId, Integer inventoryId) {
        // 查詢使用者
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("查無使用者");
        }

        // 查詢庫存
        Inventory inventory = inventoryRepository.findById(inventoryId).orElse(null);
        if (inventory == null) {
            throw new RuntimeException("查無該書庫存");
        }

        // 查詢尚未歸還的借閱紀錄
        List<BorrowingRecord> records = borrowingRecordRepository
                .findByUserAndInventoryAndReturnTimeIsNull(user, inventory);
        if (records.isEmpty()) {
            throw new RuntimeException("你沒有借閱這本書或已經歸還");
        }

        BorrowingRecord record = records.get(0); // 理論上應該只有一筆

        // 更新紀錄
        record.setReturnTime(LocalDateTime.now());
        borrowingRecordRepository.save(record);

        // 書籍狀態改為整理中
        inventory.setStatus(Inventory.Status.processing);
        inventoryRepository.save(inventory);
    }
    
    // 將書本資料整理給前端畫面顯示（書籍一覽）
    public List<BookDisplayDTO> getAllBooksForDisplay() {
        List<Book> books = bookRepository.findAll();
        List<BookDisplayDTO> result = new ArrayList<>();

        for (Book book : books) {
            String isbn = book.getIsbn();

            // 查詢此書的 available 數量
            int availableCount = inventoryRepository.countByBookIsbnAndStatus(isbn, Inventory.Status.available);

            // 組出狀態文字
            String statusText = availableCount > 0 ? "借閱" : "無庫存";

            // 包裝成 DTO
            BookDisplayDTO dto = new BookDisplayDTO();
            dto.setIsbn(isbn);
            dto.setName(book.getName());
            dto.setAuthor(book.getAuthor());
            dto.setIntroduction(book.getIntroduction());
            dto.setAvailableCount(availableCount);
            dto.setStatusText(statusText);

            result.add(dto);
        }

        return result;
    }
    
    // 查詢使用者借閱紀錄
    public List<BorrowingRecordDTO> findRecordsByUserId(Integer userId) {
        List<Map<String, Object>> records = borrowingRecordRepository.findRawRecordsByUserId(userId);

        List<BorrowingRecordDTO> result = new ArrayList<>();

        for (Map<String, Object> row : records) {
            String bookName = (String) row.get("book_name");

            // 時間格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 轉成字串格式
            String borrowingTimeStr = ((Timestamp) row.get("borrowing_time"))
                                          .toLocalDateTime()
                                          .format(formatter);

            String returnTimeStr = null;
            if (row.get("return_time") != null) {
                returnTimeStr = ((Timestamp) row.get("return_time"))
                                    .toLocalDateTime()
                                    .format(formatter);
            }

            Integer inventoryId = ((Number) row.get("inventory_id")).intValue();

            // 放進 DTO
            BorrowingRecordDTO dto = new BorrowingRecordDTO(bookName, borrowingTimeStr, returnTimeStr, inventoryId);
            result.add(dto);
        }

        return result;
    }
}
