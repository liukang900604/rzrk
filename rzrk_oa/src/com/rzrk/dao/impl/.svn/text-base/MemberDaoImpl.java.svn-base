/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.rzrk.bean.Pager;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.MemberDao;
import com.rzrk.entity.Article;
import com.rzrk.entity.Member;
import org.springframework.util.Assert;

/**
 * Dao实现类 - 会员
 */

@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, String> implements MemberDao {

	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public boolean isExistByUsername(String username) {
		String hql = "from Member as members where lower(members.username) = lower(:username)";
		Member member = (Member) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 根据用户的身份证获取用户信息
	 */

	@Override
	public Member getMemberByUserIdentification(String userIdentification) {
		Query query=null;
		String hql = "from Member as members where members.userIdentification='"+userIdentification+"'";
		query=getSession().createQuery(hql);
		List<Member> list=query.list();
		if(list!=null&&list.size()>0){
			return ((Member)list.get(0));
		}else{
			return null;
		}
	}
	/**
	 * 根据用户名称获取用户信息
	 */

	public Member getMemberByUsername(String username){
		String hql = "from Member as members where lower(members.username) = lower(:username)";
		return (Member) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
	}
	/**
	 * 根据用户名称返回用户的历史交易
	 */

	@Override
	public List<Member> getProductHistory(String username) {

		Query query=null;
		String hql = "from Member as members where members.username='"+username+"'";
		query=getSession().createQuery(hql);
		List<Member> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 根据用户名称获取持有产品
	 */

	@Override
	public List<Member> getProductList(String username) {
		Query query=null;
		String hql = "from Member as members where members.username='"+username+"'";
		query=getSession().createQuery(hql);
		List<Member> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
}