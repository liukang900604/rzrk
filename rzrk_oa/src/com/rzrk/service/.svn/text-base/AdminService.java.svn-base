/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.List;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Service接口 - 人员
 */

public interface AdminService extends BaseService<Admin, String> {

	/**
	 * 获取当前登录人员,若未登录则返回null.
	 * 
	 * @return 当前登录人员对象
	 */
	public Admin getLoginAdmin();
	
	/**
	 * 获取当前登录人员(从数据库中加载),若未登录则返回null.
	 * 
	 * @return 当前登录人员对象
	 */
	public Admin loadLoginAdmin();
	
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
	 * 获取部门下的人员信息
	 */
	public List<DeparmentAdminVo> getAdminByDeparmentAndSub(String deparemtId);
	
	public List<String>  getAdminByJob(String jobId);

	public Pager findPager(Pager pager, String depamentId) ;

	/**
	 * TODO:Please add method comments
	 * @return
	 * @author songkez
	 * @since  2015-8-28 
	 */
	public List<Admin> getAllListExceptOutOfJob();
	
	
	/**
	 * 获取当前部门人员
	 */
	public List<Admin> getDeparmentList(Admin loginAdmin);

}