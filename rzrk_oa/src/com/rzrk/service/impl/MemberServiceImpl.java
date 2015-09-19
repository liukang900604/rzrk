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


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.dao.MemberDao;
import com.rzrk.entity.Member;
import com.rzrk.entity.Product;
import com.rzrk.service.MemberService;
import com.rzrk.service.ProductService;
import com.rzrk.util.CommonUtil;

/**
 * Service实现类 - 会员
 */

@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, String> implements MemberService {
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;

	@Resource(name = "memberDaoImpl")
	public void setBaseDao(MemberDao memberDao) {
		super.setBaseDao(memberDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByUsername(String username) {
		return memberDao.isExistByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public Member getMemberByUserIdentification(String userIdentification) {
		return memberDao.getMemberByUserIdentification(userIdentification);
	}

	public boolean verifyMember(String username, String password) {
		Member member = getMemberByUsername(username);
		if (member != null && member.getPassword().equals(DigestUtils.md5Hex(password))) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 根据用户名称获取用户对象
	 * 
	 */
	@Override
	public Member getMemberByUsername(String username) {
		
		return memberDao.getMemberByUsername(username);
	}
	/**
	 * 获取用户历史交易
	 * 
	 */
	@Override
	public List<Member> getProductHistory(String username) {
		List<Member> list=memberDao.getProductHistory(username);
		return list;
	}
	/**
	 * 获取用户持有产品
	 * 
	 */
	@Override
	public List<Member> getProductList(String username) {
		List<Member> list=memberDao.getProductList(username);
		
		return list;
	}


}