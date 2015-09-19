/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 文章分类
 */

@Entity
public class ContractField extends BaseEntity {

	private static final long serialVersionUID = -513265210715164L;

	private String  contractCategoryId;// 所属模板
	@Column(length=1024)
	private String  fieldName;// 所属列名
	private String  rowId;// 所属行
	private String  parentRowId;// 父表行
//	private String  rowName;// 所属行
	private String value;// 值内容
	private boolean indication; //是否标志字段
	private Set<Admin> adminList = new HashSet<Admin>();// 查看人范围
	private String currentAdminName;//创建人名
	private String  statu;//状态 0,正常显示；1，审批通过 ；2， 待关联工作流； 3，已关联工作流 4. 驳回  5 取消状态
	private String updateValue;// 存储value值
	private String createAdminId;
	private String createAdminName;
	private String modifyAdminId;
	private String modifyAdminName;
	boolean recyle;
	@Deprecated
	private boolean isProjectRelation; //是否项目关联
	private String projectId;
	
	public String getContractCategoryId() {
		return contractCategoryId;
	}
	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
//	public String getRowName() {
//		return rowName;
//	}
//	public void setRowName(String rowName) {
//		this.rowName = rowName;
//	}
	@Column(length=2048)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isIndication() {
		return indication;
	}
	public void setIndication(boolean indication) {
		this.indication = indication;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_field_admin_set")
	public Set<Admin> getAdminList() {
		return adminList;
	}
	public void setAdminList(Set<Admin> adminList) {
		this.adminList = adminList;
	}
	
	@Column(name="currentAdminName")
	public String getCurrentAdminName() {
		return currentAdminName;
	}
	public void setCurrentAdminName(String currentAdminName) {
		this.currentAdminName = currentAdminName;
	}
	
	@Column(name="statu",length=2)
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	
	@Column(name="updateValue",length=100)
	public String getUpdateValue() {
		return updateValue;
	}
	public void setUpdateValue(String updateValue) {
		this.updateValue = updateValue;
	}
	public String getCreateAdminName() {
		return createAdminName;
	}
	public void setCreateAdminName(String createAdminName) {
		this.createAdminName = createAdminName;
	}
	public String getModifyAdminName() {
		return modifyAdminName;
	}
	public void setModifyAdminName(String modifyAdminName) {
		this.modifyAdminName = modifyAdminName;
	}
	public boolean isRecyle() {
		return recyle;
	}
	public void setRecyle(boolean recyle) {
		this.recyle = recyle;
	}
	public String getCreateAdminId() {
		return createAdminId;
	}
	public void setCreateAdminId(String createAdminId) {
		this.createAdminId = createAdminId;
	}
	public String getModifyAdminId() {
		return modifyAdminId;
	}
	public void setModifyAdminId(String modifyAdminId) {
		this.modifyAdminId = modifyAdminId;
	}
	@Column(name="isProjectRelation")
	@Deprecated
	public boolean isProjectRelation() {
		return isProjectRelation;
	}
	@Deprecated
	public void setProjectRelation(boolean isProjectRelation) {
		this.isProjectRelation = isProjectRelation;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	public String getParentRowId() {
		return parentRowId;
	}
	public void setParentRowId(String parentRowId) {
		this.parentRowId = parentRowId;
	}
	
	@Override
	public String toString() {
		return "ContractField [contractCategoryId=" + contractCategoryId
				+ ", fieldName=" + fieldName + ", rowId=" + rowId + ", value="
				+ value + ", indication=" + indication + ", adminList="
				+ adminList + ", currentAdminName=" + currentAdminName
				+ ", statu=" + statu + ", updateValue=" + updateValue
				+ ", createAdminId=" + createAdminId + ", createAdminName="
				+ createAdminName + ", modifyAdminId=" + modifyAdminId
				+ ", modifyAdminName=" + modifyAdminName + ", recyle=" + recyle
				+ ", isProjectRelation=" + isProjectRelation + ", projectId="
				+ projectId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((adminList == null) ? 0 : adminList.hashCode());
		result = prime
				* result
				+ ((contractCategoryId == null) ? 0 : contractCategoryId
						.hashCode());
		result = prime * result
				+ ((createAdminId == null) ? 0 : createAdminId.hashCode());
		result = prime * result
				+ ((createAdminName == null) ? 0 : createAdminName.hashCode());
		result = prime
				* result
				+ ((currentAdminName == null) ? 0 : currentAdminName.hashCode());
		result = prime * result
				+ ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + (indication ? 1231 : 1237);
		result = prime * result
				+ ((modifyAdminId == null) ? 0 : modifyAdminId.hashCode());
		result = prime * result
				+ ((modifyAdminName == null) ? 0 : modifyAdminName.hashCode());
		result = prime * result + (recyle ? 1231 : 1237);
		result = prime * result + ((rowId == null) ? 0 : rowId.hashCode());
		result = prime * result + ((statu == null) ? 0 : statu.hashCode());
		result = prime * result
				+ ((updateValue == null) ? 0 : updateValue.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractField other = (ContractField) obj;
		if (adminList == null) {
			if (other.adminList != null)
				return false;
		} else if (!adminList.equals(other.adminList))
			return false;
		if (contractCategoryId == null) {
			if (other.contractCategoryId != null)
				return false;
		} else if (!contractCategoryId.equals(other.contractCategoryId))
			return false;
		if (createAdminId == null) {
			if (other.createAdminId != null)
				return false;
		} else if (!createAdminId.equals(other.createAdminId))
			return false;
		if (createAdminName == null) {
			if (other.createAdminName != null)
				return false;
		} else if (!createAdminName.equals(other.createAdminName))
			return false;
		if (currentAdminName == null) {
			if (other.currentAdminName != null)
				return false;
		} else if (!currentAdminName.equals(other.currentAdminName))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (indication != other.indication)
			return false;
		if (modifyAdminId == null) {
			if (other.modifyAdminId != null)
				return false;
		} else if (!modifyAdminId.equals(other.modifyAdminId))
			return false;
		if (modifyAdminName == null) {
			if (other.modifyAdminName != null)
				return false;
		} else if (!modifyAdminName.equals(other.modifyAdminName))
			return false;
		if (recyle != other.recyle)
			return false;
		if (rowId == null) {
			if (other.rowId != null)
				return false;
		} else if (!rowId.equals(other.rowId))
			return false;
		if (statu == null) {
			if (other.statu != null)
				return false;
		} else if (!statu.equals(other.statu))
			return false;
		if (updateValue == null) {
			if (other.updateValue != null)
				return false;
		} else if (!updateValue.equals(other.updateValue))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	
	

	
	
}