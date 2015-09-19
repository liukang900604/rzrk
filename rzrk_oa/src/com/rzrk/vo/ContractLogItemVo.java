package com.rzrk.vo;

public class ContractLogItemVo {
	String fieldName;
	String oldValue;
	String newValue;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	@Override
	public String toString() {
		return "ContractLogItemVo [fieldName=" + fieldName + ", oldValue="
				+ oldValue + ", newValue=" + newValue + "]";
	}
	
}
