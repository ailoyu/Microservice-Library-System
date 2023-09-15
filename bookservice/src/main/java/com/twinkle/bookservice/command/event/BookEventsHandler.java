package com.twinkle.bookservice.command.event;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twinkle.bookservice.command.data.Book;
import com.twinkle.bookservice.command.data.BookRepository;
import com.twinkle.commonservice.event.BookRollBackStatusEvent;
import com.twinkle.commonservice.event.BookUpdateStatusEvent;

@Component
public class BookEventsHandler {
	
	@Autowired 
	private BookRepository bookRepository;
	
	@EventHandler // Lưu
	public void on(BookCreatedEvent event) {
		
		// Lưu event đã thay đổi xuống DB
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}
	
	@EventHandler // Update
	public void on(BookUpdatedEvent event) {
		
		// Lưu event đã thay đổi xuống DB
		
		Book book = bookRepository.getById(event.getBookId());
		book.setAuthor(event.getAuthor());
		book.setName(event.getName());
		book.setIsReady(event.getIsReady());
		
		bookRepository.save(book);
	}
	
	
	@EventHandler // Xóa
	public void on(BookDeletedEvent event) {
		
		bookRepository.deleteById(event.getBookId());
	}
	
	@EventHandler // CẬP NHẬT STATUS CỦA SÁCH
	public void on(BookUpdateStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}
	
	@EventHandler
	public void on(BookRollBackStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}
	
	
}
