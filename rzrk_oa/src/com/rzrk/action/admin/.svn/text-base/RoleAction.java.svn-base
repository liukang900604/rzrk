/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Project;
import com.rzrk.entity.Role;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;
import com.rzrk.service.RoleService;
import com.rzrk.service.RootContractCategoryService;

/**
 * 后台Action类 - 管理角色
 */

@ParentPackage("admin")
public class RoleAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Role role;

	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	
	@Resource(name = "rootContractCategoryServiceImpl")
	private RootContractCategoryService rootContractCategoryService;

	// 列表
	public String list() {
		pager = roleService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception{
		for (String id : ids) {
			Role role = roleService.load(id);
			Set<Admin> adminSet = role.getAdminSet();
			if (adminSet != null && adminSet.size() > 0) {
				return ajax(Status.error, "角色[" + role.getName() + "]下存在人员,删除失败!");
			}
		}
		StringBuffer logInfoStringBuffer = new StringBuffer();
		for(String temp : ids){
			Role deleteProject = roleService.get(temp);
			logInfoStringBuffer.append(deleteProject.getName() +  " ");
		}
		try {
			roleService.delete(ids);
			logInfo = "删除角色: " + logInfoStringBuffer.toString();
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			logInfo = "删除角色失败: " +e.getMessage();
			e.printStackTrace();
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}

	public List<RootContractCategory> getRootContractCagetoryList(){
		return rootContractCategoryService.getAllList();
	}
	
	public List<RootContractCategory> getRootProjectCagetoryList(){
		return rootContractCategoryService.getAllList();
	}
	
	// 添加
	public String add() {
		getRequest().setAttribute("superAuthorities", new ArrayList<String>());
		return INPUT;
	}

	// 编辑
	public String edit() {
		role = roleService.load(id);
		getRequest().setAttribute("superAuthorities", roleService.getSuperRole().getAuthorityList());
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		Boolean exsits = roleService.checkNameExsits(role);
		if(exsits){
			addActionError("角色名称已经存在!");
			return ERROR;
		}
		List<String> authorityList = (role.getAuthorityList()==null? new ArrayList<String> (): role.getAuthorityList());
		authorityList.add(Role.ROLE_BASE);
		role.setAuthorityList(authorityList);
		role.setCcList(role.getCcList());
		role.setPcList(role.getPcList());
		roleService.save(role);
		logInfo = "添加角色: " + role.getName();
		redirectUrl = "role!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		role.setId(id);
		Boolean exsits = roleService.checkNameExsits(role);
		if(exsits){
			addActionError("角色名称已经存在!");
			return ERROR;
		}
		Role persistent = roleService.load(id);
		List<String> authorityList = (role.getAuthorityList()==null? new ArrayList<String> (): role.getAuthorityList());
		authorityList.add(Role.ROLE_BASE);
		role.setAuthorityList(authorityList);
		role.setCcList(role.getCcList());
		if (persistent.getIsSystem()) {
			addActionError("系统内置角色不允许修改!");
			return ERROR;
		}
		BeanUtils.copyProperties(role, persistent, new String[] {"id", "createDate", "modifyDate", "isSystem", "adminSet"});
		roleService.update(persistent);
		logInfo = "编辑角色: " + role.getName();
		redirectUrl = "role!list.action";
		return SUCCESS;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}