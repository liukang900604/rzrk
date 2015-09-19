/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.rzrk.bean.Pager;
import com.rzrk.dao.ArticleDao;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;

/**
 * Dao实现类 - 文章
 */

@Repository("articleDaoImpl")
public class ArticleDaoImpl extends BaseDaoImpl<Article, String> implements ArticleDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory, String type, boolean isContainChildren, Integer maxResults) {
		Query query = null;
		if (articleCategory != null) {
			if (StringUtils.equalsIgnoreCase(type, "top")) {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.isTop = :isTop and article.articleCategory.path like :path order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("isTop", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory and article.isTop = :isTop order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory).setParameter("isTop", true);
				}
			} else if (StringUtils.equalsIgnoreCase(type, "recommend")) {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.isRecommend = :isRecommend and article.articleCategory.path like :path order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("isRecommend", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory and article.isRecommend = :isRecommend order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory).setParameter("isRecommend", true);
				}
			} else if (StringUtils.equalsIgnoreCase(type, "hot")) {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path order by article.hits desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory order by article.hits desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory);
				}
			} else {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory);
				}
			}
		} else {
			if (StringUtils.equalsIgnoreCase(type, "top")) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.isTop = :isTop order by article.isTop desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("isTop", true);
			} else if (StringUtils.equalsIgnoreCase(type, "recommend")) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.isRecommend = :isRecommend order by article.isTop desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("isRecommend", true);
			} else if (StringUtils.equalsIgnoreCase(type, "hot")) {
				String hql = "from Article as article where article.isPublication = :isPublication order by article.hits desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true);
			} else {
				String hql = "from Article as article where article.isPublication = :isPublication order by article.isTop desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true);
			}
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	
	
	/**
	 * xy查询所有文章内容
	 */
	@Override
	public List<Article> getArticleList() {
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name!='投资报告' and article.type = 0 order by article.createDate desc";
		query=getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(4);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}

	}
	/**
	 *导航栏资讯
	 */
	public List<Article> getPrompt(){
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name!='投资报告' and article.type = 0 order by article.createDate desc";
		query=getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(4);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}

	}
	/**
	 * 根据文章id返回对应的文章
	 */
	@Override
	public Article getArticleById(String id) {
		Query query=null;
		String hql="from Article as article where article.id='"+id+"'";
		query=getSession().createQuery(hql);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return ((Article)list.get(0));
		}else{
			return null;
		}

	}
	

	/**
	 * 根据id查询上一条新闻
	 */
	public Article getLastArticleById(String id,String name){
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name='"+name+"' and article.createDate > (select article.createDate from Article article  where article.id='"+id+"' and article.type = 0) order by article.createDate asc";
		query=getSession().createQuery(hql);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return ((Article)list.get(0));
		}else{
			return null;
		}

	}
	/**
	 * 根据id查询下一条新闻
	 */
	public Article getNextArticleById(String id,String name){
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name='"+name+"' and article.createDate < (select article.createDate from Article article where article.id='"+id+"' and article.type = 0 ) order by article.createDate desc";
		query=getSession().createQuery(hql);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return ((Article)list.get(0));
		}else{
			return null;
		}
	}

	/**
	 * 根据文章类型名称返回查询的总数
	 */
	@Override
	public List<Article> getArticleByName(String name) {
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name='"+name+"' and article.type = 0 order by article.createDate desc";
		query=getSession().createQuery(hql);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 文章分页
	 */
	@Override
	public List<Article> getArticlePagerByName(Pager pager, String name) {
		final int pageSize=pager.getPageSize();
		final int startRow=(pager.getPageNumber()-1)*pager.getPageSize();
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name='"+name+"' and article.type = 0 order by article.createDate desc";
		query=getSession().createQuery(hql);
		query.setFirstResult(startRow);
		query.setMaxResults(pageSize);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<Article> getArticlelListByArticleName(String name) {
		Query query=null;
		String hql="from Article as article inner join fetch article.articleCategory where article.articleCategory.name='"+name+"' and article.type = 0 order by article.createDate desc";
		query=getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}



	@Override
	public Article getArticleByProductId(String id) {
		Query query=null;
		String hql="from Article as article where article.productId='"+id+"' and article.type = 1 ";
		query=getSession().createQuery(hql);
		List<Article> list=query.list();
		if(list!=null&&list.size()>0){
			return ((Article)list.get(0));
		}else{
			return null;
		}
	}
		
}