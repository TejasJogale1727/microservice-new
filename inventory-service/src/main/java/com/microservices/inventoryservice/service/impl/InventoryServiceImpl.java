package com.microservices.inventoryservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.repository.InventoryRepository;
import com.microservices.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryServiceImpl implements InventoryService{

	private final InventoryRepository inventoryRepository;
	
	@Transactional(readOnly = true)
//	@SneakyThrows
	public List<InventoryResponse> isInStock(List<String> skuCode) {
//		log.info("Wait Started");
//		Thread.sleep(10000);
//		log.info("Wait Ended");
		return inventoryRepository.findBySkuCodeIn(skuCode).stream()
				.map(inventory -> 
					InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity()>0).build()
				).toList();
	}
}
