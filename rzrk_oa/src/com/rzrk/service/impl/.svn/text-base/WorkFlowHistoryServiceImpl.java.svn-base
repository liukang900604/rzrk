/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.dao.MemberProductHoldLogDao;
import com.rzrk.dao.WorkFlowHistoryDao;
import com.rzrk.entity.WorkFlowHistory;
import com.rzrk.service.WorkFlowHistoryService;


@Transactional
@Service
public class WorkFlowHistoryServiceImpl extends BaseServiceImpl<WorkFlowHistory, String> implements WorkFlowHistoryService {

	@Resource
	public WorkFlowHistoryDao workFlowHistoryDao;
	
	@Resource(name = "workFlowHistoryDaoImpl")
	public void setBaseDao(WorkFlowHistoryDao typeDao){
		super.setBaseDao(typeDao);
	}

}