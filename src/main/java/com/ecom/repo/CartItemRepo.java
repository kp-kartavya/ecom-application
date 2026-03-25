package com.ecom.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{
	CartItem findByUserAndProduct(User user, Product product);
	void deleteByUserAndProduct(User user, Product product);
	boolean existsByUserAndProduct(User user, Product product);
	List<CartItem> findByUser(User user);
	void deleteByUserId(Long userId);
}
