package com.ecom.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ecom.common.Utils;
import com.ecom.common.constant.JDOrderStateEnum;
import com.ecom.dao.OrdersDAO;
import com.ecom.domain.OrdersDO;
import com.ecom.dto.ResultDTO;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;

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
			OrderSearchRequest req = new OrderSearchRequest();
			String state = JDOrderStateEnum.WAIT_SELLER_STOCK_OUT.getStr() + ","
					+ JDOrderStateEnum.WAIT_GOODS_RECEIVE_CONFIRM.getStr() + ","
					+ JDOrderStateEnum.FINISHED.getStr() + ","
					+ JDOrderStateEnum.TRADE_CANCELED.getStr() + ","
					+ JDOrderStateEnum.TRADE_CANCELED.getStr() + ","
					+ JDOrderStateEnum.LOCKED.getStr();
			req.setOrderState(state);
			req.setPage("1");
			req.setPageSize("10");
			
			String[] fidlds = new String[]{
					"order_id",
					"order_source",
					"vender_id",
					"pay_type",
					"order_total_price",
					"order_seller_price",
					"order_payment",
					"freight_price",
					"seller_discount",
					"order_state",
					"delivery_type",
					"invoice_info",
					"order_remark",
					"order_start_time",
					"order_end_time",
					"modified",
					"consignee_info",
					"item_info_list",
					"coupon_detail_list",
					"vender_remark",
					"payment_confirm_time",
					"waybill",
					"logistics_id",
					"vat_invoice_info",
					"pin",
					"return_order",
					"order_type"
			};
			req.setOptionalFields(StringUtils.join(fidlds, ','));
			
			DateTime startDT = new DateTime(2016, 1, 11, 0, 0, 0, 0);
			DateTime endDT = new DateTime(2016, 1, 12, 0, 0, 0, 0);
			req.setStartDate(startDT.toString("yyyy-MM-dd HH:mm:ss"));
			req.setEndDate(endDT.toString("yyyy-MM-dd HH:mm:ss"));
			OrderSearchResponse rs = this.getJdClient(request).execute(req);
			
			List<OrderSearchInfo> list = rs.getOrderInfoResult().getOrderInfoList();
			if(LOG.isDebugEnabled()){
				LOG.debug(JSON.toJSONString(list));
			}
			for(OrderSearchInfo o : list){
				OrdersDO order = new OrdersDO();
				order.setPlatform(1);
				order.setOuterId(Long.valueOf(o.getOrderId()));
				order.setSellerId(Long.valueOf(o.getVenderId()));
				order.setCust(o.getPin());
				order.setPayType(payTypeParse(o.getPayType()));
				order.setCreatedTime(dateParse(o.getOrderStartTime()));
				order.setModifiedTime(dateParse(o.getModified()));
				order.setState(JDOrderStateEnum.parse(o.getOrderState()).getCode());
				order.setSellerPrice(Utils.yuanToFen(o.getOrderSellerPrice()));
				order.setFreightPrice(Utils.yuanToFen(o.getFreightPrice()));
				order.setTotalPrice(Utils.yuanToFen(o.getOrderTotalPrice()));
				order.setPayment(Utils.yuanToFen(o.getOrderPayment()));
				long orderId = orderDAO.add(order);
				
				
			}
			return ResultDTO.createOKResult("ok");
			/*
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
			*/
		}catch(Exception e){
			LOG.error("sync order error.",e);
		}
		return ResultDTO.createFailResult("");
	}
	
	private Date dateParse(String dt){
		DateTime d = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(dt);
		return d.toDate();
	}
	
	private int payTypeParse(String payType){
		if(StringUtils.contains(payType, '-')){
			return Integer.valueOf(StringUtils.split(payType, '-')[0]);
		}
		return -1;
	}
}
