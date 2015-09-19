/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.rzrk.entity.*;
import com.rzrk.service.*;
import com.rzrk.util.date.DateUtils;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类 - 客户管理
 */

@ParentPackage("admin")
public class MemberAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Member member;
	private Map<String, String[]> memberAttributeValueMap;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = member.getUsername();
		if (memberService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	// 查看
	public String view() {
		member = memberService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		//pager = memberService.findPager(pager);
		return LIST;
	}
	
	
	public String getAjaxList(){
		processAjaxPagerRequestParameter();
		pager = memberService.findPager(pager);
		List<Member> memberList = (List<Member>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < memberList.size(); i++ ){
		    Member temp = memberList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("username", temp.getUsername());
	        map.put("realName",temp.getRealName());
	        map.put("userIdentification", temp.getUserIdentification());
	        map.put("bank", temp.getBank());
	        map.put("bankAccount", temp.getBankAccount());
	        map.put("mobile", temp.getMobile());
	        map.put("email", temp.getEmail());
	        map.put("address", temp.getAddress());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}
	//
	public String getMemberList(){
		List<Member> memberList  = null;
		//获取所有的人员信息
		memberList = memberService.getAllList();
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < memberList.size(); i++ ){
		    Member temp = memberList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("username", temp.getUsername());
	        map.put("realname",temp.getRealName());
	        map.put("userIdentification", temp.getUserIdentification());
	        map.put("bank", temp.getBank());
	        map.put("bankAccount", temp.getBankAccount());
	        map.put("mobile", temp.getMobile());
	        map.put("email", temp.getEmail());
	        map.put("address", temp.getAddress());
	        jsonObj.add(map);        
		}
		
		 Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("total", pager.getTotalCount()); 
	        map.put("rows", jsonObj); 
	        
			return ajax(map);
	}
	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除客户信息: ");
		memberService.delete(ids);
		logInfo = logInfoStringBuffer.toString();
		return ajax(Status.success, "删除成功!");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		member = memberService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "member.realName", message = "真实姓名不允许为空!") 
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "member.userIdentification", message = "身份证号不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.bank", message = "银行名称不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.bankAccount", message = "银行账号不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}
//	    emails = { 
//		@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
//		},
//		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "member.score", min = "0", message = "积分必须为零或正整数!")
//		},
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (memberService.isExistByUsername(member.getUsername())) {
			addActionError("用户名已存在!");
			return ERROR;
		}

		member.setUsername(member.getUsername().toLowerCase());
		//member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setPassword(member.getPassword());
		memberService.save(member);

		logInfo = "添加客户信息: " + member.getUsername();
		
		redirectUrl = "member!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
					@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
					//@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
					@RequiredStringValidator(fieldName = "member.realName", message = "真实姓名不允许为空!") 
		},
		requiredFields = { 
					@RequiredFieldValidator(fieldName = "member.userIdentification", message = "身份证号不允许为空!"),
					@RequiredFieldValidator(fieldName = "member.bank", message = "银行名称不允许为空!"),
					@RequiredFieldValidator(fieldName = "member.bankAccount", message = "银行账号不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}
//		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "member.score", min = "0", message = "积分必须为零或正整数!")
//		},
	)
	@InputConfig(resultName = "error")
	public String update() {
		Member persistent = memberService.load(id);
		if (StringUtils.isEmpty(member.getPassword())) {
			member.setPassword(persistent.getPassword());
		} else {
			//member.setPassword(DigestUtils.md5Hex(member.getPassword()));
			member.setPassword(member.getPassword());
		}
		BeanUtils.copyProperties(member, persistent, new String[] {"id", "createDate", "modifyDate", "password", "phone",  "username", "zipCode"});
		memberService.update(persistent);
		logInfo = "编辑客户信息: " + persistent.getUsername();
		redirectUrl = "member!list.action";
		return SUCCESS;
			
	}
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Map<String, String[]> getMemberAttributeValueMap() {
		return memberAttributeValueMap;
	}

	public void setMemberAttributeValueMap(Map<String, String[]> memberAttributeValueMap) {
		this.memberAttributeValueMap = memberAttributeValueMap;
	}

}