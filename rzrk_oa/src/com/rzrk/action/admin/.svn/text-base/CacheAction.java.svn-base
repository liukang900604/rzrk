/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.service.CacheService;


/**
 * 后台Action类 - 缓存
 */

@ParentPackage("admin")
public class CacheAction extends BaseAdminAction {

	private static final long serialVersionUID = 3290111140770511789L;

	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "cacheManager")
	private GeneralCacheAdministrator cacheManager;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	
	// 刷新所有缓存
	public String flush() {
		cacheManager.flushAll();
		cacheService.flushAllPageCache(getRequest());
		freemarkerManager.getConfiguration(getServletContext()).clearTemplateCache();
		return SUCCESS;
	}

}