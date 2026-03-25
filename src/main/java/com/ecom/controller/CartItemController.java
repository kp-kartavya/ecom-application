package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.CartItemDto;
import com.ecom.request.CartItemRequest;
import com.ecom.service.CartItemService;

@RestController
@RequestMapping("/cart")
public class CartItemController {
	@Autowired
	CartItemService cartItemService;

	@GetMapping("/getCartItems")
	public ResponseEntity<?> getCartItems(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.ok(cartItemService.getCartItems(userId));
	}
	
	@PostMapping("/add")
	public ResponseEntity<CartItemDto> addToCart(@RequestHeader("X-User-Id") Long userId,
			@RequestBody CartItemRequest cartItemRequest) {
		CartItemDto addedCartItem = cartItemService.addToCart(userId, cartItemRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedCartItem);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCartItem(@RequestHeader("X-User-Id") Long userId,
			@RequestParam Long productId) {
		boolean result = cartItemService.deleteCartItem(userId, productId);
		return !result ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found for user and product")
				: ResponseEntity.status(HttpStatus.OK).body("Cart item deleted successfully");
	}
}
