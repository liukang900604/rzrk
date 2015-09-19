package com.rzrk.vo.contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.rzrk.util.JsonUtil;

public class Definition extends ArrayList<Field> {
	
	public Definition() {
	}
	
	public Definition(Collection<Field> fieldList){
		addAll(fieldList);
	}
	
	public Definition(String json){
		Definition _tmp = JsonUtil.toObject(json, Definition.class);
		addAll(_tmp);
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
	public void setPrimary(Field field){
		set(0, field);
	}
	
	public Field getPrimary(){
		return get(0);
	}
	
	public String[] getTitles(){
		String[] titleArr = new String[size()];
		for(int i=0;i<size();i++){
			titleArr[i] = get(i).getName();
		}
		return titleArr;
	}

	public String[] getTotals(){
		List<String> titleArr = new ArrayList<String>();
		for(Field field : this){
			if(field.isTotal()){
				titleArr.add(field.getName());
			}
		}
		return titleArr.toArray(new String[titleArr.size()]);
	}
	
	public boolean isChange(Definition other,Map<String, Object> result){
		boolean change = false;
		Collection<Field> inter = CollectionUtils.intersection(this, other);
		if(inter.size()>0){
			change=true;
			if(result!=null){
				result.put("self", CollectionUtils.subtract(this, inter));
				result.put("other", CollectionUtils.subtract(other, inter));
			}
		}
		return change;
	}

	public Field getField(String FieldName){
		for(Field field : this){
			if(StringUtils.equals(field.getName(),FieldName)){
				return field;
			}
		}
		return null;
	}
	
	public List<Field> getContentProviders(){
		List<Field> list = new ArrayList<Field>();
		for(Field field : this){
			if(field.isContentProvider()){
				list.add(field);
			}
		}
		return list;
	}

}
