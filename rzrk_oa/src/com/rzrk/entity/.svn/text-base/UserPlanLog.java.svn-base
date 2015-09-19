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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose:  user plan log, status change record                                                                  
 * @version 1.0                          
 * @author songkez
 * @since 2015-6-15 
 */
@Entity
public class UserPlanLog extends BaseEntity{
	
	public enum EVENT {
		
	}

	/**  */
	private static final long serialVersionUID = -398702772933608784L;

	private Admin operator;
	
	private String content;
	
	private UserPlan userPlan;
	
	private Project project;
	

	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_log_user_plan")
	public UserPlan getUserPlan() {
		return userPlan;
	}

	public void setUserPlan(UserPlan userPlan) {
		this.userPlan = userPlan;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_operator_admin")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(length=10240)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk__project__project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
	
}