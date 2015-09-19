/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rzrk.dao.RootContractCategoryDao;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;
import com.rzrk.service.RootContractCategoryService;

/**
 * Service实现类 - 文章分类
 */

@Service("rootContractCategoryServiceImpl")
public class RootContractCategoryServiceImpl extends BaseServiceImpl<RootContractCategory, String> implements RootContractCategoryService {

	@Resource(name = "rootContractCategoryDaoImpl")
	private RootContractCategoryDao rootContractCategoryDao;
	
	@Resource(name = "rootContractCategoryDaoImpl")
	public void setBaseDao(RootContractCategoryDao rootContractCategoryDao) {
		super.setBaseDao(rootContractCategoryDao);
	}

	@Override
	public boolean isExistByName(String name) {
		return rootContractCategoryDao.isExistByName(name);
	}
	
	
	public ArrayList<?> getListWithChildAndFields(){
		List<RootContractCategory> ccList = getAllList();
		ArrayList<Map<?,?>> rootList = new ArrayList<Map<?,?>>();
		if(ccList != null && ccList.size() > 0){
			for(RootContractCategory root :ccList){
		        Map<String,Object> mapRoot = new HashMap<String,Object>(); 
		        mapRoot.put("id", root.getId()); 
		        mapRoot.put("name", root.getName());
		        mapRoot.put("text", root.getName());
				ArrayList<Map<?,?>> childList = new ArrayList<Map<?,?>>();
		        for(ContractCategory child :  root.getContractCategorySet()){
			        Map<String,Object> mapChild = new HashMap<String,Object>(); 
			        mapChild.put("id", child.getId());
			        mapChild.put("name", child.getName());
			        mapChild.put("text", child.getName());
			        mapChild.put("fields", StringUtils.join(child.getDefinitionObj().getTitles(),","));
			        mapChild.put("definition", child.getDefinitionObj());
			        childList.add(mapChild);
		        }
		        mapRoot.put("children", childList);
		        rootList.add(mapRoot);
			}
		}
		return rootList;
		
	}
	/**
	 * 一级分类+二级分类+主键
	 */
	public ArrayList<?> getListWithChild(){
		List<RootContractCategory> ccList = getAllList();
		ArrayList<Map<?,?>> rootList = new ArrayList<Map<?,?>>();
		if(ccList != null && ccList.size() > 0){
			for(RootContractCategory root :ccList){
		        Map<String,Object> mapRoot = new HashMap<String,Object>(); 
		        mapRoot.put("id", root.getId()); 
		        mapRoot.put("name", root.getName());
		        mapRoot.put("text", root.getName());
				ArrayList<Map<?,?>> childList = new ArrayList<Map<?,?>>();
		        for(ContractCategory child :  root.getContractCategorySet()){
			        Map<String,Object> mapChild = new HashMap<String,Object>(); 
			        mapChild.put("id", child.getId());
			        mapChild.put("name", child.getName());
			        mapChild.put("text", child.getName());
			        mapChild.put("primaryField", child.getDefinitionObj().getPrimary());
			        childList.add(mapChild);
		        }
		        mapRoot.put("children", childList);
		        rootList.add(mapRoot);
			}
		}
		return rootList;
		
	}

	public ArrayList<?> getListWithChildAll(){
		List<RootContractCategory> ccList = getAllList();
		ArrayList<Map<?,?>> rootList = new ArrayList<Map<?,?>>();
		if(ccList != null && ccList.size() > 0){
			for(RootContractCategory root :ccList){
		        Map<String,Object> mapRoot = new HashMap<String,Object>(); 
		        mapRoot.put("id", root.getId()); 
		        mapRoot.put("name", root.getName());
		        mapRoot.put("text", root.getName());
				ArrayList<Map<?,?>> childList = new ArrayList<Map<?,?>>();
		        for(ContractCategory child :  root.getContractCategorySet()){
			        Map<String,Object> mapChild = new HashMap<String,Object>(); 
			        mapChild.put("id", child.getId());
			        mapChild.put("name", child.getName());
			        mapChild.put("text", child.getName());
			        mapChild.put("primaryField", child.getDefinitionObj().getPrimary());
			        childList.add(mapChild);
		        }
		        mapRoot.put("children", childList);
		        rootList.add(mapRoot);
			}
		}
		return rootList;
		
	}

	/*  @see com.rzrk.service.RootContractCategoryService#getByType()  */
	@Override
	public List<RootContractCategory> getByType(ROOT_CATEGORY_TYPE type) {
		return rootContractCategoryDao.getByType(type);
	}

}