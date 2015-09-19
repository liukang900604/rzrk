/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;

/**
 * Service接口 - 日志
 */

public interface DeparmentService extends BaseService<Deparment, String>{

	boolean isExistByName(String name);

	Deparment getMaxSortNo();

	List<Deparment> getRootList();
	public Deparment getByName(String name);
	public List<Deparment> getListByName(String name);
	Boolean checkIfHasRootDeparment(String id);
	
	/**
	 * 获取部门json数据
	 * @return
	 */
	public JSONArray getDeparmnetList();
	
	/**
	 * 获取部门下成员json数据
	 * @return
	 */
	public JSONObject getAllDeparemntAdmin();
	/**
	 * 获取子部门
	 * @param deparmentSet
	 * @param deparment
	 */
	public void  getSubDeparment(Collection<Deparment> deparmentSet,Deparment  deparment);
	
	public List<Deparment>  getRootDeparment();

	/**
	 * get deparment by name
	 * @return deparment
	 * @author songkez
	 * @since  2015-8-19 
	 */
	Deparment getDeparmentByName(String name);
	
	public void saveWithAdminList(Deparment  deparment,List<Admin> deparmentAdminList);
	public void updateWithAdminList(Deparment  deparment,List<Admin> deparmentAdminList);
	public List<Deparment> getDeparmentListByAlisaName(String alisaName);
	public List<String>  getDeparmentName();

}