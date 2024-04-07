package com.microservices.inventoryservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.repository.InventoryRepository;
import com.microservices.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService{

	private final InventoryRepository inventoryRepository;
	
	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		return inventoryRepository.findBySkuCodeIn(skuCode).stream()
				.map(inventory -> 
					InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity()>0).build()
				).toList();
	}
}
