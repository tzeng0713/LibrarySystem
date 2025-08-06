package com.librarySystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    
    @NotBlank(message = "電話不能為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式錯誤，需為 09 開頭的 10 碼數字")
    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @NotBlank(message = "密碼不能為空")
    @Column(nullable = false, length = 255)
    private String password;

    @Size(max = 50, message = "使用者名稱最多 50 字")
    @Column(length = 50)
    private String userName;

    private LocalDateTime registrationTime;

    private LocalDateTime lastLoginTime;
    
    
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registrationTime = registrationTime;
	}

	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
    
    
}