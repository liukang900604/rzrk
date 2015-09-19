package com.rzrk.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rzrk_work_contract_record")
public class WorkContractRecord extends BaseEntity {
	String workId;
	String rowId;
	String workFlowId;
	String contractCategoryId;
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getContractCategoryId() {
		return contractCategoryId;
	}
	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}
	
	
}
