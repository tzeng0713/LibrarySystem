package com.librarySystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarySystem.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 檢查是否有這支手機號碼，回傳 true / false
    boolean existsByPhoneNumber(String phoneNumber);
    
    // 透過手機號碼查找使用者，若查不到 → 用 Optional 包裝避免 null
    Optional<User> findByPhoneNumber(String phoneNumber);
}
