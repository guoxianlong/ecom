package com.ecom.dto;

import java.util.List;

import com.ecom.domain.OrdersDO;

public class SearchOrderDTO extends OrdersDO{
	private List<OrderItemDTO> orderItems;

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
}
