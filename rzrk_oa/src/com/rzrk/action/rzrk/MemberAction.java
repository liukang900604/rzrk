/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.rzrk;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


import javax.annotation.Resource;

import com.rzrk.service.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.entity.Member;
import com.rzrk.entity.MemberProductHold;
import com.rzrk.entity.MemberProductHoldLog;
import com.rzrk.util.CaptchaUtil;
import com.rzrk.util.RzrkLogger;

/**
 * 前台Action类 - 会员
 */

@ParentPackage("shop")
public class MemberAction extends BaseShopAction {

	private static final long serialVersionUID = 1115660086350733102L;

	private Member member=new Member();
	private String loginRedirectUrl;
	private Boolean isAgreeAgreement;
	private String passwordRecoverKey;
	private Md5PasswordEncoder passwordEncoder;
	private List<MemberProductHold> productList;

	private List<MemberProductHoldLog> productHistoryList;
	private Double total=0.0;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "memberProductHoldServiceImpl")
	private MemberProductHoldService memberProductHoldService;
	@Resource(name = "memberProductHoldLogServiceImpl")
	private MemberProductHoldLogService memberProductHoldLogService;
	//登录
	public String ajaxLogin() {
		String idCards=getRequest().getParameter("userIdentification");
		String pwd=getRequest().getParameter("password");
		Member members =null;
		if(idCards!=null){
	    members=memberService.getMemberByUserIdentification(idCards);
		getRequest().setAttribute("members", members);
		}
		if(StringUtils.isBlank(idCards)){
			return ajax(Status.error,"身份证号不能为空!");
		}
		if(StringUtils.isBlank(pwd)){
			return ajax(Status.error,"密码不能为空!");
		}
		if (members == null) {
			return ajax(Status.error,"您输入的身份证号不存在!");
		}
		//if(DigestUtils.md5Hex(pwd).equals(members.getPassword())){
		if(pwd.equals(members.getPassword())){
			getSession().setAttribute(Member.USER_NAME, members.getUsername());
			getSession().setAttribute(Member.USER,members);
			return ajax(Status.success,String.format("({id:'%s',username:'%s'})",members.getId(),members.getUsername()));
		}
		return ajax(Status.error,"身份证号或密码错误!");
	}

	public String logout() {
		getSession().removeAttribute(Member.USER);
		getSession().removeAttribute(Member.USER_NAME);
		return REDIRECT;
	}

    //基本资料
	public String memberList(){
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		if(username!=null){
			Member mb=memberService.getMemberByUsername(username);
			getRequest().setAttribute("mb",mb); 
		}
		return "info";
	}
	//用户持有产品
	public String productList(){
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		if(username!=null){
			Member members=memberService.getMemberByUsername(username);
			productList=memberProductHoldService.findByMemberId(members.getId());
			if(null!=productList&&productList.size()>0){
				for(MemberProductHold mbh:productList){	
					total+=(mbh.getTotalAmount()*mbh.getProductNetValue().getTrustValue());	
				}
			}
		}
		return "products";	
	}
    //历史交易
	public String history(){
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		if(username!=null){
			Member members=memberService.getMemberByUsername(username);
			productHistoryList=memberProductHoldLogService.findByMemberId(members.getId());
		}
		return "history";
	}
	//获取用户的身份证号跳转至修改密码页
	public String change(){
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		if(username!=null){
			Member mb=memberService.getMemberByUsername(username);
			getRequest().setAttribute("mb",mb); 
		}
		return "change";
	}
	//修改密码
	public String changePwd() throws IOException{
		
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		String currentPwd=getRequest().getParameter("currentPwd");
		if(StringUtils.isBlank(currentPwd)){
			return ajax(Status.error,"请输入当前密码");
		}
		Member mb=memberService.getMemberByUsername(username);
		getRequest().setAttribute("mb",mb); 
		//String oldPwd=DigestUtils.md5Hex(currentPwd);
		String password=getRequest().getParameter("password");
		String repassword=getRequest().getParameter("repassword");
		if(StringUtils.isBlank(password)){
			return ajax(Status.error,"请输入新密码");
		}
		if(StringUtils.isBlank(repassword)){
			return ajax(Status.error,"请输入确认密码");
		}
//		String newPwd=DigestUtils.md5Hex(password);
//		String rePwd=DigestUtils.md5Hex(repassword);
		if(!StringUtils.equals(password, repassword)){
			return ajax(Status.error,"新密码与确认密码不一致");
		}
		if(!StringUtils.equals(currentPwd, mb.getPassword())){
			
			return ajax(Status.error,"原始密码错误，请重新输入");
		}
	    
		mb.setPassword(password);
		memberService.update(mb);
		return ajax(Status.success,"密码修改成功");
	}
	//前台remote:验证当前密码是不是数据库中的密码
	public String vilidatePassWord() throws IOException{
		boolean compare=false;
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		String currentPwd=getRequest().getParameter("currentPwd");
		Member mb=memberService.getMemberByUsername(username);
		getRequest().setAttribute("mb",mb); 
		if (StringUtils.equals(currentPwd, mb.getPassword())){
	    	compare=true;
	    }
		getResponse().getWriter().print(compare);
		
		return null;
	}
	public String memberCenter(){
		return "center";
	}
	//会员登录
	public String memberLogin(){
		
		return "login";
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}

	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}

	public Boolean getIsAgreeAgreement() {
		return isAgreeAgreement;
	}

	public void setIsAgreeAgreement(Boolean isAgreeAgreement) {
		this.isAgreeAgreement = isAgreeAgreement;
	}

	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}
	public Md5PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	public List<MemberProductHold> getProductList() {
		return productList;
	}


	public void setProductList(List<MemberProductHold> productList) {
		this.productList = productList;
	}


	public List<MemberProductHoldLog> getProductHistoryList() {
		return productHistoryList;
	}


	public void setProductHistoryList(List<MemberProductHoldLog> productHistoryList) {
		this.productHistoryList = productHistoryList;
	}


	public Double getTotal() {
		return total;
	}

	public void setTotal( Double total) {
		this.total = total;
	}
}