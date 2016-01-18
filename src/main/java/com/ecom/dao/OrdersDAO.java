package com.ecom.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecom.domain.OrdersDO;

public interface OrdersDAO {
	public int add(OrdersDO order);
	public OrdersDO get(Long orderId);
	public List<OrdersDO> find(@Param("st") LocalDateTime st, @Param("et")LocalDateTime et);
}
