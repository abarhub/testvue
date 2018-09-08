package com.example.demo.rest;

import com.example.demo.dto.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Test1Controler {

	@RequestMapping("/entity/all")
	public List<String> findAll() {
		return new ArrayList<>();
	}

	@RequestMapping("/test1")
	public Message test1() {
		Message message=new Message();
		message.setMessage("Test");
		return message;
	}
}
