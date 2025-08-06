package com.librarySystem.service;

import com.librarySystem.entity.User;
import com.librarySystem.repository.UserRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired  
    private BCryptPasswordEncoder passwordEncoder;

    // 註冊帳號
    public User register(User user) {
    	// 確認手機號碼是否已被註冊
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("該手機號碼已被註冊，請使用其他號碼");
        }

        // 加鹽雜湊密碼
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // 設定註冊時間
        user.setRegistrationTime(LocalDateTime.now());

        // 儲存資料到資料庫
        return userRepository.save(user);
    }
    
    // 登入比對密碼
    public User login(String phoneNumber, String rawPassword) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("查無此帳號");
        }

        // 用 BCrypt 比對密碼
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("密碼錯誤");
        }
        
        // 更新最後登入時間
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
        
        return user;
    }

    // 根據手機查詢使用者
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    // 檢查手機是否已註冊
    public boolean isPhoneNumberRegistered(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
    
    // 根據ID查詢使用者
    public User findById(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        
        if (user == null) {
            throw new IllegalArgumentException("找不到使用者");
        }

        return user;
    }
}
