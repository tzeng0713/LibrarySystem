package com.librarySystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarySystem.entity.Inventory;
import com.librarySystem.entity.Inventory.Status;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    // 查詢所有可借閱的書（顯示在書籍一覽用）
    List<Inventory> findByStatus(Inventory.Status status);
    
    // 查詢該 isbn、狀態為 available 的第一筆資料
	Inventory findFirstByBookIsbnAndStatus(String isbn, Inventory.Status status);
	
	// 查詢某 isbn 的 available 數量
    int countByBookIsbnAndStatus(String isbn, Inventory.Status status);
}
