package com.twinkle.bookservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twinkle.bookservice.BookserviceApplication;
import com.twinkle.bookservice.query.model.BookResponseModel;
import com.twinkle.bookservice.query.queries.GetAllBooksQuery;
import com.twinkle.bookservice.query.queries.GetBookQuery;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
	
	@Autowired
	private QueryGateway queryGateway;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(BookserviceApplication.class);
	
	@GetMapping("/{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId) {
		// (set id vô query để tìm)
        GetBookQuery getBooksQuery = new GetBookQuery(); 
        getBooksQuery.setBookId(bookId);
        
        // chạy câu lệnh query bằng queryGateway 
        BookResponseModel bookResponseModel =
        queryGateway.query(getBooksQuery,
                ResponseTypes.instanceOf(BookResponseModel.class)) // trả ra 1 data kiểu BookResponseModel
                .join(); // join kết quả vô bookResponseModel

        return bookResponseModel;
    }
	
	@GetMapping
	public List<BookResponseModel> getAllBooks(){
		GetAllBooksQuery getAllBooksQuery = new GetAllBooksQuery();
		List<BookResponseModel> list = queryGateway.query(
				getAllBooksQuery, 
				ResponseTypes.multipleInstancesOf(BookResponseModel.class)) // trả ra nhìu data kiểu BookResponseModel
				.join();
		logger.info("Danh sach Book "+list.toString());
		return list;
	}
}
