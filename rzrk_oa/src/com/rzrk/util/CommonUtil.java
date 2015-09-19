/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.oscache.util.StringUtil;
import com.rzrk.entity.Job;

/**
 * 工具类 - 公用
 */

public class CommonUtil {

	public static final String WEB_APP_ROOT_KEY = "rzrk.webAppRoot";// WebRoot路径KEY
	public static final String PATH_PREPARED_STATEMENT_UUID = "\\{uuid\\}";// UUID路径占位符
	public static final String PATH_PREPARED_STATEMENT_DATE = "\\{date(\\(\\w+\\))?\\}";// 日期路径占位符

	/**
	 * 获取WebRoot路径
	 * 
	 * @return WebRoot路径
	 */
	public static String getWebRootPath() {
		return System.getProperty(WEB_APP_ROOT_KEY);
	}

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	/**
	 * 获取实际路径
	 * 
	 * @param path
	 *            路径
	 */
	public static String getPreparedStatementPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		StringBuffer uuidStringBuffer = new StringBuffer();
		Matcher uuidMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_UUID)
				.matcher(path);
		while (uuidMatcher.find()) {
			uuidMatcher.appendReplacement(uuidStringBuffer,
					CommonUtil.getUUID());
		}
		uuidMatcher.appendTail(uuidStringBuffer);

		StringBuffer dateStringBuffer = new StringBuffer();
		Matcher dateMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_DATE)
				.matcher(uuidStringBuffer.toString());
		while (dateMatcher.find()) {
			String dateFormate = "yyyyMM";
			Matcher dateFormatMatcher = Pattern.compile("\\(\\w+\\)").matcher(
					dateMatcher.group());
			if (dateFormatMatcher.find()) {
				String dateFormatMatcherGroup = dateFormatMatcher.group();
				dateFormate = dateFormatMatcherGroup.substring(1,
						dateFormatMatcherGroup.length() - 1);
			}
			dateMatcher.appendReplacement(dateStringBuffer,
					new SimpleDateFormat(dateFormate).format(new Date()));
		}
		dateMatcher.appendTail(dateStringBuffer);

		return dateStringBuffer.toString();
	}
	
	
	public static JSONArray getJobJsonArray(List<Job> jobList){
		
		JSONArray jsonObjTotal = new JSONArray();
		
        JSONArray jsonArray[] = new JSONArray[Job.JobType.values().length]; 
        
		for(Job job : jobList){
			Map<String,Object> map = new HashMap<String,Object>(); 
			map.put("id", job.getId());
			map.put("name", job.getJobName());
			if (jsonArray[job.getJobType().ordinal()] == null){
				jsonArray[job.getJobType().ordinal()] = new JSONArray();
			}
			jsonArray[job.getJobType().ordinal()].add(map);
		}
		
		for(int i=0; i<Job.JobType.values().length; i ++ ){
			Map<String,Object> map = new HashMap<String,Object>(); 
			map.put("typeid", i+"");
			map.put("typename", Job.JobType.values()[i]);
			if (jsonArray[i] == null){
				map.put("types", new JSONArray());
			}else{
				map.put("types", jsonArray[i]);
			}
			
			jsonObjTotal.add(map);
		}
		return jsonObjTotal;
	}
	
	public static String getTimeNo(){
		return com.rzrk.util.date.DateUtils.getCurrentDate("yyyyMMddHHmmss")+com.rzrk.util.StringUtils.randomNumnber(3); 
	}

}