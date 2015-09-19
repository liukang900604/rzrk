package com.rzrk.util;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;

import com.rzrk.dao.SerialNumberDao;
import com.rzrk.service.SerialNumberService;

public class GenerateUtil {
    private static final AtomicLong ATOMIC_LONG = new AtomicLong();
//	public static String createPrimary(String name){
//		String py = CnSpellUtils.getPinYinHeadChar(name);
//		if(py.length()>4){
//			py = py.substring(0,4);
//		}
//		py = StringUtils.leftPad(py, 4, '0');
//		String primary = String.format("%4s-%s-%04d", py, com.rzrk.util.date.DateUtils.getCurrentDate("yyyyMMddHHmmss"),
//				ATOMIC_LONG.incrementAndGet()%1000);
//		return primary;
//	} 
	public static String createPrimary(String name){
		SerialNumberService serialNumberService =  (SerialNumberService) SpringUtil.getBean("serialNumberServiceImpl");
		long num = serialNumberService.getAndSet(name);
		String str = String.format("%08d", num);
		return str;
	} 
	
	public static void main(String[] args) {
		System.out.println(createPrimary("干戈"));
		System.out.println(createPrimary("fuk"));
	}
	
	
}
