package com.rzrk.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractLog;
import com.rzrk.service.ContractLogService;

@ParentPackage("admin")
public class ContractLogAction  extends BaseAdminAction {
	@Resource
	private ContractLogService contractLogService;
	
	private String rowId;
	private String contractCategoryId;
	
	public String getRowId() {
		return rowId;
	}



	public void setRowId(String rowId) {
		this.rowId = rowId;
	}



	public String getContractCategoryId() {
		return contractCategoryId;
	}



	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}



	// 编辑
	public String view() {
		List<ContractLog> contractLogList = contractLogService.queryContractLog(contractCategoryId, rowId,getLoginAdmin());
		getRequest().setAttribute("contractLogList", contractLogList);
		return VIEW;
	}

	
}
