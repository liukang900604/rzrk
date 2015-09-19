package com.rzrk.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashSet;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.math.NumberUtils;

public class JsUtil {
	private static ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");

	public synchronized static Object eval(String str){
		Object eval = null;
        try {
            eval = se.eval(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eval;
	}
	
	public static Number getNumber(String str){
		Double num = (Double)eval(str);
		if(num!=null || num!=Double.NaN){
			return num;
		}else{
			return 0;
		}
	}
	
	
	public static void main(String[] args) {
		String str = "(3+40000000000)*3";
		Number test = getNumber(str);
		BigDecimal gd = BigDecimal.valueOf(test.doubleValue());
		if(gd.doubleValue() == gd.longValue()){
			System.out.println(gd.toBigInteger().toString());
		}else{
			System.out.println(BigDecimal.valueOf(test.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
		}

	}
}
