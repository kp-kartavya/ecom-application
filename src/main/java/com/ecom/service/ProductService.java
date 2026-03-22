package com.ecom.service;

import java.util.List;

import com.ecom.dto.ProductDto;

public interface ProductService {

	ProductDto addProduct(ProductDto productDto);

	ProductDto updateProduct(Long id, ProductDto productDto);

	List<ProductDto> getAllProducts();

	void deleteProduct(Long id);

	List<ProductDto> searchProducts(String keyword);

}
