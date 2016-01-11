package com.ecom.common;

import org.apache.commons.lang.StringUtils;

public class Utils {
	public static int yuanToFen(String yuan){
		if(StringUtils.isBlank(yuan)){
			return 0;
		}
		if(StringUtils.contains(yuan, '.')){
			String[] arr = StringUtils.split(yuan, '.');
			if(arr.length == 2){
				return Integer.valueOf(arr[0]) * 100 + Integer.valueOf(StringUtils.substring(arr[1], 0, 2));
			}
		}
		return Integer.valueOf(yuan) * 100;
	}
}
