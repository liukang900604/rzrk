package com.rzrk.util;

import java.math.BigDecimal;

public class NumberUtil {
	

	public static double getFormatedDouble(double number, int range){
		BigDecimal   b   =   new   BigDecimal(number);  
		return b.setScale(range,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}

	
	public static void main(String args[]){
		System.out.println(getFormatedDouble(1.1111,2));
	}
}
