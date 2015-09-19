/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ProductDailyRecordDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductDailyRecord;

/**
 * Dao实现类 - 人员
 */

@Repository("productDailyRecordDaoImpl")
public class ProductDailyRecordDaoImpl extends BaseDaoImpl<ProductDailyRecord, String> implements ProductDailyRecordDao {

	@Override
	public ProductDailyRecord getPdrByProductIdAndDate(String id, String date) {
		String hql = "from ProductDailyRecord as pdr where pdr.productId = '"+ id +"' and recordDate = '" + date + "'";
        List<ProductDailyRecord> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);
	}

	@Override
	public String getNewestDate() {
		String hql = "from ProductDailyRecord order by recordDate desc limit 1";
        List<ProductDailyRecord> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0).getRecordDate();
	}

	@Override
	public List<ProductDailyRecord> getPdrByProductId(String productId) {
		String hql = "from ProductDailyRecord as pdr where pdr.productId = '"+ productId +"' order by recordDate";
        List<ProductDailyRecord> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}

	@Override
	public int getCurrentDayCountByDate(String date) {
		String hql = "from ProductDailyRecord as pdr where pdr.recordDate = '" + date + "'";
        List<ProductDailyRecord> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? 0 : resultList.size();
	}

	@Override
	public List<ProductDailyRecord> getListByFromTo(String beginDate, String endDate) {
		String hql="";
		if(StringUtils.isNotEmpty(beginDate)&& StringUtils.isNotEmpty(endDate)){
			hql = "from ProductDailyRecord as pdr where pdr.recordDate >= '" + beginDate + "' and pdr.recordDate <= '" + endDate + "' order by pdr.recordDate desc";
		}else if(StringUtils.isEmpty(beginDate) && StringUtils.isNotEmpty(endDate) ){
			hql = "from ProductDailyRecord as pdr where pdr.recordDate = '" + endDate + "' order by pdr.recordDate desc";
		}else if(StringUtils.isEmpty(endDate) && StringUtils.isNotEmpty(beginDate)){
			hql = "from ProductDailyRecord as pdr where pdr.recordDate = '" + beginDate + "' order by pdr.recordDate desc";
		}else{
			hql = "from ProductDailyRecord as pdr  order by pdr.recordDate desc";
		}
        List<ProductDailyRecord> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}

	/*  @see com.rzrk.dao.ProductDailyRecordDao#getPdrByProductName(java.lang.String, java.lang.String, java.lang.String)  */
	@Override
	public List<ProductDailyRecord> getPdrByProductName(String productName,
			String from, String to) {
		String hql = "from ProductDailyRecord as pdr where pdr.productId = '"+ productName +"' and pdr.recordDate >='" + from + "' and pdr.recordDate <= '" + to + "' order by recordDate";
        List<ProductDailyRecord> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}
	

}