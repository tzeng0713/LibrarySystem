package com.librarySystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    private LocalDateTime storeTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        available, borrowed, processing, lost, damaged
    }


    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(LocalDateTime storeTime) {
        this.storeTime = storeTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
