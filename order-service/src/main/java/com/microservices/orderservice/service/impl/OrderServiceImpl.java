package com.microservices.orderservice.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.orderservice.dto.InventoryResponse;
import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;
import com.microservices.orderservice.service.OrderService;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	
	private final WebClient.Builder webClientBuilder;
	
	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
		.stream()
		.map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
		.toList();
		
		order.setOrderLineItems(orderLineItems);
		
		List<String> skuCodes =  order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();
		
		//Call Inventory service and place order if it is in stock
		InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();

		boolean allProductsInStock =  Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
		
		if (allProductsInStock) {
			orderRepository.save(order);
			return "Order Placed Successfully"; 
		}else {
			throw new IllegalArgumentException("Prodcut is not in stock, please try again later.");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
