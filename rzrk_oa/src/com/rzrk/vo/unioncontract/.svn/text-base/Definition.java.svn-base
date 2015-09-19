package com.rzrk.vo.unioncontract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rzrk.util.JsonUtil;

public class Definition  extends ArrayList<Field> {
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
	

	public Set<String> getTableSet(){
		Set<String> tableIdSet = new LinkedHashSet<String>();
		Iterator<Field> ite = iterator();
		while(ite.hasNext()){
			Field field = ite.next();
			if(field.getType()==Field.TYPE_TABLE){
				tableIdSet.add(field.getTableName());
			}
		}
		return tableIdSet;
	}
	public Set<String> getTableIdSet(){
		Set<String> tableIdSet = new LinkedHashSet<String>();
		Iterator<Field> ite = iterator();
		while(ite.hasNext()){
			Field field = ite.next();
			if(field.getType()==Field.TYPE_TABLE){
				tableIdSet.add(field.getTableId());
			}
		}
		return tableIdSet;
	}
	public String getTableIdByName(String name){
		Iterator<Field> ite = iterator();
		while(ite.hasNext()){
			Field field = ite.next();
			if(field.getType()==Field.TYPE_TABLE && field.getTableName().equalsIgnoreCase(name)){
				return field.getTableId();
			}
		}
		return "";
	}
	public String getTableNameById(String id){
		Iterator<Field> ite = iterator();
		while(ite.hasNext()){
			Field field = ite.next();
			if(field.getType()==Field.TYPE_TABLE && field.getTableId().equalsIgnoreCase(id)){
				return field.getTableName();
			}
		}
		return "";
	}
	
	public Definition getTableFileds(){
		Definition def = new Definition();
		Iterator<Field> ite = iterator();
		while(ite.hasNext()){
			Field field = ite.next();
			if(field.getType()==Field.TYPE_TABLE){
				def.add(field);
			}
		}
		return def;
	}
	public Definition getExpressionFileds(){
		Definition def = new Definition();
		Iterator<Field> ite = iterator();
		while(ite.hasNext()){
			Field field = ite.next();
			if(field.getType()==Field.TYPE_EXPRESSION){
				def.add(field);
			}
		}
		return def;
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
			titleArr[i] = get(i).getShowField();
		}
		return titleArr;
	}

	public String[] getTotals(){
		List<String> titleArr = new ArrayList<String>();
		for(Field field : this){
			if(field.isTotal()){
				titleArr.add(field.getShowField());
			}
		}
		return titleArr.toArray(new String[titleArr.size()]);
	}

}
