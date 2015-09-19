package com.rzrk.vo.contract;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Field {
	public static final String TYPE_TEXT="文本框";
	public static final String TYPE_AREA="文本区";
	public static final String TYPE_SELECT="下拉框";
	public static final String TYPE_CHECKBOX="单选框";
	public static final String TYPE_RADIO="复选框";
	public static final String TYPE_SELECT_TREE="选择树";
	
	public static final String BUILT_LEVEL="级别";

	
	String name="";
	boolean required;			//是否必填
	boolean total;			//是否统计
	String type="";
	boolean built;		//是否内置
	String builtsign=""; //内置标记
	Map<String,Object> builtdata = new LinkedHashMap<String,Object>(); //内置标记的参数
	List<String> options = new ArrayList<String>();	//可选项
	String expression; //表达式
	List<String> departmentIds = new ArrayList<String>(); //所属部门ids
	List<String> adminIds = new ArrayList<String>(); //指定用户ids
	boolean superiorView;  //领导可见
	boolean contentProvider;//是否作为内容提供者

	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isTotal() {
		return total;
	}
	public void setTotal(boolean total) {
		this.total = total;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isBuilt() {
		return built;
	}
	public void setBuilt(boolean built) {
		this.built = built;
	}
	public String getBuiltsign() {
		return builtsign;
	}
	public void setBuiltsign(String builtsign) {
		this.builtsign = builtsign;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public List<String> getDepartmentIds() {
		return departmentIds;
	}
	public void setDepartmentIds(List<String> departmentIds) {
		this.departmentIds = departmentIds;
	}
	public boolean isSuperiorView() {
		return superiorView;
	}
	public void setSuperiorView(boolean superiorView) {
		this.superiorView = superiorView;
	}
	
	public Map<String, Object> getBuiltdata() {
		return builtdata;
	}
	public void setBuiltdata(Map<String, Object> builtdata) {
		this.builtdata = builtdata;
	}
	public boolean isContentProvider() {
		return contentProvider;
	}
	public void setContentProvider(boolean contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	public List<String> getAdminIds() {
		return adminIds;
	}
	public void setAdminIds(List<String> adminIds) {
		this.adminIds = adminIds;
	}
	
	
	
	@Override
	public String toString() {
		return "Field [name=" + name + ", total=" + total + ", type=" + type
				+ ", built=" + built + ", builtsign=" + builtsign
				+ ", options=" + options + ", expression=" + expression
				+ ", departmentIds=" + departmentIds + ", superiorView="
				+ superiorView + ", contentProvider=" + contentProvider + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (built ? 1231 : 1237);
		result = prime * result
				+ ((builtsign == null) ? 0 : builtsign.hashCode());
		result = prime * result + (contentProvider ? 1231 : 1237);
		result = prime * result
				+ ((departmentIds == null) ? 0 : departmentIds.hashCode());
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + (superiorView ? 1231 : 1237);
		result = prime * result + (total ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (built != other.built)
			return false;
		if (builtsign == null) {
			if (other.builtsign != null)
				return false;
		} else if (!builtsign.equals(other.builtsign))
			return false;
		if (contentProvider != other.contentProvider)
			return false;
		if (departmentIds == null) {
			if (other.departmentIds != null)
				return false;
		} else if (!departmentIds.equals(other.departmentIds))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (superiorView != other.superiorView)
			return false;
		if (total != other.total)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
