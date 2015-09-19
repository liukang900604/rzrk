package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Job extends BaseEntity {
	
	public enum JobType{
		管理, 技术, 销售, 行政
	}//岗位类别枚举
	private String jobName;//岗位名称
	private String jobCode;//岗位代码
    private int sortNo;//排序号
    private int duplicateSortDeal;// 重复序号处理 0，插入；1，排序
	private JobType jobType;//岗位类型
    private int status;//状态
    private String desciption;//描述
    private String isKeyName; //是否为关键字  1：关键字  0：不是关键字   工作流节点使用
    private String  adminId;    //关键字处理人
    private String  priorityLevel; //优先级  
    
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public int getDuplicateSortDeal() {
		return duplicateSortDeal;
	}
	public void setDuplicateSortDeal(int duplicateSortDeal) {
		this.duplicateSortDeal = duplicateSortDeal;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobCode() {
		return jobCode==null? "" : jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public JobType getJobType() {
		return jobType;
	}
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	
	@Column(name="isKeyName",length = 2)
	public String getIsKeyName() {
		return isKeyName;
	}
	public void setIsKeyName(String isKeyName) {
		this.isKeyName = isKeyName;
	}
	
	@Column(name="adminId" ,length = 255)
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Column(name="priorityLevel",length=4)
	public String getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	
	
	
	

    
    
}
