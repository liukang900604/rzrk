package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类 - 工作流
 * 
 * @author kang.liu
 */

@Entity
@Table(name = "rzrk_workflowHistory")
public class WorkFlowHistory extends BaseEntity{

	private String flowName;// 流程名称
	private String flowFormId;// 实际流程表单
	private String flowTypeId;// 流程类型
	private String flowContent; // 流程介绍
	private String isDelete;// 是否可以删除 特殊工作流使用  1:可以  2：不可以
	/*private Set<Admin> adminSet = new HashSet<Admin>();// 审批人范围
	private Set<WorkFlowPoint> WorkFlowPointSet = new HashSet<WorkFlowPoint>();// 节点
*/	private String isMsg; //是否短信  1：是  0：否
	private String isEmail;//是否邮件 1：是  0：否

	private Long version;	//版本号
	private String uuid;	//保存原流程Id
	
	
	@Column
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Column
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 无参构造方法
	 */
	public WorkFlowHistory() {
	}
	


	@Column(name = "flowName", length = 50)
	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Column(name = "flowFormId")
	public String getFlowFormId(){
		return flowFormId; 
	}
	
	public void setFlowFormId(String flowFormId){
		this.flowFormId = flowFormId;
	}
	
	@Column(name = "flowTypeId")
	public String getFlowTypeId(){
		return flowTypeId;
	}
	
	public void setFlowTypeId(String flowTypeId){
		this.flowTypeId = flowTypeId;
	}

	@Column(name = "flowContent", length = 200)
	public String getFlowContent() {
		return flowContent;
	}

	public void setFlowContent(String flowContent) {
		this.flowContent = flowContent;
	}
 
	/*@OneToMany(cascade = CascadeType.ALL)
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "workFlow", orphanRemoval = true)
	@OrderBy("pointSort asc")
	public Set<WorkFlowPoint> getWorkFlowPointSet() {
		return WorkFlowPointSet;
	}

	public void setWorkFlowPointSet(Set<WorkFlowPoint> workFlowPointSet) {
		WorkFlowPointSet = workFlowPointSet;
	}*/

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
	

}