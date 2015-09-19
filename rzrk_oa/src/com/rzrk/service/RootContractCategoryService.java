/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.ArrayList;
import java.util.List;

import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;
/**
 * Service接口 - 根分类
 */

public interface RootContractCategoryService extends BaseService<RootContractCategory, String> {

	boolean isExistByName(String name);
	public ArrayList<?> getListWithChildAndFields();
	public ArrayList<?> getListWithChild();
	/**
	 * get by type
	 * @return
	 * @author songkez
	 * @since  2015-6-18 
	 */
	List<RootContractCategory> getByType(ROOT_CATEGORY_TYPE type);
}