/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import com.rzrk.util.date.CalendarUtils;

/**
 * 实体类 - 会员
 */

@Entity
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1533130686714725835L;

	public static final String MEMBER_ID_SESSION_NAME = "memberId";// 保存登录会员ID的Session名称
	public static final String MEMBER_USERNAME_COOKIE_NAME = "memberUsername";// 保存登录会员用户名的Cookie名称
	public static final String USER_NAME = "username";
	public static final String USER="_user_key";
	

	private String username;// 用户名
	private String password;// 密码
	private String email;// E-mail
	private String address;// 地址
	private String zipCode;// 邮编
	private String phone;// 电话
	private String mobile;// 手机
	private String userIdentification;//用户身份证
	private String bank; //银行名称
	private String bankAccount; //银行帐号
	private String realName;//用户真实姓名
	@Column(nullable = false)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(nullable = false)
	public String getBank() {
		return (bank==null ? "" :bank);
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	@Column(nullable = false)
	public String getBankAccount() {
		return bankAccount==null ? "" :bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(nullable = false)
    public String getUserIdentification() {
		return userIdentification == null ? "" : userIdentification;
	}

	public void setUserIdentification(String userIdentification) {
		this.userIdentification = userIdentification;
	}

	@Column(nullable = false, updatable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = true)
	public String getEmail() {
		return email == null ? "" : email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getAddress() {
		return address == null ? "" : address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode == null ? "" : zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone == null ? "" : phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile == null ? "" : mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * generate user name
	 * 
	 * @return
	 * @author songkez
	 * @since 2012-5-21
	 */
	public static String generateUserName() {
		String month = (CalendarUtils.getCurMonth() + "");
		return (CalendarUtils.getCurYear() + "").substring(2, 4)
				+ (month.length()==1?('0'+month):month)
				+ (CalendarUtils.getCurDay() + "")
				+ com.rzrk.util.StringUtils.generateAlpaWords(4);
	}
}