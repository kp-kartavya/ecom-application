package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.OrderDto;
import com.ecom.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@PostMapping("/place")
	public ResponseEntity<OrderDto> placeOrder(@RequestHeader("X-User-Id") Long userId) {
		OrderDto order = orderService.placeOrder(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}
}
