///**
// * Project Name: rzrk Web
// * Confidential and Proprietary                                                                
// * Copyright (C) 2013 By                                                                                     
// * rzrk Company                 
// * All Rights Reserved                                                                                                                                                                                                                       
// */
//package com.rzrk.entity;
//
//import java.util.HashSet;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.OrderBy;
//
//import org.hibernate.annotations.ForeignKey;
//
///**
// * 实体类 - 文章分类
// */
//
//@Entity
//public class ProjectCategory extends BaseEntity {
//
//	private static final long serialVersionUID = -513265210715164L;
//
//	private String name;// 名称
//	
//	private String remark;//备注
//	
//	private RootContractCategory rootContractCategory;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(nullable = false)
//	@ForeignKey(name = "fk_project_root_contract_category")
//	public RootContractCategory getRootContractCategory() {
//		return rootContractCategory;
//	}
//	public void setRootContractCategory(RootContractCategory rootContractCategory) {
//		this.rootContractCategory = rootContractCategory;
//	}
//
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//	
//}