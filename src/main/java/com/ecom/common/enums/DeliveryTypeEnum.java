package com.ecom.common.enums;

/**
 * 送货类型
 * @author lingkong
 * @date 2016年1月14日 下午11:56:16
 */
public enum DeliveryTypeEnum {
	//工作日
	WORK_DAY(1),
	//休息日
	REST_DAY(2),
	//不限
	ALL(3);
	private int id;
	private DeliveryTypeEnum(int id){
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
