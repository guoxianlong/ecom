package com.ecom.domain;

import java.util.Date;

public class OrdersDO {
	private Long id;;
	private Long outerId;;
	private int platform;;
	private Long sellerId;
	private String cust;
	private int payType;
	private int totalPrice;
	private int sellerPrice;
	private int payment;
	private int freightPrice;
	private int sellerDiscount;
	private int state;
	private String deliveryType;
	private String invoiceInfo;
	private String remark;
	private String consName;
	private String consTel;
	private String consMob;
	private String consAddr;
	private String consProvince;
	private String consCity;
	private String consCounty;
	private String waybill;
	private String logisticsId;
	private int returnOrder;
	private Date createdTime;
	private Date modifiedTime;
	private Date paymentConfirmTime;
	private Date endTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOuterId() {
		return outerId;
	}
	public void setOuterId(Long outerId) {
		this.outerId = outerId;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getCust() {
		return cust;
	}
	public void setCust(String cust) {
		this.cust = cust;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getSellerPrice() {
		return sellerPrice;
	}
	public void setSellerPrice(int sellerPrice) {
		this.sellerPrice = sellerPrice;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(int freightPrice) {
		this.freightPrice = freightPrice;
	}
	public int getSellerDiscount() {
		return sellerDiscount;
	}
	public void setSellerDiscount(int sellerDiscount) {
		this.sellerDiscount = sellerDiscount;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getConsName() {
		return consName;
	}
	public void setConsName(String consName) {
		this.consName = consName;
	}
	public String getConsTel() {
		return consTel;
	}
	public void setConsTel(String consTel) {
		this.consTel = consTel;
	}
	public String getConsMob() {
		return consMob;
	}
	public void setConsMob(String consMob) {
		this.consMob = consMob;
	}
	public String getConsAddr() {
		return consAddr;
	}
	public void setConsAddr(String consAddr) {
		this.consAddr = consAddr;
	}
	public String getConsProvince() {
		return consProvince;
	}
	public void setConsProvince(String consProvince) {
		this.consProvince = consProvince;
	}
	public String getConsCity() {
		return consCity;
	}
	public void setConsCity(String consCity) {
		this.consCity = consCity;
	}
	public String getConsCounty() {
		return consCounty;
	}
	public void setConsCounty(String consCounty) {
		this.consCounty = consCounty;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}
	public int getReturnOrder() {
		return returnOrder;
	}
	public void setReturnOrder(int returnOrder) {
		this.returnOrder = returnOrder;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Date getPaymentConfirmTime() {
		return paymentConfirmTime;
	}
	public void setPaymentConfirmTime(Date paymentConfirmTime) {
		this.paymentConfirmTime = paymentConfirmTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
