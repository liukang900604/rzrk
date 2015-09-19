/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.Date;
import java.util.List;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.SerialNumber;


/**
 * Service接口 - 文章
 */

public interface SerialNumberService extends BaseService<SerialNumber, String> {
	
	public long getAndSet(String type);
	
}