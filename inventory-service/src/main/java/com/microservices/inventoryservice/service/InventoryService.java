package com.microservices.inventoryservice.service;

import java.util.List;

import com.microservices.inventoryservice.dto.InventoryResponse;

public interface InventoryService {
	public List<InventoryResponse> isInStock(List<String> skuCode);
}
