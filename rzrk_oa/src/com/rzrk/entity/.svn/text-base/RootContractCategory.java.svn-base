/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 动态一级分类菜单
 */

@Entity
public class RootContractCategory extends BaseEntity {

	private static final long serialVersionUID = -513265210715164L;

	private String name;// 名称
	@Column(length=10240)
	private String comment;// 备注
	
	public enum ROOT_CATEGORY_TYPE{
		表格,项目模板
	}
	
	private ROOT_CATEGORY_TYPE rootCategoryType;
	
	private Set<ContractCategory> contractCategorySet = new HashSet<ContractCategory>();//  分类集合
	
	private Set<Project> projectCategorySet = new HashSet<Project>();//  项目集合
	
	
	@OneToMany(mappedBy = "rootContractCategory", fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	public Set<Project> getProjectCategorySet() {
		return projectCategorySet;
	}
	public void setProjectCategorySet(Set<Project> projectCategorySet) {
		this.projectCategorySet = projectCategorySet;
	}
	public ROOT_CATEGORY_TYPE getRootCategoryType() {
		return rootCategoryType;
	}
	public void setRootCategoryType(ROOT_CATEGORY_TYPE rootCategoryType) {
		this.rootCategoryType = rootCategoryType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@OneToMany(mappedBy = "rootContractCategory", fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	public Set<ContractCategory> getContractCategorySet() {
		return contractCategorySet;
	}
	public void setContractCategorySet(Set<ContractCategory> contractCategorySet) {
		this.contractCategorySet = contractCategorySet;
	}
}