package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.ProductDto;
import com.ecom.service.ProductService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService productService;

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
		ProductDto savedProduct = productService.addProduct(productDto);
		return ResponseEntity.ok(savedProduct);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
		ProductDto updatedProduct = productService.updateProduct(id, productDto);
		return ResponseEntity.ok(updatedProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product with id " + id + " deleted successfully");
	}
	
	@GetMapping("/searchProducts")
	public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
		List<ProductDto> products = productService.searchProducts(keyword);
		return ResponseEntity.ok(products);
	}
}
