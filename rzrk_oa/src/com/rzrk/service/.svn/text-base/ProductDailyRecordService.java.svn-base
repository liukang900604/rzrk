/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.List;

import com.rzrk.entity.Product;
import com.rzrk.entity.ProductDailyRecord;

/**
 * Service接口 - 每日净值
 */

public interface ProductDailyRecordService extends BaseService<ProductDailyRecord, String> {

	List<Product> getAllProduct();

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