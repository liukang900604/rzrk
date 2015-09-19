package net.sf.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.rzrk.util.JsonUtil;

public class JSONArray extends ArrayList<Object>{
	
	public static JSONArray fromObject(Object obj){
		if(obj==null){
			return null;
		}else if(obj instanceof CharSequence){
			return JsonUtil.toObject(obj.toString(), JSONArray.class);
		}else if(obj.getClass().isArray()){
			return fromObject((Object[]) obj);
		}else{
			JSONArray jarr = new JSONArray();
			for(Object item : (Collection<?>)obj){
				jarr.add(item);
			}
			return jarr;
		}
	}
	public static JSONArray fromObject(Object[] obj){
		if(obj==null){
			return null;
		}else{
			JSONArray jarr = new JSONArray();
			for(Object item : obj){
				jarr.add(item);
			}
			return jarr;
		}
	}
	public String getString(int index){
		Object temp = get(index);
		if(temp==null){
			return null;
		}else{
			return temp.toString();
		}
	}
	
	public int getInt(int index){
		Object temp = get(index);
		if(temp==null){
			throw new NumberFormatException("空值无法转化为数字");
		}else if(temp instanceof Number){
			return ((Number)temp).intValue();
		}else{
			return Integer.valueOf(temp.toString());
		}
	}
	
	
	public JSONArray getJSONArray(int i){
		Object obj = get(i);
		return JSONArray.fromObject(obj);
	}
	
	public JSONObject getJSONObject(int i){
		Object obj = get(i);
		return JSONObject.fromObject(obj);
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

}
