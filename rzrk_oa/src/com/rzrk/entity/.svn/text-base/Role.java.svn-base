/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

import com.rzrk.util.JsonUtil;

/**
 * 实体类 - 管理角色
 */

@Entity
public class Role extends BaseEntity {

	private static final long serialVersionUID = -6614052029623997372L;
	
	public static final String ROLE_BASE = "ROLE_BASE";// 基础管理权限

	private String name;// 角色名称
	private Boolean isSystem;// 是否为系统内置角色
	private String description;// 描述
	private String authorityListStore;// 权限集合存储
	
	private String contractCategoryList;// 可以查看的二级分类
	
	private String projectCategoryList;// 可以查看的二级分类
	
	@Column(length = 30000)
	public String getProjectCategoryList() {
		return projectCategoryList;
	}

	public void setProjectCategoryList(String projectCategoryList) {
		this.projectCategoryList = projectCategoryList;
	}
	
	
	// 获取权限集合
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getPcList() {
		if (StringUtils.isEmpty(projectCategoryList)) {
			return new ArrayList<String>();
		}
		return JsonUtil.toObject(projectCategoryList, ArrayList.class);
	}
		
	// 设置二级分类集合
	@Transient
	public void setPcList(List<String> ccList) {
		if (ccList == null || ccList.size() == 0) {
			projectCategoryList = null;
			return;
		}
		projectCategoryList = JsonUtil.toJson(ccList);
	}
	

	private Set<Admin> adminSet = new HashSet<Admin>();// 人员
	

	@Column(length = 30000)
	public String getContractCategoryList() {
		return contractCategoryList;
	}

	public void setContractCategoryList(String contractCategoryList) {
		this.contractCategoryList = contractCategoryList;
	}

	
	// 获取权限集合
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getCcList() {
		if (StringUtils.isEmpty(contractCategoryList)) {
			return new ArrayList<String>();
		}
		return JsonUtil.toObject(contractCategoryList, ArrayList.class);
	}
		
	// 设置二级分类集合
	@Transient
	public void setCcList(List<String> ccList) {
		if (ccList == null || ccList.size() == 0) {
			contractCategoryList = null;
			return;
		}
		contractCategoryList = JsonUtil.toJson(ccList);
	}
	
	
	@Column(nullable = false)
	public String getName() {
		return name == null ? null : name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Column(length = 3000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(mappedBy = "roleSet", fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_role_admin_set")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@Column(length = 3000)
	public String getAuthorityListStore() {
		return authorityListStore;
	}

	public void setAuthorityListStore(String authorityListStore) {
		this.authorityListStore = authorityListStore;
	}
	
	// 获取权限集合
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getAuthorityList() {
		if (StringUtils.isEmpty(authorityListStore)) {
			return null;
		}
		return JsonUtil.toObject(authorityListStore, ArrayList.class);
	}
		
	// 设置权限集合
	@Transient
	public void setAuthorityList(List<String> roleList) {
		if (roleList == null || roleList.size() == 0) {
			authorityListStore = null;
			return;
		}
		authorityListStore = JsonUtil.toJson(roleList);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isSystem == null) {
			isSystem = false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isSystem == null) {
			isSystem = false;
		}
	}

}