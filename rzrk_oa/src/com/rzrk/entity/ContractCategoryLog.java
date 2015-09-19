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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose:  user plan log, status change record                                                                  
 * @version 1.0                          
 * @author liujingjing
 * @since 2015-6-15 
 */
@Entity
public class ContractCategoryLog extends BaseEntity{
	

	/**  */
	private static final long serialVersionUID = -398702772933608784L;

	private Admin operator;
	
	private String content;
	
	private ContractCategory contractCategory;

	@ManyToOne()
	@JoinColumn
	@OrderBy("createDate asc")
	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(ContractCategory contractCategory) {
		this.contractCategory = contractCategory;
	}


	@ManyToOne
	@JoinColumn
	@OrderBy("createDate asc")
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
	
	
}