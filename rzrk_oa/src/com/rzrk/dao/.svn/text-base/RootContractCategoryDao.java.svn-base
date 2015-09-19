package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;

public interface RootContractCategoryDao extends BaseDao<RootContractCategory, String> {

	boolean isExistByName(String name);

	/**
	 * get by type
	 * @param type
	 * @return
	 * @author songkez
	 * @since  2015-6-18 
	 */
	List<RootContractCategory> getByType(ROOT_CATEGORY_TYPE type);

}