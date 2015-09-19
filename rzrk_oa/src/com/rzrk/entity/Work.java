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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.ForeignKey;
/**
 * 实体类 - 工作
 * 
 * @author kang.liu
 */

@Entity
@Table(name = "rzrk_work")
public class Work extends BaseEntity {

	private static final long serialVersionUID = -7519486823153844426L;

	private String workName; // 工作名称
	private WorkFlowType flowType;// 流程类型
	private WorkFlow workFlow;// 流程
	private String formContent;// 表单内容
	private String status = "3"; // 工作状态 已完成（1：取消 2 正常结束） 待办（3：待审批 4 被撤回）  默认待审批
	private String isComplete = "1";  //是否完成  1：待办  2： 完成  默认待办
	private Admin  currentAdmin;//当前处理人
	private String currentAdminName;//当前处理人名
	private String currentId;//当前处理人id
    private Admin  admin;//流程发起人
    private Admin  lastAdmin;//最近审批人
    private String lastAdminName;//最后审批人名字
    private Set<ApprovalRecord> record = new HashSet<ApprovalRecord>();//审签记录
    private String code;//拼音首字母字符串
    private String wokrCode;//我的工作首字母
    private String fileName;//文件名 
    private String filePath;//文件真实路径
    private Set<ContractField> fieldSet = new HashSet<ContractField>();//审签记录
    private String isDelete; //是否删除Field  1:删除  2： 保存 3：更新
    private String isInternal;//是否为内置工作流  1：非内置正常工作流   2：内置工作流

	private String  expand; //扩展字段，需求管理对应    Project4workflow；  requestField以后删除
	private String  workType; // 1:立项工作(提交开发审批工作),2、收款确认工作  3、:部署工作、提交需求工作
	private Long  version; //流程版本号
	
	/*
	 * 工作流与工作流定义解绑字段,我的工作最后一个节点审批完成或者工作删除 解除两者关系
	 */
	private String flowName;//流程名称
	private String flowId;//流程id
	private String flowTypeName;//流程类型名称
	private String flowTypeId; //流程类型Id
	private String currentPointId;//当前节点id
	private String currentPointName;//当前节点名称
	private String lastPointId;//上一节点id
	private String lastPointName;//上一节点名称
	
	private String  checkSort;//审批顺序   json格式  { 节点序号:审批人,节点序号:审批人..}
	private String checkPerson;//审批过的人
	
	private  Date endDate;//工作结束日期
	
	@Column(length=10240)
	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}


	/**
	 * 无参构造方法
	 */
	public Work() {
	}

	@Column(name="workName",length=200)
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="flowTypeId")
	public WorkFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(WorkFlowType flowType) {
		this.flowType = flowType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="workFlowId")
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	@Column(name="formContent",length=11028)
	public String getFormContent() {
		return formContent;
	}

	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}
   @Column(name="status",length=40)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="adminId")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@OneToMany(cascade={CascadeType.ALL},mappedBy="work")
	@OrderBy("createDate asc")
	public Set<ApprovalRecord> getRecord() {
		return record;
	}

	public void setRecord(Set<ApprovalRecord> record) {
		this.record = record;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lastAdminId")
	public Admin getLastAdmin() {
		return lastAdmin;
	}

	public void setLastAdmin(Admin lastAdmin) {
		this.lastAdmin = lastAdmin;
	}
	
   @Column(name="lastAdminName",length = 40)
	public String getLastAdminName() {
		return lastAdminName;
	}

	public void setLastAdminName(String lastAdminName) {
		this.lastAdminName = lastAdminName;
	}
	 @Column(name="isComplete",length = 2)
	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="currentAdminId")
	public Admin getCurrentAdmin() {
		return currentAdmin;
	}

	public void setCurrentAdmin(Admin currentAdmin) {
		this.currentAdmin = currentAdmin;
	}
	
	@Column(name="currentAdminName",length = 120)
	public String getCurrentAdminName() {
		return currentAdminName;
	}

	public void setCurrentAdminName(String currentAdminName) {
		this.currentAdminName = currentAdminName;
	}

	@Column(name="currentId",length=600)
	public String getCurrentId() {
		return currentId;
	}

	public void setCurrentId(String currentId) {
		this.currentId = currentId;
	}

    @Column(name="code",length=20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="fileName",length=1024)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    @Column(name="filePath",length=10240)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_work_field_set")
	public Set<ContractField> getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(Set<ContractField> fieldSet) {
		this.fieldSet = fieldSet;
	}

	@Column(name="isDelete",length = 2)
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="workCode",length=20)
	public String getWokrCode() {
		return wokrCode;
	}

	public void setWokrCode(String wokrCode) {
		this.wokrCode = wokrCode;
	}
	
	@Column(name="isInternal",length=2)
	public String getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	@Column(name="workType",length=2)
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	@Column(name = "flowName", length = 30)
	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Column(name="flowId",length=255)
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	@Column(name="flowTypeName",length=30)
	public String getFlowTypeName() {
		return flowTypeName;
	}

	public void setFlowTypeName(String flowTypeName) {
		this.flowTypeName = flowTypeName;
	}

	@Column(name="type_Id",length=255)
	public String getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getCurrentPointId() {
		return currentPointId;
	}

	public void setCurrentPointId(String currentPointId) {
		this.currentPointId = currentPointId;
	}

	public String getCurrentPointName() {
		return currentPointName;
	}

	public void setCurrentPointName(String currentPointName) {
		this.currentPointName = currentPointName;
	}

	public String getLastPointId() {
		return lastPointId;
	}

	public void setLastPointId(String lastPointId) {
		this.lastPointId = lastPointId;
	}

	public String getLastPointName() {
		return lastPointName;
	}

	public void setLastPointName(String lastPointName) {
		this.lastPointName = lastPointName;
	}

	public String getCheckSort() {
		return checkSort;
	}

	public void setCheckSort(String checkSort) {
		this.checkSort = checkSort;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    @Column(length=11024)
	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	

	
	
	
	
	
	

	
	
	
	
	
	
  
	

}