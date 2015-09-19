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

import org.springframework.util.Assert;

import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;

/**
 * Dao接口 - 日志
 */

public interface DeparmentDao extends BaseDao<Deparment, String> {

	boolean isExistByName(String name);

	Deparment getMaxSortNo();
	public Deparment getByName(String name);
	public List<Deparment> getListByName(String name);
	Boolean checkIfHasRootDeparment(String id);
	/**
	 * 获取子部门集合
	 * @param deparmentSet
	 * @param deparment
	 */
	public void  getSubDeparment(Collection<Deparment> deparmentSet,Deparment  deparment);

	/**
	 * get deparment by name
	 * @param name
	 * @return
	 * @author songkez
	 * @since  2015-8-19 
	 */
	Deparment getDeparmentByName(String name);
	
    @Override
    public void delete(Deparment entity);
    @Override
    public void delete(String id);
    @Override
    public void delete(String[] ids);

}
