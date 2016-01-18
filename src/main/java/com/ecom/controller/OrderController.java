package com.ecom.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecom.dao.ItemDAO;
import com.ecom.dao.OrderItemDAO;
import com.ecom.dao.OrdersDAO;
import com.ecom.domain.ItemDO;
import com.ecom.domain.OrderItemDO;
import com.ecom.domain.OrdersDO;
import com.ecom.dto.OrderItemDTO;
import com.ecom.dto.ResultDTO;
import com.ecom.dto.SearchOrderDTO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

@Controller
@RequestMapping(value = "/api/order")
public class OrderController extends BaseController {
	@Resource
	private OrdersDAO ordersDAO;
	@Resource
	private OrderItemDAO orderItemDAO;
	@Resource
	private ItemDAO itemDAO;
	
	@RequestMapping(value = "/find")
    @ResponseBody
    public ResultDTO find(HttpServletRequest request) throws Exception{
		List<SearchOrderDTO> result = Lists.newArrayList();
		
		LocalDateTime st = LocalDateTime.parse(request.getParameter("st"),DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		LocalDateTime et = LocalDateTime.parse(request.getParameter("et"),DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		List<OrdersDO> orderList = ordersDAO.find(st, et);
		
		List<Long> ids = Lists.newArrayList();
		for(OrdersDO o : orderList){
			ids.add(o.getId());
			
			SearchOrderDTO order = new SearchOrderDTO();
			BeanUtils.copyProperties(order, o);
			result.add(order);
		}
		List<OrderItemDO> items = orderItemDAO.findByOrderIds(ids);
		List<Long> itemIds = Lists.transform(items, new Function<OrderItemDO,Long>(){
			@Override
			public Long apply(OrderItemDO input) {
				return input.getItemId();
			}
		});
		List<ItemDO> itemInDB = itemDAO.findByIds(itemIds);
		
		Map<Long,ItemDO> itemMap = Maps.uniqueIndex(itemInDB, new Function<ItemDO,Long>(){
			@Override
			public Long apply(ItemDO input) {
				return input.getpId();
			}
		});
		
		// to dto
		List<OrderItemDTO> orderItems = Lists.newArrayList();
		for(OrderItemDO i : items){
			OrderItemDTO item = new OrderItemDTO();
			BeanUtils.copyProperties(item, i);
			
			// set image
			if(itemMap.containsKey(i.getItemId())){
				item.setImg(itemMap.get(i.getItemId()).getImg());
			}
			
			orderItems.add(item);
		}
		
		Map<Long,Collection<OrderItemDTO>> maps = Multimaps.index(orderItems, new Function<OrderItemDO,Long>(){
			@Override
			public Long apply(OrderItemDO input) {
				return input.getOrderId();
			}
		}).asMap();
		
		for(SearchOrderDTO o : result){
			o.setOrderItems((List<OrderItemDTO>)maps.get(o.getId()));
		}
		
		return ResultDTO.createOKResult(result);
	}
}
