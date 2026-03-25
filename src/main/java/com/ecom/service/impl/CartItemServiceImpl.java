package com.ecom.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.CartItemDto;
import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repo.CartItemRepo;
import com.ecom.repo.ProductRepo;
import com.ecom.repo.UserRepo;
import com.ecom.request.CartItemRequest;
import com.ecom.service.CartItemService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
	@Autowired
	CartItemRepo cartItemRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CartItemDto addToCart(Long userId, CartItemRequest cartItemRequest) {
		Product product = productRepo.findById(cartItemRequest.getProductId()).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", cartItemRequest.getProductId().toString()));

		if (product.getStockQuantity() < cartItemRequest.getQuantity()) {
			throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
		}

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

		CartItem existingCartItem = cartItemRepo.findByUserAndProduct(user, product);

		if (existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
			existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
			cartItemRepo.save(existingCartItem);
			return modelMapper.map(existingCartItem, CartItemDto.class);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
			cartItem.setQuantity(cartItemRequest.getQuantity());
			cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
			CartItem savedCartItem = cartItemRepo.save(cartItem);
			return modelMapper.map(savedCartItem, CartItemDto.class);
		}

	}

	@Override
	public boolean deleteCartItem(Long userId, Long productId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));

		if (!cartItemRepo.existsByUserAndProduct(user, product)) {
			return false;
		}
		cartItemRepo.deleteByUserAndProduct(user, product);
		return true;
	}

	@Override
	public List<CartItemDto> getCartItems(Long userId) {
		return userRepo.findById(userId).map(cartItemRepo::findByUser)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId.toString())).stream()
				.map(cartItem -> modelMapper.map(cartItem, CartItemDto.class)).toList();
	}

	@Override
	public void clearCart(Long userId) {
		if (!userRepo.existsById(userId)) {
		    throw new ResourceNotFoundException("User", "id", userId.toString());
		}

		cartItemRepo.deleteByUserId(userId);
	}
}
