/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rzrk.dao.ContractCategoryLogDao;
import com.rzrk.dao.UserPlanDao;
import com.rzrk.dao.UserPlanLogDao;
import com.rzrk.entity.ContractCategoryLog;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.service.ContractCategoryLogService;
import com.rzrk.service.UserPlanLogService;

/**
 * Service实现类 - 日志
 */

@Service("contractCategoryLogServiceImpl")
public class ContractCategoryLogServiceImpl extends BaseServiceImpl<ContractCategoryLog, String> implements ContractCategoryLogService {
	
	@Resource(name = "contractCategoryLogDaoImpl")
	private ContractCategoryLogDao contractCategoryLogDao;

	@Resource(name = "contractCategoryLogDaoImpl")
	public void setBaseDao(ContractCategoryLogDao contractCategoryLogDao) {
		super.setBaseDao(contractCategoryLogDao);
	}


	@Override
	public List<ContractCategoryLog> getByContractCategoryId(String id) {
		return contractCategoryLogDao.getByContractCategoryId(id);
	}

}