package com.ecom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecom.domain.ItemDO;

public interface ItemDAO {
	public int add(ItemDO item);
	public List<ItemDO> findByIds(@Param("itemIds")List<Long> itemIds);
}
