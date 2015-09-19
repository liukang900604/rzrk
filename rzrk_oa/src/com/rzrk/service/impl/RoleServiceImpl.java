/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.rzrk.dao.RoleDao;
import com.rzrk.entity.Role;
import com.rzrk.service.RoleService;

/**
 * Service实现类 - 角色
 */

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
	
	@Resource(name = "roleDaoImpl")
	RoleDao roleDao;

	@Resource(name = "roleDaoImpl")
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}

	@Override
	public Boolean checkNameExsits(Role role) {
		return roleDao.checkNameExsits(role);
	}

	/*  @see com.rzrk.service.RoleService#getSuperRole()  */
	@Override
	public Role getSuperRole() {
		return roleDao.get("ROLE00001");
	}

}