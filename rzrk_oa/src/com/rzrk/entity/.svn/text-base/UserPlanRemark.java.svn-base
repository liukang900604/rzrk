/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose:  user plan remark                                                                  
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-6-15 
 */
@Entity
public class UserPlanRemark extends BaseEntity{
	

	public enum RemarkType{
		计划, 项目
	}//消息类别枚举
	
	/**  */
	private static final long serialVersionUID = 5270295066758141866L;
	
	private String content; //评论内容
	
	private Admin admin;//评论人
	
	private UserPlan userPlan; //userplan id
	
	private Project project; //project id
	
	private RemarkType type;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="admin")
	@ForeignKey(name = "fk_admin")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userPlan")
	@ForeignKey(name = "fk_admin")
	public UserPlan getUserPlan() {
		return userPlan;
	}

	public void setUserPlan(UserPlan userPlan) {
		this.userPlan = userPlan;
	}
	
	
	@Column
	public RemarkType getType() {
		return type;
	}

	public void setType(RemarkType type) {
		this.type = type;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project")
	@ForeignKey(name = "fk_admin")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
