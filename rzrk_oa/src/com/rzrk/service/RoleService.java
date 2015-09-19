/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import com.rzrk.entity.Role;

/**
 * Service接口 - 角色
 */

public interface RoleService extends BaseService<Role, String> {

	Boolean checkNameExsits(Role role);
	
	Role getSuperRole();

}