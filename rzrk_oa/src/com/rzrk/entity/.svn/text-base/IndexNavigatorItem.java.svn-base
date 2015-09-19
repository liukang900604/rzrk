/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose:  首页导航快捷方式                                                                  
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-7-10 
 */
@Entity
public class IndexNavigatorItem  extends BaseEntity{
	
	/**  */
	private static final long serialVersionUID = -4228575044858777141L;

	private String name; //二级菜单名称
	
	private String url; //url路径
	
	private int listMaxCount;//每页显示的条数
	
	private int lineMaxCount;//每行显示的条数
	
	private Admin admin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_admin_indexnavigatoritem")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getListMaxCount() {
		return listMaxCount;
	}

	public void setListMaxCount(int listMaxCount) {
		this.listMaxCount = listMaxCount;
	}

	public int getLineMaxCount() {
		return lineMaxCount;
	}

	public void setLineMaxCount(int lineMaxCount) {
		this.lineMaxCount = lineMaxCount;
	}
	
}
