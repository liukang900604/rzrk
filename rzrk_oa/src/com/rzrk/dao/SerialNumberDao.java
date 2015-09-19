/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.Collection;
import java.util.List;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.SerialNumber;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Dao接口 - 人员
 */

public interface SerialNumberDao extends BaseDao<SerialNumber, String> {
	
	public long getAndSet(String type);
	
	
	
}