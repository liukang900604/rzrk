package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类 - 工作流表单
 * 
 * @author kang.liu
 */

@Entity
@Table(name = "rzrk_workflow_form")
public class WorkFlowForm extends BaseEntity {

	private static final long serialVersionUID = -7519486823153844426L;

	private String formName;// 表单名称
	private String formContent;// 表单内容
	private String isDelete;// 是否可以删除 特殊工作流使用 1:可以  2：不可以
	private String contractCategoryId;
	private String contractName;//二级分类名称

	/**
	 * 无参构造方法
	 */
	public WorkFlowForm() {
	}

	@Column(name = "formName", length = 50)
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	// 表单内容 默认10kb
	@Column(name = "formContent", length = 11024)
	public String getFormContent() {
		return formContent;
	}

	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}

	@Column(name = "isDelete", length = 2)
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getContractCategoryId() {
		return contractCategoryId;
	}

	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	

}