/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.bean.Pager;
import com.rzrk.entity.SystemMessage;

/**
 * Dao接口 - 日志
 */

public interface SystemMessageDao extends BaseDao<SystemMessage, String> {

	boolean isExistByName(String title);

	int getUnredCount(String id);

	List<SystemMessage> getAdminMessage(String id, Pager pager);

	List<SystemMessage> getMessage(String id, Pager pager);
	

}
