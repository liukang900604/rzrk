/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;












import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ProductDailyRecordDao;
import com.rzrk.dao.ProductDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductDailyRecord;
import com.rzrk.service.AdminService;
import com.rzrk.service.ProductDailyRecordService;

/**
 * Service实现类 - 人员
 */

@Service("productDailyRecordServiceImpl")
public class ProductDailyRecordServiceImpl extends BaseServiceImpl<ProductDailyRecord, String> implements ProductDailyRecordService {
	@Resource(name = "productDailyRecordDaoImpl")
	private ProductDailyRecordDao productDailyRecordDao;
	
	@Resource(name = "productDailyRecordDaoImpl")
	public void setBaseDao(ProductDailyRecordDao productDailyRecordDao) {
		super.setBaseDao(productDailyRecordDao);
	}
	
    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

	@Override
	public List<Product> getAllProduct() {
		return productDao.getAllList();
	}

	@Override
	public ProductDailyRecord getPdrByProductIdAndDate(String id, String date) {
		return productDailyRecordDao.getPdrByProductIdAndDate(id, date);
	}

	@Override
	public String getNewestDate() {
		return productDailyRecordDao.getNewestDate();
	}

	@Override
	public List<ProductDailyRecord> getPdrByProductId(String productId) {
		return productDailyRecordDao.getPdrByProductId(productId);
	}

	@Override
	public int getCurrentDayCountByDate(String date) {
		return productDailyRecordDao.getCurrentDayCountByDate(date);
	}

	@Override
	public List<ProductDailyRecord> getListByFromTo(String from, String to) {
		return productDailyRecordDao.getListByFromTo(from, to);
	}

	/*  @see com.rzrk.service.ProductDailyRecordService#getPdrByProductName(java.lang.String, java.lang.String, java.lang.String)  */
	@Override
	public List<ProductDailyRecord> getPdrByProductName(String productName,
			String from, String to) {
		return productDailyRecordDao.getPdrByProductName(productName, from, to);
	}
}