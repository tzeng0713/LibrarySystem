-- 建立圖書館系統資料庫
DROP DATABASE IF EXISTS library_system;
CREATE DATABASE library_system;
USE library_system;

-- 使用者表
CREATE TABLE user (
  user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  phone_number VARCHAR(10) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  user_name VARCHAR(50),
  registration_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  last_login_time DATETIME
);

-- 書籍資訊表
CREATE TABLE book (
  isbn VARCHAR(20) NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  author VARCHAR(100),
  introduction TEXT
);

-- 庫存（每一本實體書）
CREATE TABLE inventory (
  inventory_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  isbn VARCHAR(20),
  store_time DATETIME,
  status ENUM('available', 'borrowed', 'processing', 'lost', 'damaged') DEFAULT 'available',
  FOREIGN KEY (isbn) REFERENCES book(isbn)
);

-- 借閱紀錄
CREATE TABLE borrowing_record (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  inventory_id INT,
  borrowing_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  return_time DATETIME,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);

