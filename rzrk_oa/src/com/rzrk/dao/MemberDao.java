/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.Member;

/**
 * Dao接口 - 会员
 */

public interface MemberDao extends BaseDao<Member, String> {
	
	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	
	public Member getMemberByUsername(String username);
	/**
	 * 根据用户身份证获取会员对象,若会员不存在,则返回null（不区分大小写）
	 * 
	 */
	public Member getMemberByUserIdentification(String userIdentification);
	
	/**
	 * 根据用户名称获取用户的产品历史交易
	 * 
	 */
	
	public List<Member> getProductHistory(String username);
	/**
	 * 根据用户名称获取用户持有的产品
	 * 
	 */
	
	public List<Member> getProductList(String username);
		
		
	
	
	
	
	
	
}