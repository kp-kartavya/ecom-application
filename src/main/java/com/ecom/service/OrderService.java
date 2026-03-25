package com.ecom.service;

import com.ecom.dto.OrderDto;

public interface OrderService {

	OrderDto placeOrder(Long userId);

}
