package net.sf.json;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.rzrk.exception.PersonalException;
import com.rzrk.util.JsonUtil;

public class JSONObject extends LinkedHashMap<String, Object>{
	
	public static JSONObject fromObject(Object obj){
		if(obj==null){
			return null;
		}else if(obj instanceof CharSequence){
			return JsonUtil.toObject(obj.toString(), JSONObject.class);
		}else if(obj instanceof Map){
			JSONObject jobj = new JSONObject();
			jobj.putAll((Map)obj);
			return jobj;
		}else{
			JSONObject jobj = new JSONObject();
			try {
				Map  _map = BeanUtils.describe(obj);
				_map.remove("class");
				jobj.putAll(_map);
				return jobj;
			} catch (Exception e) {
				throw new PersonalException("bean to map 出错:"+e.getMessage());
			}
		}
	}
	
	public String getString(String key){
		Object temp = get(key);
		if(temp==null){
			return null;
		}else{
			return temp.toString();
		}
	}
	
	public int getInt(String key){
		Object temp = get(key);
		if(temp==null){
			throw new NumberFormatException("空值无法转化为数字");
		}else if(temp instanceof Number){
			return ((Number)temp).intValue();
		}else{
			return Integer.valueOf(temp.toString());
		}
	}

	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
