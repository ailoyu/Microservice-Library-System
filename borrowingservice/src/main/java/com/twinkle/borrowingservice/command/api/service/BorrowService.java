package com.twinkle.borrowingservice.command.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinkle.borrowingservice.command.api.data.BorrowRepository;
import com.twinkle.borrowingservice.command.api.model.Message;

@Service
@EnableBinding(Source.class)
public class BorrowService {
	
	@Autowired
	private BorrowRepository repository;
	
	@Autowired
	private MessageChannel output;
	
	public void sendMessage(Message message) {
		try {
			// Parse từ message sang JSON để gửi qua kafka Message Queue
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(message);
			output.send(MessageBuilder.withPayload(json).build());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String findIdBorrowing(String employeeId, String bookId) {
		// Lấy ra Id của borrowing
		return repository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId).getId();
	}
	
}
