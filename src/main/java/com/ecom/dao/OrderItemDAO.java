package com.ecom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecom.domain.OrderItemDO;

public interface OrderItemDAO {
	public Long add(OrderItemDO item);
	public List<OrderItemDO> find(Long orderId);
	public List<OrderItemDO> findByOrderIds(@Param("orderIds")List<Long> orderIds);
}
