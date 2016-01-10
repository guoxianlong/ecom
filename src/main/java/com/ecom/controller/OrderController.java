package com.ecom.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecom.dto.OrderDTO;
import com.ecom.dto.OrderItemDTO;
import com.ecom.dto.ResultDTO;
import com.google.common.collect.Lists;
import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;

@Controller
@RequestMapping(value = "/api/order")
public class OrderController extends BaseController {
	
	@RequestMapping(value = "/find")
    @ResponseBody
    public ResultDTO find(HttpServletRequest request) throws Exception{
		OrderSearchRequest req = new OrderSearchRequest();
		req.setOrderState("WAIT_GOODS_RECEIVE_CONFIRM");
		req.setPage("1");
		req.setPageSize("10");
		req.setOptionalFields("pin,item_info_list,order_state,order_start_time,order_total_price,freight_price,order_seller_price");
		OrderSearchResponse rs = this.getJdClient(request).execute(req);
		
		List<OrderDTO> orderList = Lists.newArrayList();
		List<OrderSearchInfo> list = rs.getOrderInfoResult().getOrderInfoList();
		for(OrderSearchInfo o : list){
			OrderDTO order = new OrderDTO();
			order.setId(Long.valueOf(o.getOrderId()));
			order.setStartTime(o.getOrderStartTime());
			order.setState(o.getOrderState());
			order.setSellerPrice(o.getOrderSellerPrice());
			order.setFreightPrice(o.getFreightPrice());
			order.setTotalPrice(o.getOrderTotalPrice());
			order.setPin(o.getPin());
			
			List<OrderItemDTO> items = Lists.newArrayList();
			List<ItemInfo> arr = o.getItemInfoList();
			for(ItemInfo it : arr){
				OrderItemDTO item = new OrderItemDTO();
				item.setSkuId(Long.valueOf(it.getSkuId()));
				item.setSkuName(it.getSkuName());
				item.setProdNo(it.getProductNo());
				item.setPrice(it.getJdPrice());
				item.setOuterSkuId(it.getOuterSkuId());
				item.setItemId(Long.valueOf(it.getWareId()));
				item.setGiftPoint(it.getGiftPoint());
				item.setCount(Integer.valueOf(it.getItemTotal()));
				items.add(item);
			}
			order.setItems(items);
			orderList.add(order);
		}
		return ResultDTO.createOKResult(orderList);
	}
}
