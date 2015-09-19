/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.rzrk.bean.Pager;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.Deparment;
import com.rzrk.service.AdminService;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Service实现类 - 人员
 */

@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	@Resource(name = "adminDaoImpl")
	private AdminDao adminDao;
	
	@Resource(name = "deparmentDaoImpl")
	private DeparmentDao deparmentDao;
	
	
	@Resource(name = "adminDaoImpl")
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}
	
	
	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			Admin a =  (Admin) principal;
			return a;
		}
	}
	
	@Transactional(readOnly = true)
	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

	@Transactional(readOnly = true)
	public Admin getAdminByName(String name) {
		return adminDao.getAdminByName( name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Admin> getAdminListByName(String name) {
		return adminDao.getAdminListByName(name);
	}
	@Override
	public Admin getMaxSortNo() {
		return adminDao.getMaxSortNo();
	}

	/**
	 * 获取部门下人员信息
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<DeparmentAdminVo> getAdminByDeparmentAndSub(String deparemtId){
		return adminDao.getAdminByDeparmentAndSub(deparemtId);
	}
	
	
	/**
	 * 获取当前部门人员
	 */
	public List<Admin> getDeparmentList(Admin loginAdmin){

		Set<Admin> adminList = new HashSet<Admin>();
		String hql = "from Deparment where deparmentLeader.id =:loginAdminId";
		List<Deparment> deparmentList = this.getBaseDao().getSession().createQuery(hql).setParameter("loginAdminId", loginAdmin.getId()).list();
		if(deparmentList != null && deparmentList.size() > 0){
			for(Deparment deparment : deparmentList){
				adminList.addAll(deparment.getDeparmentAdmins());
			}
		}else{
			adminList.add(loginAdmin);
		}

		return new ArrayList<Admin>(adminList);

	}
	
	@Transactional(readOnly = true)
	public List<String>  getAdminByJob(String jobId){
		return adminDao.getAdminByJob(jobId);
	}
	private void collectionDepartMent(Deparment department,List<String> depIdList){
		depIdList.add(department.getId());
		for(Deparment dep : department.getChildDeparments()){
			collectionDepartMent(dep,depIdList);
		}
	}
	
	public Pager findPager(Pager pager, String departmentId) {
//		Set<String> depIdSet = new HashSet<String>();
		List<String> depIdList = new ArrayList<String>();
		if(StringUtils.isEmpty(departmentId)){
			List<Deparment> deparmentList = getRootDeparment();
			 if(deparmentList != null){
				for(Deparment department : deparmentList){
					collectionDepartMent(department,depIdList);
				}
			 }
		
		}else{
			Deparment department  = deparmentDao.get(departmentId);
			collectionDepartMent(department,depIdList);
		}
		return adminDao.findPager(pager, depIdList);
	}

	/*  @see com.rzrk.service.AdminService#getAllListExceptOutOfJob()  */
	@Override
	@Cacheable(modelId = "allAdminListOutOfJobCaching")
	public List<Admin> getAllListExceptOutOfJob() {
		System.out.println("oscache invoked。。。。。。。");
		return adminDao.getAllListExceptOutOfJob();
	};
	
	
	@Override
	@CacheFlush(modelId = "allAdminListOutOfJobFlushing")
	public void delete(Admin admin) {
		adminDao.delete(admin);
	}

	@Override
	@CacheFlush(modelId = "allAdminListOutOfJobFlushing")
	public void delete(String id) {
		adminDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "allAdminListOutOfJobFlushing")
	public void delete(String[] ids) {
		adminDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "allAdminListOutOfJobFlushing")
	public String save(Admin admin) {
		return adminDao.save(admin);
	}

	@Override
	@CacheFlush(modelId = "allAdminListOutOfJobFlushing")
	public void update(Admin admin) {
		adminDao.update(admin);
	}

	
	/**
	 * 获取根部门
	 * @return
	 */
	public List<Deparment>  getRootDeparment(){
		String hql = "from Deparment where parent.id is null ";
		return this.getBaseDao().getSession().createQuery(hql).list();
	}
	

}