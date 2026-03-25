package com.ecom.service;

import java.util.List;

import com.ecom.dto.CartItemDto;
import com.ecom.request.CartItemRequest;

public interface CartItemService {

	CartItemDto addToCart(Long userId, CartItemRequest cartItemRequest);

	boolean deleteCartItem(Long userId, Long productId);

	List<CartItemDto> getCartItems(Long userId);

	void clearCart(Long userId);

}
