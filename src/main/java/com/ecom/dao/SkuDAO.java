package com.ecom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecom.domain.SkuDO;

public interface SkuDAO {
	public int add(SkuDO sku);
	public List<SkuDO> findByIds(@Param("skuIds")List<Long> skuIds);
}
