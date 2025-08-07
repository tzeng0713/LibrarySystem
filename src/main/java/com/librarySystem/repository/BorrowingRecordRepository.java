package com.librarySystem.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.librarySystem.dto.BorrowingRecordDTO;
import com.librarySystem.entity.BorrowingRecord;
import com.librarySystem.entity.Inventory;
import com.librarySystem.entity.User;

import io.lettuce.core.dynamic.annotation.Param;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

    // 查詢使用者所有借閱紀錄（用於「借閱紀錄」頁面）
    @Query(value = """
    	    SELECT b.name AS book_name, br.borrowing_time, br.return_time, br.inventory_id
    	    FROM borrowing_record br
    	    JOIN inventory i ON br.inventory_id = i.inventory_id
    	    JOIN book b ON i.isbn = b.isbn
    	    WHERE br.user_id = :userId
    	    ORDER BY br.borrowing_time DESC
    	""", nativeQuery = true)
    	List<Map<String, Object>> findRawRecordsByUserId(@Param("userId") Integer userId);
    
    // 查詢使用者是否借了「這一筆庫存」，且尚未歸還（用於還書功能）
    List<BorrowingRecord> findByUserAndInventoryAndReturnTimeIsNull(User user, Inventory inventory);

    // 查詢使用者是否已借過該 ISBN 書籍，且尚未歸還（用於借書功能，避免重複借書）
    boolean existsByUser_UserIdAndInventory_Book_IsbnAndReturnTimeIsNull(Integer userId, String isbn);

}
