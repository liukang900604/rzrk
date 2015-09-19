package com.rzrk.entity;

import javax.persistence.Entity;

@Entity
public class JobLevel extends BaseEntity {
	
	private String name;//职务级别名称
	private String code;//职务级别代码
    private int sortNo;//排序号
    private int duplicateSortDeal;// 重复序号处理 0，插入；1，排序
    private int status;//状态
    private String description;//描述
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code == null?"" : code;
	}
	public void setCode(String code) {
		this.code = code;
	}


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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description == null ? "" : description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
	
    
}
