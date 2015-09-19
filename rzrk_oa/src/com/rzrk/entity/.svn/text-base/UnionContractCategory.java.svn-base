package com.rzrk.entity;

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
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.ForeignKey;

import com.rzrk.util.JsonUtil;
import com.rzrk.vo.unioncontract.Definition;

@Entity
public class UnionContractCategory  extends BaseEntity{
	public static final String CONTROL_TYPE_BY_DEP = "bydep";//按部门
	public static final String CONTROL_TYPE_BY_OP = "byop";//按人
	public static final String CONTROL_TYPE_BY_OP_CREATE_ADMIN = "__create_admin"; //按人排序时默认的按创建人

	private String name;
	private String definition;
	private RootContractCategory rootContractCategory;
	private String primaryTable; //主表
	private String primaryField; //主键
	private String  isView;//是否允许部门下人查看  0：否  1：是
	private String  isSuperiorView;//是否允许上级部门领导查看  0：否  1：是
	private String  isSubDepView;//是否允许下级部门查看  0：否  1：是  
	private String controlType;// byDep按部门限制 byOp指定字段人限制
	private String controlField;// 为byOp辅助， 创建人为__createAdmin,其他为字段名
	private boolean contentProvider;//是否作为内容提供者,辅助definition的查询用
	private Set<Deparment> deparmentSet = new HashSet<Deparment>();// 部门

//	Set<QueryHistory> queryHistorySet;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=10240)
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	@Transient
	public Definition getDefinitionObj(){
		if(StringUtils.isEmpty(definition)){
			return new Definition();
		}else{
			return JsonUtil.toObject(definition,Definition.class);
		}
	}
	
	public String getPrimaryTable() {
		return primaryTable;
	}
	public void setPrimaryTable(String primaryTable) {
		this.primaryTable = primaryTable;
	}
	public String getPrimaryField() {
		return primaryField;
	}
	public void setPrimaryField(String primaryField) {
		this.primaryField = primaryField;
	}
	@Column(name="isView",length=2)
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	@Column(name="isSuperiorView",length=2)
	public String getIsSuperiorView() {
		return isSuperiorView;
	}
	public void setIsSuperiorView(String isSuperiorView) {
		this.isSuperiorView = isSuperiorView;
	}
	@Column(name="isSubDepView",length=2)
	public String getIsSubDepView() {
		return isSubDepView;
	}
	public void setIsSubDepView(String isSubDepView) {
		this.isSubDepView = isSubDepView;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getControlField() {
		return controlField;
	}
	public void setControlField(String controlField) {
		this.controlField = controlField;
	}
	public boolean isContentProvider() {
		return contentProvider;
	}
	public void setContentProvider(boolean contentProvider) {
		this.contentProvider = contentProvider;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_union_category_deparment_set")
	public Set<Deparment> getDeparmentSet() {
		return deparmentSet;
	}
	public void setDeparmentSet(Set<Deparment> deparmentSet) {
		this.deparmentSet = deparmentSet;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_root_contract_category")
	public RootContractCategory getRootContractCategory() {
		return rootContractCategory;
	}
	public void setRootContractCategory(RootContractCategory rootContractCategory) {
		this.rootContractCategory = rootContractCategory;
	}
	
//	@OneToMany(mappedBy="viewlayer",cascade=CascadeType.ALL,orphanRemoval=true)
//	public Set<QueryHistory> getQueryHistorySet() {
//		return queryHistorySet;
//	}
//	public void setQueryHistorySet(Set<QueryHistory> queryHistorySet) {
//		this.queryHistorySet = queryHistorySet;
//	}

	
}
