package com.microservices.productservice.service;

import java.util.List;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;

public interface ProductService {
	public void createProduct(ProductRequest productRequest);

	public List<ProductResponse> getAllProducts();
}
