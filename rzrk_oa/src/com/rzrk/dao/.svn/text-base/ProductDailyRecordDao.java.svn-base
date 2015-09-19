/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.Admin;
import com.rzrk.entity.ProductDailyRecord;

/**
 * Dao接口 - 人员
 */

public interface ProductDailyRecordDao extends BaseDao<ProductDailyRecord, String> {

	ProductDailyRecord getPdrByProductIdAndDate(String id, String date);

	String getNewestDate();

	List<ProductDailyRecord> getPdrByProductId(String productId);

	int getCurrentDayCountByDate(String date);

	List<ProductDailyRecord> getListByFromTo(String from, String to);

	/**
	 * TODO:Please add method comments
	 * @param productName
	 * @param from
	 * @param to
	 * @return
	 * @author songkez
	 * @since  2015-8-3 
	 */
	List<ProductDailyRecord> getPdrByProductName(String productName,
			String from, String to);
	

}