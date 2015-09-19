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


/**
 * Service接口 - 文章
 */

public interface ArticleService extends BaseService<Article, String> {
	/**
	 * 根据文章分类、文章类型、是否包含子分类文章、最大结果数获取文章集合（只包含isPublication=true的对象）
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param type
	 *            文章类型,null表示无限制
	 *            
	 * @param isContainChildren
	 *            是否包含子分类文章
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的文章集合
	 */
	public List<Article> getArticleList(ArticleCategory articleCategory, String type, boolean isContainChildren, Integer maxResults);
	/**
	 * xy查询所有文章内容
	 */
	public List<Article> getArticleList();
	
	
	/**
	 * 根据id查询新闻
	 */
	
	public Article getArticleById(String id);
	
	/**
	 * 根据id查询上一条新闻
	 */
	public Article getLastArticleById(String id,String name);
	/**
	 * 根据id查询下一条新闻
	 */
	public Article getNextArticleById(String id,String name);
	
	/**
	 * 导航栏资讯
	 */
	
	public List<Article> getPrompt();
	/**
	 * 根据文章类型名称返回文章总数
	 */
	public List<Article> getArticleByName(String name);
	/**
	 * 文章分页
	 */
	public List<Article> getArticlePagerByName(Pager pager, String name);
	
	public List<Article> getArticlelListByArticleName(String name);
	
	public Article getArticleByProductId(String id);
	
	
}