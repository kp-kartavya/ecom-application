package com.ecom.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.ProductDto;
import com.ecom.entity.Product;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repo.ProductRepo;
import com.ecom.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> products = productRepo.findByActiveTrue();
		return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		Product savedProduct = productRepo.save(product);
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(Long id, ProductDto productDto) {
		Product existingProduct = productRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(id)));

		existingProduct.setName(productDto.getName());
		existingProduct.setDescription(productDto.getDescription());
		existingProduct.setPrice(productDto.getPrice());
		existingProduct.setStockQuantity(productDto.getStockQuantity());
		existingProduct.setCategory(productDto.getCategory());
		existingProduct.setImageUrl(productDto.getImageUrl());
		existingProduct.setActive(productDto.getActive());

		Product updatedProduct = productRepo.save(existingProduct);
		return modelMapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void deleteProduct(Long id) {
		if (!productRepo.existsById(id)) {
			throw new ResourceNotFoundException("Product", "id", String.valueOf(id));
		}
		productRepo.deleteById(id);
	}

	@Override
	public List<ProductDto> searchProducts(String keyword) {
		List<Product> products = productRepo.searchProducts(keyword);
		return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
	}
}
