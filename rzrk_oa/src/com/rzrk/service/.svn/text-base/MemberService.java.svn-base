/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import com.rzrk.entity.Member;


/**
 * Service接口 - 会员
 */

public interface MemberService extends BaseService<Member, String> {
	
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
	 * 根据用户名、密码验证会员
	 * 
	 * @param username
	 *            用户名
	 *            
	 * @param password
	 *            密码
	 * 
	 * @return 验证是否通过
	 */
	public boolean verifyMember(String username, String password);
	/**
	 * 根据用户名称获取历史交易
	 * 
	 */
	public List<Member> getProductHistory(String username);
	/**
	 * 根据用户名称获取用户持有产品
	 * 
	 */
	public List<Member> getProductList(String username);

}