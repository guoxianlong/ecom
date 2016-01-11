package com.ecom.common.constant;

import org.apache.commons.lang.StringUtils;

/**
 * JD订单状态
 * 1）WAIT_SELLER_STOCK_OUT 等待出库 
 * 2）SEND_TO_DISTRIBUTION_CENER 发往配送中心（只适用于LBP，SOPL商家） 
 * 3）DISTRIBUTION_CENTER_RECEIVED 配送中心已收货（只适用于LBP，SOPL商家） 
 * 4）WAIT_GOODS_RECEIVE_CONFIRM 等待确认收货 
 * 5）RECEIPTS_CONFIRM 收款确认（服务完成）（只适用于LBP，SOPL商家） 
 * 6）WAIT_SELLER_DELIVERY等待发货（只适用于海外购商家，等待境内发货 标签下的订单） 
 * 7）FINISHED_L 完成 
 * 8）TRADE_CANCELED 取消 
 * 9）LOCKED 已锁定 
 * 10）PAUSE 暂停（适用于LOC订单）
 * @author lingkong
 *
 */
public enum JDOrderStateEnum {
	UNKOWN(-1,"unkown"),
	//等待出库
	WAIT_SELLER_STOCK_OUT(1,"WAIT_SELLER_STOCK_OUT"),
	//发往配送中心（只适用于LBP，SOPL商家）
	SEND_TO_DISTRIBUTION_CENER(2,"SEND_TO_DISTRIBUTION_CENER"),
	//等待确认收货
	WAIT_GOODS_RECEIVE_CONFIRM(4,"WAIT_GOODS_RECEIVE_CONFIRM"),
	//完成
	FINISHED(7,"FINISHED_L"),
	//取消
	TRADE_CANCELED(8,"TRADE_CANCELED"),
	//已锁定 
	LOCKED(9,"LOCKED"),
	//暂停（适用于LOC订单）
	PAUSE(10,"PAUSE");
	
	private int code;
	private String str;
	private JDOrderStateEnum(int code, String str){
		this.code = code;
		this.str = str;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getStr(){
		return str;
	}
	
	public static JDOrderStateEnum parse(String str){
		if(StringUtils.isBlank(str)){
			return JDOrderStateEnum.UNKOWN;
		}
		if(JDOrderStateEnum.WAIT_SELLER_STOCK_OUT.getStr().equals(str)){
			return JDOrderStateEnum.WAIT_SELLER_STOCK_OUT;
		}
		if(JDOrderStateEnum.SEND_TO_DISTRIBUTION_CENER.getStr().equals(str)){
			return JDOrderStateEnum.SEND_TO_DISTRIBUTION_CENER;
		}
		if(JDOrderStateEnum.WAIT_GOODS_RECEIVE_CONFIRM.getStr().equals(str)){
			return JDOrderStateEnum.WAIT_GOODS_RECEIVE_CONFIRM;
		}
		if(JDOrderStateEnum.FINISHED.getStr().equals(str)){
			return JDOrderStateEnum.FINISHED;
		}
		if(JDOrderStateEnum.TRADE_CANCELED.getStr().equals(str)){
			return JDOrderStateEnum.TRADE_CANCELED;
		}
		if(JDOrderStateEnum.LOCKED.getStr().equals(str)){
			return JDOrderStateEnum.LOCKED;
		}
		if(JDOrderStateEnum.PAUSE.getStr().equals(str)){
			return JDOrderStateEnum.PAUSE;
		}
		return JDOrderStateEnum.UNKOWN;
	}
}
