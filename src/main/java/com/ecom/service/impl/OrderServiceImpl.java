package com.ecom.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.CartItemDto;
import com.ecom.dto.OrderDto;
import com.ecom.dto.OrderStatus;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;
import com.ecom.entity.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repo.CartItemRepo;
import com.ecom.repo.OrderRepo;
import com.ecom.repo.ProductRepo;
import com.ecom.repo.UserRepo;
import com.ecom.service.CartItemService;
import com.ecom.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepo orderRepo;
	@Autowired
	CartItemRepo cartItemRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CartItemService cartItemService;

	@Override
	public OrderDto placeOrder(Long userId) {
		List<CartItemDto> cartItems = cartItemService.getCartItems(userId);

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

		BigDecimal totalPrice = cartItems.stream().map(CartItemDto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

		Order order = new Order();
		order.setUser(user);
		order.setTotalAmount(totalPrice);
		order.setStatus(OrderStatus.CONFIRMED);

		List<OrderItem> orderItems = cartItems.stream()
				.map(item -> new OrderItem(null, item.getProduct(), item.getQuantity(), item.getPrice(), order))
				.collect(Collectors.toList());

		order.setOrderItems(orderItems);
		Order savedOrder = orderRepo.save(order);
		
		cartItemService.clearCart(userId);
		OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);
		return orderDto;
	}

}
