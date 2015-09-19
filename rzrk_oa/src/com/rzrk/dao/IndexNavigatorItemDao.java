/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.Admin;
import com.rzrk.entity.IndexNavigatorItem;

/**                                                                                                                                    
 * Purpose:    IndexNavigatorItemDao                                                                
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-7-10 
 */
public interface IndexNavigatorItemDao extends BaseDao<IndexNavigatorItem, String>{

	/**
	 * get all user list
	 * @param loginAdmin
	 * @return list
	 * @author songkez
	 * @since  2015-7-16 
	 */
	List<IndexNavigatorItem> getAllUserList(Admin loginAdmin);

	/**
	 * delete item by url
	 * @param temp
	 * @param loginAdmin
	 * @author songkez
	 * @since  2015-7-16 
	 */
	void deleteItemByUrl(String temp, Admin loginAdmin);

	/**
	 * TODO:Please add method comments
	 * @param string
	 * @param loginAdmin
	 * @return
	 * @author songkez
	 * @since  2015-7-16 
	 */
	IndexNavigatorItem getOneByUrl(String string, Admin loginAdmin);
	
	/**
	 * 删除用户的关注
	 * @param temp
	 * @param loginAdmin
	 */
	public void deleteItemByAdmin(Admin loginAdmin);

}
