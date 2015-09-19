/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.Cacheable;

import com.rzrk.bean.Pager;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.dao.ScriptLibraryDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.ScriptLibrary;
import com.rzrk.service.AdminService;
import com.rzrk.service.ScriptLibraryService;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Service实现类 - 人员
 */

@Service("scriptLibraryServiceImpl")
public class ScriptLibraryServiceImpl extends BaseServiceImpl<ScriptLibrary, String> implements ScriptLibraryService {

	@Resource(name = "scriptLibraryDaoImpl")
	private ScriptLibraryDao scriptLibraryDao;
	
	@Resource(name = "scriptLibraryDaoImpl")
	public void setBaseDao(ScriptLibraryDao scriptLibraryDao) {
		super.setBaseDao(scriptLibraryDao);
	}

}