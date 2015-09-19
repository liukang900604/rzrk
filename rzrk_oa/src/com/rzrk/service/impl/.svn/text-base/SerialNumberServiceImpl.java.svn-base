/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;






import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.bean.Pager;
import com.rzrk.dao.ArticleDao;
import com.rzrk.dao.SerialNumberDao;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.SerialNumber;
import com.rzrk.service.ArticleService;
import com.rzrk.service.SerialNumberService;

/**
 * Service实现类 - 文章
 */

@Service("serialNumberServiceImpl")
public class SerialNumberServiceImpl extends BaseServiceImpl<SerialNumber, String> implements SerialNumberService {
	

	@Resource(name = "serialNumberDaoImpl")
	private SerialNumberDao serialNumberDao;

	@Resource(name = "serialNumberDaoImpl")
	public void setBaseDao(SerialNumberDao serialNumberDao) {
		super.setBaseDao(serialNumberDao);
	}
	
	@Override
	public long getAndSet(String type) {
		return serialNumberDao.getAndSet(type);
	}
	
	
}