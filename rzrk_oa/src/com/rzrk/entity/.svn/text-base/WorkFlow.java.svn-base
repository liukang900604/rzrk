package com.rzrk.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
/**
 * 实体类 - 工作流
 * 
 * @author kang.liu
 */

@Entity
@Table(name = "rzrk_workflow")
public class WorkFlow extends BaseEntity{

	private static final long serialVersionUID = -7519486823153844426L;
	
	private String flowName;// 流程名称
	private WorkFlowForm flowForm;// 实际流程表单
	private WorkFlowType flowType;// 流程类型
	private String flowContent; // 流程介绍
	private String isDelete;// 是否可以删除 特殊工作流使用  1:可以  2：不可以
	private Set<WorkFlowPoint> WorkFlowPointSet = new HashSet<WorkFlowPoint>();// 节点
	private String isMsg; //是否短信  1：是  0：否
	private String isEmail;//是否邮件 1：是  0：否

	private String currentFlowId;	//保存原流程Id
	private Long version = 0l;//初始版本号
	private int  isHistory = 1; //1：当前   2：历史
	/**
	 * 无参构造方法
	 */
	public WorkFlow() {
	}

	@Column(name = "flowName", length = 50)
	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formId")
	public WorkFlowForm getFlowForm() {
		return flowForm;
	}

	public void setFlowForm(WorkFlowForm flowForm) {
		this.flowForm = flowForm;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flowTypeId")
	public WorkFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(WorkFlowType flowType) {
		this.flowType = flowType;
	}

	@Column(name = "flowContent", length = 200)
	public String getFlowContent() {
		return flowContent;
	}

	public void setFlowContent(String flowContent) {
		this.flowContent = flowContent;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "workFlow", orphanRemoval = true)
	@OrderBy("pointSort asc")
	public Set<WorkFlowPoint> getWorkFlowPointSet() {
		return WorkFlowPointSet;
	}

	public void setWorkFlowPointSet(Set<WorkFlowPoint> workFlowPointSet) {
		WorkFlowPointSet = workFlowPointSet;
	}

	@Column(name = "isDelete", length = 2)
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
   @Column(name="isMsg",length=2)
	public String getIsMsg() {
		return isMsg;
	}

	public void setIsMsg(String isMsg) {
		this.isMsg = isMsg;
	}

	@Column(name="isEmail",length=2)
	public String getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}

	@Column
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name="isHistory",length=2)
	public int getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(int isHistory) {
		this.isHistory = isHistory;
	}

	@Column(name="currentFlowId",length=255)
	public String getCurrentFlowId() {
		return currentFlowId;
	}

	public void setCurrentFlowId(String currentFlowId) {
		this.currentFlowId = currentFlowId;
	}
	
	
	
	
	  

	
	
}