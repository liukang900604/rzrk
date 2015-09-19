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
import com.rzrk.entity.Deparment;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Dao接口 - 人员
 */

public interface AdminDao extends BaseDao<Admin, String> {
	
	/**
	 * 根据用户名判断此人员是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取人员,若人员不存在,则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username);
	public Admin getAdminByName(String name);
	public List<Admin> getAdminListByName(String name);
	
	public Admin getMaxSortNo();
	
	/**
	 * 获取部门及其以下下人员信息
	 * @return
	 */
	public List<DeparmentAdminVo> getAdminByDeparmentAndSub(String deparemtId);

	/**
	 * 获取部门及其以下下人员信息
	 * @return
	 */
	public List<DeparmentAdminVo> getAdminByDeparment(String deparmentId);

	public Pager findPager(Pager pager, Collection<String> depamentIdList) ;

	/**
	 * TODO:Please add method comments
	 * @return
	 * @author songkez
	 * @since  2015-8-28 
	 */
	public List<Admin> getAllListExceptOutOfJob();
	
	
	/**
	 * 通过岗位获取人员
	 * @param jobId
	 * @return
	 */
	public List<String>  getAdminByJob(String jobId);
}