package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderRequest;

public interface OrderService {
	public String placeOrder(OrderRequest orderRequest);
}
