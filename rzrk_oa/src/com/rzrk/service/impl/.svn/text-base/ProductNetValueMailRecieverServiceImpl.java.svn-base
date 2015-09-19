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
import com.rzrk.dao.ProductNetValueMailRecieverDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ProductNetValueMailReciever;
import com.rzrk.service.AdminService;
import com.rzrk.service.ProductNetValueMailRecieverService;

/**
 * Service实现类 - 人员
 */

@Service("productNetValueMailRecieverServiceImpl")
public class ProductNetValueMailRecieverServiceImpl extends BaseServiceImpl<ProductNetValueMailReciever, String> implements ProductNetValueMailRecieverService {

	@Resource(name = "productNetValueMailRecieverDaoImpl")
	private ProductNetValueMailRecieverDao productNetValueMailRecieverDao;
	
	@Resource(name = "productNetValueMailRecieverDaoImpl")
	public void setBaseDao(ProductNetValueMailRecieverDao productNetValueMailRecieverDaoImpl) {
		super.setBaseDao(productNetValueMailRecieverDaoImpl);
	}

	@Override
	public List<ProductNetValueMailReciever> getProductNetValueMailRecieverListByProductId(
			String productId) {
		return productNetValueMailRecieverDao.getProductNetValueMailRecieverListByProductId(productId);
	}


}