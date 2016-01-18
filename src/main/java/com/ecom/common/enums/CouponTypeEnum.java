package com.ecom.common.enums;

public enum CouponTypeEnum {
	//20-套装优惠, 28-闪团优惠, 29-团购优惠, 30-单品促销优惠, 34-手机红包, 35-满返满送(返现), 
	//39-京豆优惠,41-京东券优惠, 52-礼品卡优惠,100-店铺优惠
	UNKOWN(-1),
	TZ(20),
	ST(28),
	TG(29),
	DPCX(30),
	MHB(34),
	MF(35),
	JD(39),
	JDJ(41),
	LPK(52),
	DP(100);
	private int id;
	
	private CouponTypeEnum(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	 public static CouponTypeEnum parse(String s){
		 if(s.equals("20-套装优惠")){
			 return CouponTypeEnum.TZ;
		 }
		 if(s.equals("28-闪团优惠")){
			 return CouponTypeEnum.ST;
		 }
		 if(s.equals("29-团购优惠")){
			 return CouponTypeEnum.TG;
		 }
		 if(s.equals("30-单品促销优惠")){
			 return CouponTypeEnum.DPCX;
		 }
		 if(s.equals("34-手机红包")){
			 return CouponTypeEnum.MHB;
		 }
		 if(s.equals("35-满返满送(返现)")){
			 return CouponTypeEnum.MF;
		 }
		 if(s.equals("39-京豆优惠")){
			 return CouponTypeEnum.JD;
		 }
		 if(s.equals("41-京东券优惠")){
			 return CouponTypeEnum.JDJ;
		 }
		 if(s.equals("52-礼品卡优惠")){
			 return CouponTypeEnum.LPK;
		 }
		 if(s.equals("100-店铺优惠")){
			 return CouponTypeEnum.DP;
		 }
		 return CouponTypeEnum.UNKOWN;
	 }

}
