package com.ecom.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecom.entity.Product;
import com.ecom.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	private Long id;
	private Integer quantity;
	private BigDecimal price;
	private User user;
	private Product product;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
