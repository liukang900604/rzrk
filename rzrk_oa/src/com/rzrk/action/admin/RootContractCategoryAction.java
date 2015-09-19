/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Role;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.service.AdminService;
import com.rzrk.service.RoleService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 文章
 */

@ParentPackage("admin")
public class RootContractCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;
	private RootContractCategory rootContractCategory;

	@Resource(name = "rootContractCategoryServiceImpl")
	private RootContractCategoryService rootContractCategoryService;
	
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	
	@Resource
	private AdminService adminService;

	public RootContractCategory getRootContractCategory() {
		return rootContractCategory;
	}

	public void setRootContractCategory(RootContractCategory rootContractCategory) {
		this.rootContractCategory = rootContractCategory;
	}

	// 添加
	public String add() {
		getRequest().setAttribute("rootContractCategoryTypeList", RootContractCategory.ROOT_CATEGORY_TYPE.values());
		return INPUT;
	}

	public String save(){
		rootContractCategoryService.save(rootContractCategory);
		Role role = roleService.getSuperRole();
		List<String> tempCCList = role.getCcList();
		tempCCList.add(rootContractCategory.getId());
		role.setCcList(tempCCList);
		roleService.update(role);
		return ajax(Status.success, "保存成功!");
	}
	
	// 编辑
	public String edit() {
		rootContractCategory = rootContractCategoryService.get(id);
		getRequest().setAttribute("rootContractCategoryTypeList", RootContractCategory.ROOT_CATEGORY_TYPE.values());
		
		return INPUT;
	}


	// 列表
	public String list() {
		return LIST;
	}
	
	
	// 删除
	public String delete() {
		try{
			rootContractCategoryService.delete(ids);
		}catch(Exception e){
			return ajax(Status.error, "删除失败，请检查数据是否被关联!");
		}
		return ajax(Status.success, "删除成功!");
	}
	
	@InputConfig(resultName = "error")
	public String update() {

		RootContractCategory persistent = rootContractCategoryService.load(id);
		BeanUtils.copyProperties(rootContractCategory, persistent, new String[] {"id", "createDate", "name"});

		rootContractCategoryService.update(persistent);
		redirectUrl = "root_contract_category!list.action";
		return SUCCESS;
	}
	
	public String checkRootName(){
		String name = rootContractCategory.getName();
		if (rootContractCategoryService.isExistByName(name)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	
	public String getAjaxList(){
		processAjaxPagerRequestParameter();
		pager = rootContractCategoryService.findPager(pager);
		List<RootContractCategory> ccList = (List<RootContractCategory>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < ccList.size(); i++ ){
			RootContractCategory temp = ccList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        map.put("comment",temp.getComment()==null?"":temp.getComment());
	        map.put("rootCategoryType",temp.getRootCategoryType());//分类模板类型
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
		
	}

}