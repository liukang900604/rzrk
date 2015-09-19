/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.ContractField;
import com.rzrk.entity.Work;

/**
 * Dao接口 - 我的工作
 */

public interface WorkDao extends BaseDao<Work, String> {
	public List<Work> getByContractFieldPrimary(ContractField primaryField);
}