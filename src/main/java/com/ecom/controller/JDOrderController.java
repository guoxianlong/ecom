package com.ecom.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.ecom.common.enums.CouponTypeEnum;
import com.ecom.common.enums.DeliveryTypeEnum;
import com.ecom.common.enums.JDOrderStateEnum;
import com.ecom.dao.ItemDAO;
import com.ecom.dao.OrderCouponDAO;
import com.ecom.dao.OrderItemDAO;
import com.ecom.dao.OrdersDAO;
import com.ecom.dao.SkuDAO;
import com.ecom.domain.ItemDO;
import com.ecom.domain.OrderCouponDO;
import com.ecom.domain.OrderItemDO;
import com.ecom.domain.OrdersDO;
import com.ecom.domain.SkuDO;
import com.ecom.dto.ResultDTO;
import com.google.common.collect.Lists;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.order.CouponDetail;
import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareSkuGetRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareSkuGetResponse;

@Controller
@RequestMapping(value = "/api/jd/order")
public class JDOrderController extends BaseController{
	private static Logger LOG = Logger.getLogger(JDOrderController.class);
	@Resource
	private OrdersDAO orderDAO;
	@Resource
	private OrderItemDAO orderItemDAO;
	@Resource
	private OrderCouponDAO orderCouponDAO;
	@Resource
	private ItemDAO itemDAO;
	@Resource
	private SkuDAO skuDAO;
	
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
			
			LocalDateTime startDT = LocalDateTime.of(2016, 1, 17, 0, 0);
			LocalDateTime endDT = LocalDateTime.of(2016, 1, 18, 0, 0);
//			DateTime startDT = new DateTime(2016, 1, 11, 0, 0, 0, 0);
//			DateTime endDT = new DateTime(2016, 1, 12, 0, 0, 0, 0);
			req.setStartDate(startDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			req.setEndDate(endDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			JdClient jdClient = null;
			Optional<JdClient> r = this.getJdClient(request);
			if(!r.isPresent()){
				return ResultDTO.createFailResult("");
			}else{
				jdClient = r.get();
			}
			OrderSearchResponse rs = jdClient.execute(req);
			
			List<OrderSearchInfo> list = rs.getOrderInfoResult().getOrderInfoList();
			if(LOG.isDebugEnabled()){
				LOG.debug(JSON.toJSONString(list));
			}
			
			List<Long> skuIds = Lists.newArrayList();
			for(OrderSearchInfo o : list){
				OrdersDO order = new OrdersDO();
				order.setPlatform(1);
				order.setOuterId(Long.valueOf(o.getOrderId()));
				order.setSellerId(Long.valueOf(o.getVenderId()));
				order.setCust(o.getPin());
				order.setPayType(payTypeParse(o.getPayType()));
				
				order.setDeliveryType(deliveryTypeParse(o.getDeliveryType()).getId());
				order.setInvoiceInfo(o.getInvoiceInfo());
				order.setRemark(o.getOrderRemark());
				order.setConsName(o.getConsigneeInfo().getFullname());
				order.setConsMob(o.getConsigneeInfo().getMobile());
				order.setConsTel(o.getConsigneeInfo().getTelephone());
				order.setConsAddr(o.getConsigneeInfo().getFullAddress());
				order.setConsProvince(o.getConsigneeInfo().getProvince());
				order.setConsCity(o.getConsigneeInfo().getCity());
				order.setConsCounty(o.getConsigneeInfo().getCounty());
				order.setWaybill(o.getWaybill());
				order.setLogisticsId(o.getLogisticsId());
				order.setReturnOrder(Integer.valueOf(o.getReturnOrder()));
				
				order.setPaymentConfirmTime(dateParse(o.getPaymentConfirmTime()));
				order.setEndTime(dateParse(o.getOrderEndTime()));
				order.setCreatedTime(dateParse(o.getOrderStartTime()));
				order.setModifiedTime(dateParse(o.getModified()));
				order.setState(JDOrderStateEnum.parse(o.getOrderState()).getCode());
				order.setSellerPrice(Utils.yuanToFen(o.getOrderSellerPrice()));
				order.setFreightPrice(Utils.yuanToFen(o.getFreightPrice()));
				order.setTotalPrice(Utils.yuanToFen(o.getOrderTotalPrice()));
				order.setPayment(Utils.yuanToFen(o.getOrderPayment()));
				orderDAO.add(order);
				
				List<ItemInfo> items = o.getItemInfoList();
				for(ItemInfo i : items){
					Long skuId = Long.valueOf(i.getSkuId());
					
					OrderItemDO item = new OrderItemDO();
					item.setOrderId(order.getId());
					item.setSkuId(skuId);
					item.setSkuName(i.getSkuName());
					item.setItemId(Long.valueOf(i.getWareId()));
					item.setPrice(Utils.yuanToFen(i.getJdPrice()));
					item.setGiftPoint(Integer.valueOf(i.getGiftPoint()));
					item.setCount(Integer.valueOf(i.getItemTotal()));
					item.setOuterSkuId(i.getOuterSkuId());
					orderItemDAO.add(item);
					
					if(!skuIds.contains(skuId)){
						skuIds.add(skuId);
					}
				}
				
				List<CouponDetail> coupons = o.getCouponDetailList();
				for(CouponDetail c : coupons){
					if(StringUtils.isBlank(c.getOrderId())){
						continue;
					}
					OrderCouponDO coupon = new OrderCouponDO();
					if(StringUtils.isBlank(c.getSkuId())){
						coupon.setSkuId(0L);
					}else{
						coupon.setSkuId(Long.valueOf(c.getSkuId()));
					}
					
					coupon.setOrderId(order.getId());
					coupon.setType(CouponTypeEnum.parse(c.getCouponType()).getId());
					coupon.setPrice(Utils.yuanToFen(c.getCouponPrice()));
					orderCouponDAO.add(coupon);
				}
			}
			
			//sync sku
			syncSku(jdClient,skuIds);
			return ResultDTO.createOKResult("ok");
		}catch(Exception e){
			LOG.error("sync order error.",e);
		}
		return ResultDTO.createFailResult("");
	}
	
	private void syncSku(JdClient jdClient,List<Long> skuIds) throws Exception{
		List<SkuDO> skuInDB = skuDAO.findByIds(skuIds);
		for(Long id : skuIds){
			if(skuInDB.contains(id)){
				continue;
			}
			WareSkuGetRequest request = new WareSkuGetRequest();
			request.setSkuId(String.valueOf(id));
			WareSkuGetResponse  rs = jdClient.execute(request);
			
			if(!rs.getCode().equals("0")) {
				continue;
			}
			Sku jdSku = rs.getSku();
			SkuDO sku = new SkuDO();
			sku.setpId(jdSku.getSkuId());
			sku.setShopId(jdSku.getShopId());
			sku.setP(1);
			sku.setItemId(jdSku.getWareId());
			sku.setAttr(jdSku.getAttributes());
			sku.setCostPrice(Utils.yuanToFen(jdSku.getCostPrice()));
			sku.setCreated(dateParse(jdSku.getCreated()));
			sku.setMarketPrice(Utils.yuanToFen(jdSku.getMarketPrice()));
			sku.setModified(dateParse(jdSku.getModified()));
			sku.setPrice(Utils.yuanToFen(jdSku.getJdPrice()));
			sku.setStatus(statusParse(jdSku.getStatus()));
			sku.setStockNum(Long.valueOf(jdSku.getStockNum()).intValue());
			skuDAO.add(sku);
			
			//sync item
			syncItem(jdClient, jdSku.getWareId());
		}
	}
	
	private void syncItem(JdClient jdClient,Long itemId) throws Exception{
		List<Long> ids = Lists.newArrayList();
		ids.add(itemId);
		List<ItemDO> itemInDB = itemDAO.findByIds(ids);
		if(itemInDB != null && itemInDB.size()>0){
			return;
		}
		
		WareGetRequest request = new WareGetRequest();
		request.setWareId(String.valueOf(itemId));
		WareGetResponse response = jdClient.execute(request);
		if(!"0".equals(response.getCode())){
			return;
		}
		Ware ware = response.getWare();
		ItemDO item = new ItemDO();
		item.setpId(ware.getWareId());
		item.setP(1);
		item.setSellerId(ware.getVenderId());
		item.setShopId(ware.getShopId());
		item.setStatus(statusParse(ware.getStatus()));
		item.setAttr(ware.getAttributes());
		item.setImg(ware.getLogo());
		item.setOnlineTime(dateParse(ware.getOnlineTime()));
		item.setOfflineTime(dateParse(ware.getOfflineTime()));
		item.setPrice(Utils.yuanToFen(ware.getJdPrice()));
		item.setTitle(ware.getTitle());
		itemDAO.add(item);
	}
	
	private int statusParse(String s){
		if("Valid".equals(s)){
			return 1;
		}
		if("Invalid".equals(s)){
			return 0;
		}
		if("Delete".equals(s)){
			return -1;
		}
		return -2;
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
	
	private DeliveryTypeEnum deliveryTypeParse(String s){
		if(StringUtils.isNotBlank(s) || s.equals("工作日、双休日与假日均可送货")){
			return DeliveryTypeEnum.ALL;
		}
		if(s.equals("任意时间")){
			return DeliveryTypeEnum.ALL;
		}
		if(s.equals("只工作日送货(双休日、假日不用送)")){
			return DeliveryTypeEnum.WORK_DAY;
		}
		if(s.equals("只双休日、假日送货(工作日不用送)")){
			return DeliveryTypeEnum.REST_DAY;
		}
		return DeliveryTypeEnum.ALL;
	}
}
