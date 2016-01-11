package com.ecom.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecom.dao.OrdersDAO;
import com.ecom.domain.OrdersDO;
import com.ecom.dto.ResultDTO;

@Controller
@RequestMapping(value = "/api/jd/order")
public class JDOrderController extends BaseController{
	private static Logger LOG = Logger.getLogger(JDOrderController.class);
	@Resource
	private OrdersDAO orderDAO;
	
	@RequestMapping(value = "/sync")
    @ResponseBody
	public ResultDTO sync(HttpServletRequest request){
		try{
			/*
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
			*/
			OrdersDO order = new OrdersDO();
			order.setOuterId(100L);
			order.setPlatform(1);
			order.setSellerId(100000L);
			order.setCust("test");
			order.setPayType(1);
			order.setCreatedTime(new Date());
			order.setModifiedTime(new Date());
			orderDAO.add(order);
			return ResultDTO.createOKResult("ok");
		}catch(Exception e){
			LOG.error("sync order error.",e);
		}
		return ResultDTO.createFailResult("");
	}
}
