package com.twinkle.bookservice.command.event;

public class BookDeletedEvent {
	
	// xóa theo ID
	private String bookId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

   
}
