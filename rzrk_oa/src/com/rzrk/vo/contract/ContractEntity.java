package com.rzrk.vo.contract;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContractEntity<K extends String, V extends String> extends LinkedHashMap<K, V>{
	String rowId;
	String contractCategoryId;
	String projectId;
	Date createTime;
	Date createAdminId;
	Date createModifyId;
	String createAdminName;
	String createModifyName;
	
	public ContractEntity() {
	}
	
	public ContractEntity(Map<K,V> map){
		putAll(map);
	}
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateAdminId() {
		return createAdminId;
	}
	public void setCreateAdminId(Date createAdminId) {
		this.createAdminId = createAdminId;
	}
	public Date getCreateModifyId() {
		return createModifyId;
	}
	public void setCreateModifyId(Date createModifyId) {
		this.createModifyId = createModifyId;
	}
	public String getCreateAdminName() {
		return createAdminName;
	}
	public void setCreateAdminName(String createAdminName) {
		this.createAdminName = createAdminName;
	}
	public String getCreateModifyName() {
		return createModifyName;
	}
	public void setCreateModifyName(String createModifyName) {
		this.createModifyName = createModifyName;
	}
	public String getContractCategoryId() {
		return contractCategoryId;
	}
	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
