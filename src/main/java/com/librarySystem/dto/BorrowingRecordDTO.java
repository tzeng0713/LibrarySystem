package com.librarySystem.dto;


public class BorrowingRecordDTO {
	private String bookName;
    private String borrowingTime;
    private String returnTime;
    private Integer inventoryId;

    public BorrowingRecordDTO(String bookName, String borrowingTime, String returnTime, Integer inventoryId) {
        this.bookName = bookName;
        this.borrowingTime = borrowingTime;
        this.returnTime = returnTime;
        this.inventoryId = inventoryId;
    }

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBorrowingTime() {
		return borrowingTime;
	}

	public void setBorrowingTime(String borrowingTime) {
		this.borrowingTime = borrowingTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}
    
	

}

