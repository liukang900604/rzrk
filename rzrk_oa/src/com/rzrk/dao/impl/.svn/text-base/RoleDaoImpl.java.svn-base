/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import org.springframework.stereotype.Repository;

import com.rzrk.dao.RoleDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Role;
import com.rzrk.util.StringUtils;

/**
 * Dao实现类 - 角色
 */

@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {

	// 忽略isSystem=true的对象
	@Override
	public void delete(Role role) {
		if (role.getIsSystem()) {
			return;
		}
		super.delete(role);
	}

	// 忽略isSystem=true的对象
	@Override
	public void delete(String id) {
		Role role = load(id);
		this.delete(role);
	}

	// 忽略isSystem=true的对象
	@Override
	public void delete(String[] ids) {
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				this.delete(id);
			}
		}
	}

	// 设置isSystem=false
	@Override
	public String save(Role role) {
		role.setIsSystem(false);
		return super.save(role);
	}

	// 忽略isSystem=true的对象
	@Override
	public void update(Role role) {
		if (role.getIsSystem()) {
			return;
		}
		super.update(role);
	}

	@Override
	public Boolean checkNameExsits(Role role) {
		if(StringUtils.isEmpty(role.getId())){
			String hql = "from Role as role where lower(role.name) = lower(:name)";
			Role ret = (Role) getSession().createQuery(hql).setParameter("name", role.getName()).uniqueResult();
			if (ret != null) {
				return true;
			} else {
				return false;
			}
		}else{
			String hql = "from Role as role where lower(role.name) = lower(:name) and role.id != :id";
			Role ret = (Role) getSession().createQuery(hql).setParameter("name", role.getName()).setParameter("id", role.getId()).uniqueResult();
			if (ret != null) {
				return true;
			} else {
				return false;
			}
		}

	}

}