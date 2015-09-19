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
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.service.ArticleService;

/**
 * Service实现类 - 文章
 */

@Service("articleServiceImpl")
public class ArticleServiceImpl extends BaseServiceImpl<Article, String> implements ArticleService {
	
	private long refreshTime = System.currentTimeMillis();

	@Resource(name = "cacheManager")
	private GeneralCacheAdministrator cacheManager;
	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;

	@Resource(name = "articleDaoImpl")
	public void setBaseDao(ArticleDao articleDao) {
		super.setBaseDao(articleDao);
	}
	/**
	 * xy查询所有文章内容
	 */
	@Override
	public List<Article> getArticleList() {
		
		return articleDao.getArticleList();
	}

	

	public Article getArticleById(String id){
		return articleDao.getArticleById(id);
	}

	@Override
	public Article getLastArticleById(String id,String name) {
		return articleDao.getLastArticleById(id,name);
	}

	@Override
	public Article getNextArticleById(String id,String name) {
		return articleDao.getNextArticleById(id,name);
	}

	@Override
	public List<Article> getPrompt() {
		return articleDao.getPrompt();
	}

	@Override
	public List<Article> getArticleByName(String name) {
		return articleDao.getArticleByName(name);
	}

	@Override
	public List<Article> getArticlePagerByName(Pager pager, String name) {
		return articleDao.getArticlePagerByName(pager, name);
	}

	public List<Article> getArticlelListByArticleName(String name){
		return articleDao.getArticlelListByArticleName(name);
	}
	@Override
	public List<Article> getArticleList(ArticleCategory articleCategory,
			String type, boolean isContainChildren, Integer maxResults) {
		return articleDao.getArticleList(articleCategory, type, isContainChildren, maxResults);
	}
	@Override
	public Article getArticleByProductId(String id) {
		
		return articleDao.getArticleByProductId(id);
	}
	
}