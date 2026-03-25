package com.ecom.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Long id;
	private UserDto user;
	private BigDecimal totalAmount;
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.PENDING;
	private List<OrderItemDto> orderItems = new ArrayList<>();
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
