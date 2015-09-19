/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

import com.rzrk.util.JsonUtil;
import com.rzrk.vo.contract.Definition;

/**
 * 实体类 - 文章分类
 */

@Entity
public class ContractCategory extends BaseEntity {

	private static final long serialVersionUID = -513265210715164L;
	public static final String CONTROL_TYPE_BY_DEP = "bydep";//按部门
	public static final String CONTROL_TYPE_BY_OP = "byop";//按人
	public static final String CONTROL_TYPE_BY_OP_CREATE_ADMIN = "__create_admin"; //按人排序时默认的按创建人

	
	private String name;// 名称
	private String fields;// 标识
	private String totals;// 需要统计的列
	private String definition; //描述
	private String script; //初始化脚本
	private Set<ScriptLibrary> scriptLibrarySet; //初始化脚本
	private RootContractCategory rootContractCategory;
	
	private int isUrlView;//0, 不是，1， 是。 用来标识该二级分类为一个URL引用
	private String url;
	private int approvalNeeded; //0，不需审核；1，需要审核。
	private String  isView;//是否允许部门下人查看  0：否  1：是
	private String  isSuperiorView;//是否允许上级部门领导查看  0：否  1：是
	private String  isSubDepView;//是否允许下级部门查看  0：否  1：是  
	private String controlType;// byDep按部门限制 byOp指定字段人限制
	private String controlField;// 为byOp辅助， 创建人为__createAdmin,其他为字段名
	private boolean contentProvider;//是否作为内容提供者,辅助definition的查询用
	ContractCategory parent;
	Set<ContractCategory> childSet = new LinkedHashSet<ContractCategory>();
	
	private Set<Deparment> deparmentSet = new HashSet<Deparment>();// 部门
	private Set<QueryHistory> queryHistorySet = new LinkedHashSet<QueryHistory>();
	private Set<ContractCategoryLog> contractCategoryLogSet = new LinkedHashSet<ContractCategoryLog>();
	private Set<ContractLog> contractLogSet = new LinkedHashSet<ContractLog>();
	
	private String formTemplate;  //字段显示json格式  包含行、列等信息 
	
	public int getIsUrlView() {
		return isUrlView;
	}
	public void setIsUrlView(int isUrlView) {
		this.isUrlView = isUrlView;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(length=11024)
	public String getFormTemplate() {
		return formTemplate;
	}
	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
	}
	
	
	public int getApprovalNeeded() {
		return approvalNeeded;
	}
	public void setApprovalNeeded(int approvalNeeded) {
		this.approvalNeeded = approvalNeeded;
	}
	
	private Admin approval;//
	
	private Set<Admin> viewPowerAdminList = new HashSet<Admin>();
	
	private String  workFlowId;//工作流流程
	
	private boolean recyle;


	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_contract_category_admin_set")
	public Set<Admin> getViewPowerAdminList() {
		return viewPowerAdminList;
	}
	public void setViewPowerAdminList(Set<Admin> viewPowerAdminList) {
		this.viewPowerAdminList = viewPowerAdminList;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_approval_admin")
	public Admin getApproval() {
		return approval;
	}
	public void setApproval(Admin approval) {
		this.approval = approval;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=10240)
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
	@Column(length=10240)
	public String getTotals() {
		return totals;
	}
	public void setTotals(String totals) {
		this.totals = totals;
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_category_deparment_set")
	public Set<Deparment> getDeparmentSet() {
		return deparmentSet;
	}
	public void setDeparmentSet(Set<Deparment> deparmentSet) {
		this.deparmentSet = deparmentSet;
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
	@Column(name="workFlowId",length=255)
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	
	@OneToMany(mappedBy="contractCategory",cascade=CascadeType.ALL,orphanRemoval=true)
	public Set<QueryHistory> getQueryHistorySet() {
		return queryHistorySet;
	}
	public void setQueryHistorySet(Set<QueryHistory> queryHistorySet) {
		this.queryHistorySet = queryHistorySet;
	}
	@OneToMany(mappedBy="contractCategory",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("createDate desc")
	public Set<ContractCategoryLog> getContractCategoryLogSet() {
		return contractCategoryLogSet;
	}
	public void setContractCategoryLogSet(
			Set<ContractCategoryLog> contractCategoryLogSet) {
		this.contractCategoryLogSet = contractCategoryLogSet;
	}
	
	@OneToMany(mappedBy="contractCategory",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("createDate desc")
	public Set<ContractLog> getContractLogSet() {
		return contractLogSet;
	}
	public void setContractLogSet(Set<ContractLog> contractLogSet) {
		this.contractLogSet = contractLogSet;
	}
	public boolean isRecyle() {
		return recyle;
	}
	public void setRecyle(boolean recyle) {
		this.recyle = recyle;
	}
	@Column(length=10240)
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	@Transient
	public Definition getDefinitionObj() {
		if(StringUtils.isEmpty(definition)){
			return new Definition();
		}else{
			return JsonUtil.toObject(definition,Definition.class);
		}
	}
	@Transient
	public void setDefinitionObj(Definition definition) {
		String def = JsonUtil.toJson(definition);
		setDefinition(def);
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
	
	@ManyToOne
	@JoinColumn
	public ContractCategory getParent() {
		return parent;
	}
	public void setParent(ContractCategory parent) {
		this.parent = parent;
	}
	@OneToMany(mappedBy="parent")
	public Set<ContractCategory> getChildSet() {
		return childSet;
	}
	public void setChildSet(Set<ContractCategory> childSet) {
		this.childSet = childSet;
	}
	@Column(length=102400)
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	@ManyToMany
    @JoinTable
	public Set<ScriptLibrary> getScriptLibrarySet() {
		return scriptLibrarySet;
	}
	public void setScriptLibrarySet(Set<ScriptLibrary> scriptLibrarySet) {
		this.scriptLibrarySet = scriptLibrarySet;
	}

	
	
	

}