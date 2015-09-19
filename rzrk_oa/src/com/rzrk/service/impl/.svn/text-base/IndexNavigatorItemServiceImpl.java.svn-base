/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rzrk.dao.IndexNavigatorItemDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.IndexNavigatorItem;
import com.rzrk.service.IndexNavigatorItemService;

/**
 * Service实现类 - indexNavigatorItemServiceImpl
 */

@Service("indexNavigatorItemServiceImpl")
public class IndexNavigatorItemServiceImpl extends BaseServiceImpl<IndexNavigatorItem, String> implements IndexNavigatorItemService {
	
	@Resource(name = "indexNavigatorItemDaoImpl")
	private IndexNavigatorItemDao indexNavigatorItemDao;

	@Resource(name = "indexNavigatorItemDaoImpl")
	public void setBaseDao(IndexNavigatorItemDao indexNavigatorItemDao) {
		super.setBaseDao(indexNavigatorItemDao);
	}

	/*  @see com.rzrk.service.IndexNavigatorItemService#getAllUrlList()  */
	@Override
	public List<String> getAllUrlList(Admin loginAdmin) {
		List<IndexNavigatorItem> list = indexNavigatorItemDao.getAllUserList(loginAdmin);
		List<String> urlList = new ArrayList<String>();
		for(IndexNavigatorItem temp : list){
			urlList.add(temp.getUrl());
		} 
		return urlList;
	}

	/*  @see com.rzrk.service.IndexNavigatorItemService#getAllUserList(com.rzrk.entity.Admin)  */
	@Override
	public List<IndexNavigatorItem> getAllUserList(Admin loginAdmin) {
		return indexNavigatorItemDao.getAllUserList(loginAdmin);
	}

	/*  @see com.rzrk.service.IndexNavigatorItemService#deleteItemByUrl(java.lang.String, com.rzrk.entity.Admin)  */
	@Override
	public void deleteItemByUrl(String temp, Admin loginAdmin) {
		indexNavigatorItemDao.deleteItemByUrl(temp, loginAdmin);
		
	}
	
	/**
	 * 删除用户的关注
	 * @param temp
	 * @param loginAdmin
	 */
	public void deleteItemByAdmin(Admin loginAdmin){
		indexNavigatorItemDao. deleteItemByAdmin(loginAdmin);
	}

	/*  @see com.rzrk.service.IndexNavigatorItemService#getOneByUrl(java.lang.String, com.rzrk.entity.Admin)  */
	@Override
	public IndexNavigatorItem getOneByUrl(String string, Admin loginAdmin) {
		return indexNavigatorItemDao.getOneByUrl(string, loginAdmin);
	}
	
}