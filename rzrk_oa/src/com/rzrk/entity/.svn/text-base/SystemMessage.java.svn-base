/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 系统消息
 */

@Entity
public class SystemMessage extends BaseEntity  {

	public enum SystemmessageType{
		系统消息, 个人消息,审批消息
	}//消息类别枚举
	public enum RedType{
		未读, 已读
	}//消息是否读取枚举
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;// 标题
	private SystemmessageType type;// 类型
	private Date createTime;// 时间
	private String context;// 信息内容
	private String isMsg; //是否短信  1：是  0：否
	private String isEmail;//是否邮件 1：是  0：否
	private RedType redType;//0 未读  1已读
	private Admin loginAdmin; //发布人
	private String isDelete; //已发信息是否删除
	private String workId; //工作id   
	private String currentPointId;//当前节点
    private String isInternal;//是否为内置工作流  1：非内置正常工作流   2：内置工作流
	private List<Admin> toMessagAdmin = new ArrayList<Admin>();//消息接收者


	


	public SystemMessage(String title, SystemmessageType type, Date createTime,
			String context, String isMsg, String isEmail, RedType redType,
			Admin loginAdmin, String isDelete, String workId,
			String currentPointId, List<Admin> toMessagAdmin) {
		super();
		this.title = title;
		this.type = type;
		this.createTime = createTime;
		this.context = context;
		this.isMsg = isMsg;
		this.isEmail = isEmail;
		this.redType = redType;
		this.loginAdmin = loginAdmin;
		this.isDelete = isDelete;
		this.workId = workId;
		this.currentPointId = currentPointId;
		this.toMessagAdmin = toMessagAdmin;
	}

	/**
	 * 无参构造器
	 */
	public SystemMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public SystemmessageType getType() {
		return type;
	}
	public void setType(SystemmessageType type) {
		this.type = type;
	}
	@Column
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}

	public String getIsMsg() {
		return isMsg;
	}

	public void setIsMsg(String isMsg) {
		this.isMsg = isMsg;
	}

	public String getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}

	@Column
	public RedType getRedType() {
		return redType;
	}
	public void setRedType(RedType redType) {
		this.redType = redType;
	}
	@Column
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="adminId")
	@ForeignKey(name = "fk_admin")
	public Admin getLoginAdmin() {
		return loginAdmin;
	}
	public void setLoginAdmin(Admin loginAdmin) {
		this.loginAdmin = loginAdmin;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_message_admin")
	public List<Admin> getToMessagAdmin() {
		return toMessagAdmin;
	}
	public void setToMessagAdmin(List<Admin> toMessagAdmin) {
		this.toMessagAdmin = toMessagAdmin;
	}
	
	@Column(name="workId",length=255)
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}

	@Column(name="currentPointId",length=255)
	public String getCurrentPointId() {
		return currentPointId;
	}

	public void setCurrentPointId(String currentPointId) {
		this.currentPointId = currentPointId;
	}

	public String getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	
	
	


	
	

	
}