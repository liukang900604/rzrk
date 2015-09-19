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

import com.rzrk.bean.Pager;
import com.rzrk.dao.SystemMessageDao;
import com.rzrk.entity.SystemMessage;
import com.rzrk.service.SystemMessageService;
import com.rzrk.util.StrUtil;

/**
 * Service实现类 - 日志
 */

@Service("systemMessageServiceImpl")
public class SystemMessageServiceImpl extends BaseServiceImpl<SystemMessage, String> implements SystemMessageService {

	@Resource(name = "systemMessageDaoImpl")
	private SystemMessageDao systemMessageDao;
	@Resource(name = "systemMessageDaoImpl")
	public void setBaseDao(SystemMessageDao systemMessageDao) {
		super.setBaseDao(systemMessageDao);
	}

	@Override
	public boolean isExistByName(String title) {
		
		return systemMessageDao.isExistByName(title);
	}

	@Override
	public int getUnredCount(String id) {
		// TODO Auto-generated method stub
		return systemMessageDao.getUnredCount(id);
	}

	@Override
	public List<SystemMessage> getAdminMessage(String id, Pager pager) {
		// TODO Auto-generated method stub
		return systemMessageDao.getAdminMessage(id,pager);
	}

	@Override
	public List<SystemMessage> getMessage(String id, Pager pager) {
		return systemMessageDao.getMessage(id,pager);
	}
	
	public void markType(String[] ids ){
		StringBuffer sql = new StringBuffer("update rzrk_system_message set red_type = 1 ");
		if(ids != null && ids.length > 0){
			sql.append(" where id in (").append(StrUtil.toString(ids)).append(")");
		}
		this.getBaseDao().getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
}