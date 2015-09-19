package com.rzrk.common.product;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class ExtractDateManager {
	Map<String, ExtractDateStrategy> extractDateStrategyMap = new HashMap<String, ExtractDateStrategy>();
	
	public ExtractDateManager() {
		extractDateStrategyMap.put("年封季开", new ExtractDateStrategyNFJK());
	}
	public java.sql.Date getExtractDate(String type,java.sql.Date date,int n){
		ExtractDateStrategy extractDateStrategy = extractDateStrategyMap.get(type);
		if(extractDateStrategy!=null){
			return extractDateStrategy.getExtractDate(date.getYear()+1900, date.getMonth()+1, date.getDate(), n);
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
		Date now = new Date(2014-1900,8-1,6);
		java.sql.Date snow = new java.sql.Date(now.getTime());
		ExtractDateManager em = new ExtractDateManager();
		em.getExtractDate("年封季开", new java.sql.Date(now.getTime()), 0);
		em.getExtractDate("年封季开", new java.sql.Date(now.getTime()), 1);
		em.getExtractDate("年封季开", new java.sql.Date(now.getTime()), 2);
		em.getExtractDate("年封季开", new java.sql.Date(now.getTime()), 3);
	}
}
