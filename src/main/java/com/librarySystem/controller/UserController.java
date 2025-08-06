package com.librarySystem.controller;

import com.librarySystem.dto.LoginRequest;
import com.librarySystem.entity.User;
import com.librarySystem.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 處理會員註冊
     * @param user 來自前端的 JSON 物件，自動綁定為 User 實體，並驗證欄位
     * @return 回傳註冊成功的 User JSON（密碼不會回傳給前端）
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }
    
    // 登入功能
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request.getPhoneNumber(), request.getPassword());
        
        // ✅ 登入成功後存入 Session
        session.setAttribute("loggedInUserId", user.getUserId());

        // 為了安全，將密碼設為 null 不回傳
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        // 從 Session 取得登入者 ID
        Integer userId = (Integer) session.getAttribute("loggedInUserId");

        // 沒有登入
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("尚未登入，請先登入後再操作");
        }

        // 有登入 → 查詢資料庫回傳使用者資料
        User user = userService.findById(userId);
        user.setPassword(null); // 安全處理：不回傳密碼

        return ResponseEntity.ok(user);
    }

}
