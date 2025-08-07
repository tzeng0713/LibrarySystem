package com.librarySystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.librarySystem.dto.BookDisplayDTO;
import com.librarySystem.dto.BorrowingRecordDTO;
import com.librarySystem.service.LibraryService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;
    
    // 查詢所有書籍資訊
    @GetMapping("/books")
    public List<BookDisplayDTO> getAllBooksForDisplay() {
        return libraryService.getAllBooksForDisplay();
    }
    
    // 借書
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(
        @RequestBody Map<String, Object> requestBody,
        HttpSession session) {

        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");
        }

        String isbn = (String) requestBody.get("isbn");
        if (isbn == null || isbn.isEmpty()) {
            return ResponseEntity.badRequest().body("缺少 isbn");
        }

        libraryService.borrowBook(userId, isbn);
        return ResponseEntity.ok("借書成功");
    }
    
    // 查詢借閱紀錄
    @GetMapping("/records")
    public List<BorrowingRecordDTO> getMyBorrowingRecords(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            throw new RuntimeException("未登入");
        }

        return libraryService.findRecordsByUserId(userId);
    }
    
    // 還書
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(
            @RequestBody Map<String, Object> requestBody,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");
        }

        Integer inventoryId = (Integer) requestBody.get("inventoryId");
        if (inventoryId == null) {
            return ResponseEntity.badRequest().body("缺少 inventoryId");
        }

        libraryService.returnBook(userId, inventoryId);
        return ResponseEntity.ok("還書成功");
    }
}
