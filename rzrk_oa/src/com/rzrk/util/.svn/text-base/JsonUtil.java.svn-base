/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;

/**
 * Util - JSON
 * Revision: 2.1
 * Date: 2012-01
 */

public class JsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
//	static{
//		mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//
//			@Override
//			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
//				arg1.writeString("");  
//			}
//		});
//	}
	/**
	 * 将对象转换为JSON字符串
	 * @param object 对象
	 */
	public static String toJson(Object object) {
		Assert.notNull(object);
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转换为JSON流
	 * @param response HttpServletResponse
	 * @param contentType contentType
	 * @param object 对象
	 */
	public static void toJson(HttpServletResponse response, String contentType, Object value) {
		Assert.notNull(response);
		Assert.notNull(contentType);
		Assert.notNull(value);
		try {
			response.setContentType(contentType);
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象转换为JSON流
	 * @param response HttpServletResponse
	 * @param object 对象
	 */
	public static void toJson(HttpServletResponse response, Object value) {
		Assert.notNull(response);
		Assert.notNull(value);
		try {
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param valueType 对象类型
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.notNull(json);
		Assert.notNull(valueType);
		try {
			return (T)mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param valueType 对象类型
	 */
	public static <T> T toObject(String json, TypeReference<T> typeReference) {
		Assert.notNull(json);
		Assert.notNull(typeReference);
		try {
			return (T)mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param typeReference 对象类型
	 */
//	public static <T> T toObject(String json, TypeReference<?> typeReference) {
//		Assert.notNull(json);
//		Assert.notNull(typeReference);
//		try {
//			return mapper.readValue(json, typeReference);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param javaType 对象类型
	 */
//	public static <T> T toObject(String json, JavaType javaType) {
//		Assert.notNull(json);
//		Assert.notNull(javaType);
//		try {
//			return mapper.readValue(json, javaType);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		String str = "[123,567.2,\"555\"]";
		JSONArray jarr = JSONArray.fromObject(str);
//		System.out.println(jarr.getString(1));
		System.out.println(jarr.getString(1));
////		JSONArray jarr = new JSONArray();
//		ArrayList<Object> jarr = new ArrayList<Object>();
//		Map<String, Object> map =new HashMap<String, Object>();
//		map.put("nima", "geegeg");
//		map.put("hh", null);
//		jarr.add(map);
////		JSONObject jobj = jarr.getJSONObject(0);
//		ObjectMapper mapper = new ObjectMapper();
//		String res = mapper.writeValueAsString(jarr);
//		ArrayList list = mapper.readValue(res, ArrayList.class);
//	System.out.println(res);
	}


}