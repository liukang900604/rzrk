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

import com.rzrk.dao.GroupInfoDao;
import com.rzrk.dao.JobDao;
import com.rzrk.dao.LogDao;
import com.rzrk.entity.GroupInfo;
import com.rzrk.entity.Job;
import com.rzrk.entity.Log;
import com.rzrk.service.GroupInfoService;
import com.rzrk.service.JobService;
import com.rzrk.service.LogService;

/**
 * Service实现类 - 日志
 */

@Service("groupInfoServiceImpl")
public class GroupInfoServiceImpl extends BaseServiceImpl<GroupInfo, String> implements GroupInfoService {

	@Resource(name = "groupInfoDaoImpl")
	public void setBaseDao(GroupInfoDao groupInfoDao) {
		super.setBaseDao(groupInfoDao);
	}

}