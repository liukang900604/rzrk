/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.Cacheable;

import com.rzrk.dao.DeparmentDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Service实现类 - 日志
 */

@Service("deparmentServiceImpl")
public class DeparmentServiceImpl extends BaseServiceImpl<Deparment, String> implements DeparmentService {
	
	@Resource(name = "deparmentDaoImpl")
	private DeparmentDao deparmentDao;
	
	@Resource(name = "deparmentDaoImpl")
	public void setBaseDao(DeparmentDao deparmentDao) {
		super.setBaseDao(deparmentDao);
	}
    
	/**
	 * 人员service
	 */
	@Resource
	private AdminService adminService;
	
	
	@Override
	public boolean isExistByName(String name) {
		
		return deparmentDao.isExistByName(name);
	}

	@Override
	public Deparment getMaxSortNo() {
		return deparmentDao.getMaxSortNo();
	}

	@Override
	public List<Deparment> getRootList() {
		return null;
	}
	public Deparment getByName(String name){
		return deparmentDao.getByName(name);
	}
	
	@Override
	public List<Deparment> getListByName(String name) {
		return deparmentDao.getListByName(name);
	}

	@Override
	public Boolean checkIfHasRootDeparment(String id) {
		return deparmentDao.checkIfHasRootDeparment(id);
	}
	
	/**
	 * 获取根部门
	 * @return
	 */
	public List<Deparment>  getRootDeparment(){
		String hql = "from Deparment where parent.id is null ";
		return this.getBaseDao().getSession().createQuery(hql).list();
	}
	
	/**
	 * 根据别名获取部门
	 */
	public List<Deparment> getDeparmentListByAlisaName(String alisaName){
		String hql = "from Deparment where deparmentAlisa =:deparmentAlisa ";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("deparmentAlisa", alisaName).list();
		
	}
	
	/**
	 * 获取所有部门json数据
	 * @return
	 */
	@Override
	@Cacheable(modelId = "allAdminListOutOfJobCaching")
	public JSONArray getDeparmnetList(){
		JSONArray jsonObj = new JSONArray();
		Set<String> depramentSet = new HashSet<String>();//过滤部门id
		/*	List<Deparment> deparmentList = this.getAllList();
		for(Deparment deparment : deparmentList){
			if(depramentSet.contains(deparment.getId())){
				continue;
			}else{
				depramentSet.add(deparment.getId());
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", deparment.getId());//部门id
			map.put("text", deparment.getName());//部门名称
			 if(null != deparment.getChildDeparments()){//子部门
		        	map.put("children", deparment.getParent().getId());
		        }else{
		        	map.put("parent", "");
		        }
			jsonObj.add(map);
		}*/
		List<Deparment> deparmentList = getRootDeparment();//获取根部门
		if(deparmentList != null){
			for(Deparment deparment: deparmentList){
				Map<String,Object> parmentMap = new HashMap<String,Object>();
				parmentMap.put("id", deparment.getId());
				parmentMap.put("text", deparment.getName());
				getSubDeparment(parmentMap,deparment,depramentSet);
				jsonObj.add(parmentMap);
			}
		}
		
		return jsonObj;
	}
	
	/**
	 * 获取子部门
	 * @param deparmentSet
	 * @return
	 */
	public void  getSubDeparment(Map<String,Object> childrenMap,Deparment  deparment,Set<String> depramentSet){
		if(deparment != null){//当前部门不为空
		    Set<Deparment> deparmentSet = deparment.getChildDeparments();//子部门
			JSONArray jsonObj = new JSONArray();//存子部门
			if(deparmentSet != null && deparmentSet.size() > 0){//子部门
				childrenMap.put("state", "open");
				for(Deparment tempDeparment : deparmentSet){
					Map<String,Object> subMap = new HashMap<String,Object>();
					subMap.put("id", tempDeparment.getId());
					subMap.put("text", tempDeparment.getName());
					getSubDeparment(subMap,tempDeparment,depramentSet);
					jsonObj.add(subMap);
				}
				childrenMap.put("children", jsonObj);
			}else{//无子部门
				//childrenMap.put("state", "closed");
				childrenMap.put("children", jsonObj);
			}
			
		}
		
		
	}
	
	/**
	 * 获取所有的部门成员
	 * @return
	 */
	@Cacheable(modelId = "allAdminListOutOfJobCaching")
	public JSONObject getAllDeparemntAdmin(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Deparment> deparmentList = this.getAllList();
		for(Deparment deparment : deparmentList){
			JSONArray jsonObj = new JSONArray();
			List<DeparmentAdminVo> adminList = adminService.getAdminByDeparmentAndSub(deparment.getId());//获取部门下的成员
			if(adminList != null && adminList.size() > 0){
				for(DeparmentAdminVo tempAdmin : adminList){
					Map<String,Object> adminMap = new HashMap<String,Object>();
					adminMap.put("id", tempAdmin.getAdminId());//用户id
					adminMap.put("name", tempAdmin.getAdminName());//用户名称
					adminMap.put("deparmentId", tempAdmin.getDeparmentId());//部门id
					adminMap.put("deparmentName", tempAdmin.getDeparmentName());//部门id
					jsonObj.add(adminMap);
				}
				
			}
			resultMap.put(deparment.getId(),jsonObj );
		}
		return JSONObject.fromObject(resultMap);
	}

	public void  getSubDeparment(Collection<Deparment> deparmentSet,Deparment  deparment){
		deparmentDao.getSubDeparment(deparmentSet, deparment);
	}

	/*  @see com.rzrk.service.DeparmentService#getDeparmentByName(java.lang.String)  */
	@Override
	public Deparment getDeparmentByName(String name) {
		return deparmentDao.getDeparmentByName(name);
	};
	
	public void saveWithAdminList(Deparment  deparment,List<Admin> deparmentAdminList){
		List<Admin> adminList = new ArrayList<Admin>();
		for(Admin _admin : deparmentAdminList){
			Admin admin = adminService.get(_admin.getId());
			admin.getDeparmentSet().add(deparment);
			adminList.add(admin);
		}
		deparment.getDeparmentAdmins().clear();
		deparment.getDeparmentAdmins().addAll(adminList);
		saveOrUpdate(deparment);
	};
	
	private boolean hasAdmin(Admin  admin,List<Admin> deparmentAdminList){
		for(Admin  _admin : deparmentAdminList){
			if(StringUtils.equals(admin.getId(), _admin.getId())){
				return true;
			}
		}
		return false;
	}
	
	private void removeDeparment(Deparment deparment,Set<Deparment> departmentSet){
		
		Iterator<Deparment> ite = departmentSet.iterator();
		while(ite.hasNext()){
			Deparment  _deparment = ite.next();
			if(StringUtils.equals(_deparment.getId(), _deparment.getId())){
				ite.remove();
			}
		}
	}
	
	public void updateWithAdminList(Deparment  deparment,List<Admin> deparmentAdminList){
		List<Admin> adminList = new ArrayList<Admin>();
		for(Admin  _admin : deparment.getDeparmentAdmins()){
			if(!hasAdmin(_admin,deparmentAdminList)){
				Admin admin = adminService.get(_admin.getId());
				removeDeparment(deparment,admin.getDeparmentSet());
			}
		}
		for(Admin _admin : deparmentAdminList){
			Admin admin = adminService.get(_admin.getId());
			admin.getDeparmentSet().add(deparment);
			adminList.add(admin);
		}
		deparment.getDeparmentAdmins().clear();
		deparment.getDeparmentAdmins().addAll(adminList);
		saveOrUpdate(deparment);
	};
	
	/**
	 * 获取部门名称
	 * @return
	 */
	public List<String>  getDeparmentName(){
		String hql = "select name from Deparment";
		return this.getBaseDao().getSession().createQuery(hql).list();
	}


}