package com.rzrk.vo.unioncontract;

import java.util.ArrayList;
import java.util.List;

public class Field {
	public static final int TYPE_TABLE=0;
	public static final int TYPE_EXPRESSION=1;
	
	int type;
	String tableId;
	String tableName;
	String tableField;
	String showField;
	String expression;
	boolean total;
	//-----
	List<String> departmentIds = new ArrayList<String>(); //所属部门ids
	List<String> adminIds = new ArrayList<String>(); //指定用户ids
	boolean superiorView;  //领导可见
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableField() {
		return tableField;
	}
	public void setTableField(String tableField) {
		this.tableField = tableField;
	}
	public String getShowField() {
		return showField;
	}
	public void setShowField(String showField) {
		this.showField = showField;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	public boolean isTotal() {
		return total;
	}
	public void setTotal(boolean total) {
		this.total = total;
	}
	public List<String> getDepartmentIds() {
		return departmentIds;
	}
	public void setDepartmentIds(List<String> departmentIds) {
		this.departmentIds = departmentIds;
	}
	public List<String> getAdminIds() {
		return adminIds;
	}
	public void setAdminIds(List<String> adminIds) {
		this.adminIds = adminIds;
	}
	public boolean isSuperiorView() {
		return superiorView;
	}
	public void setSuperiorView(boolean superiorView) {
		this.superiorView = superiorView;
	}

	
	
	
	
}
