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
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

/**
 * 实体类工作流节点
 * 
 * @author Dell
 *
 */

@Entity
@Table(name = "rzrk_workflow_point")
public class WorkFlowPoint extends BaseEntity {

	private static final long serialVersionUID = -7519486823153844426L;

	private String pointSort;// 节点序号
	private String pointLocation;// 节点位置 0：开始 1：中间 2：结束
	private String nextPonit; // 下一节点
	private String pointName; // 节点名称
	private String checkSelect; // 审批人选择 1、自由选定 2、列表选择 3、直属主管
	private WorkFlow workFlow; //工作流流程
	private String  userName;  //用户名称全写
	private String  name; //用户名称缩写
	private String  userId;//用户id
	private String  isFile; //是否支持附件   1：是  2:否
	private String  isAdd;//是否新增  1：新增  2：工作流创建
	private Work work; //内置工作流
	private Set<Admin> workFlowSet = new HashSet<Admin>();// 审批人范围
	private String  isBranch;//是否为分支节点  1:是  2：不是
	private String  keyName;//分支节点关键字  普通节点不需要
	private String  userCondition;//用户的条件   格式    用户id:条件，用户id:条件
	private String  showCondition;//页面显示用户-条件
	private String  searchName; //分支节点搜索name绑定在分支    部门经理、总经理
	private String  formKeyName;//表单关键字   
	private String  workFlowPointId; //流程节点id-版本号

	/**
	 * 无参构造函数
	 */
	public WorkFlowPoint() {
	}
   
	@Column(name="pointSort",length=4)
	public String getPointSort() {
		return pointSort;
	}

	public void setPointSort(String pointSort) {
		this.pointSort = pointSort;
	}

	@Column(name="pointLocation",length=4)
	public String getPointLocation() {
		return pointLocation;
	}
	public void setPointLocation(String pointLocation) {
		this.pointLocation = pointLocation;
	}

	@Column(name="nextPonit",length=60)
	public String getNextPonit() {
		return nextPonit;
	}

	public void setNextPonit(String nextPonit) {
		this.nextPonit = nextPonit;
	}
	
	
	@Column(name="pointName")
	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	@Column(name="checkSelect",length=4)
	public String getCheckSelect() {
		return checkSelect;
	}

	public void setCheckSelect(String checkSelect) {
		this.checkSelect = checkSelect;
	}
//,cascade={CascadeType.MERGE}
	@ManyToMany(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_point_admin_set")
	public Set<Admin> getWorkFlowSet() {
		
		return workFlowSet;
	}

	public void setWorkFlowSet(Set<Admin> workFlowSet) {
		this.workFlowSet = workFlowSet;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="workFlowId")
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	@Column(name="isFile",length=4)
	public String getIsFile() {
		return isFile;
	}

	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}

	@Column(name="userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="spellName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="isAdd",length=2)
	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}
	
   @Column(name="userId",length=1024)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="workId")
	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}
	
	@Column(name="isBranch",length=2)
	public String getIsBranch() {
		return isBranch;
	}

	public void setIsBranch(String isBranch) {
		this.isBranch = isBranch;
	}

	@Column(name="keyName",length=20)
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Column(name="userCondition",length=1024)
	public String getUserCondition() {
		return userCondition;
	}

	public void setUserCondition(String userCondition) {
		this.userCondition = userCondition;
	}
	@Column(name="showCondition",length=1024)
	public String getShowCondition() {
		return showCondition;
	}

	public void setShowCondition(String showCondition) {
		this.showCondition = showCondition;
	}

	@Column(name="searchName",length=100)
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	@Column(name="formKeyName",length=30)
	public String getFormKeyName() {
		return formKeyName;
	}

	public void setFormKeyName(String formKeyName) {
		this.formKeyName = formKeyName;
	}
	@Column(name="workFlowPointId",length=255)
	public String getWorkFlowPointId() {
		return workFlowPointId;
	}

	public void setWorkFlowPointId(String workFlowPointId) {
		this.workFlowPointId = workFlowPointId;
	}
	
	
	
	
	
	

}