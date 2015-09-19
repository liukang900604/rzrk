package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose: 用户工作计划                                                                    
 * @version 1.0                                                        
 * @author songkez
 * @since 2015-6-8 
 */
@Entity
public class UserPlan extends BaseEntity {

	/**  */
	private static final long serialVersionUID = 6256470272590949832L;
	
	private int userPlanUUID;
	
	private Admin admin;
	
	private String name;
	
	private String content;
	
	private String beginTime;
	
	private String preEndTime;//预期结束时间
	private String endTime;
	
	private double progress;
	
	private String remark;
	
	private Admin creator;
	private Admin testPerson;//测试人
	public enum Status{
//		新建, 开发中, 反馈, 认可, 已确认, 已解决, 已完成, 已关闭,测试中,暂停中,合并中,回归测试中
		新建, 开发中, 反馈, 已完成, 已关闭,测试中,暂停中,合并中,回归测试中
	}
	
	public enum Severity{
		请选择, 新功能, 细节, 文字, 小调整, 小错误, 很严重, 崩溃, 宕机
	}
	
	public enum Plantype{
		请选择, Client端功能错误, Portal端功能错误, Server端功能错误, 性能瓶颈, 界面问题, 设计缺陷, 需求, 非bug
	}
	
	public enum Reproducibility{
		请选择, 总是, 有时, 随机, 没有试验, 无法重现, 不适用
	}
	
	private Status status;
	private Severity severity;
	private Plantype plantype;
	private Reproducibility reproducibility;
	private Project project;
	
    private String fileName;//文件名 
    private String filePath;//文件真实路径
    
    private String objectiveVersion;//目标版本
    private String productVersion;//产品版本
    private String objectiveVersionName;//目标版本名称
    private String productVersionName;//产品版本名称
    private String copyLink;//复制链接
    
	
	
	
	public UserPlan(String id) {
		this.id = id;
	}
	
	public UserPlan() {
	}
	
	public int getUserPlanUUID() {
		return userPlanUUID;
	}

	public void setUserPlanUUID(int userPlanUUID) {
		this.userPlanUUID = userPlanUUID;
	}

	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_userplan_admin")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	@LogClass(comments="计划名称修改:")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@LogClass(comments="计划内容修改:")
	@Column(length=10240)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@LogClass(comments="开始时间修改:")
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@LogClass(comments="实际结束时间修改:")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@LogClass(comments="进度修改:")
	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	@LogClass(comments="备注修改:")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@LogClass(comments="状态修改:")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@LogClass(comments="类型修改:")
	@Column(name="severity",nullable=false)
	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	@LogClass(comments="严重性修改:")
	@Column(name="plantype",nullable=false)
	public Plantype getPlantype() {
		return plantype;
	}

	public void setPlantype(Plantype plantype) {
		this.plantype = plantype;
	}
	@LogClass(comments="出现频率修改:")
	@Column(name="reproducibility",nullable=false)
	public Reproducibility getReproducibility() {
		return reproducibility;
	}


	public void setReproducibility(Reproducibility reproducibility) {
		this.reproducibility = reproducibility;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_user_plan_project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_user_plan_admin")
	public Admin getCreator() {
		return creator;
	}

	public void setCreator(Admin creator) {
		this.creator = creator;
	}
	
	@LogClass(comments="文件名称修改:")
	@Column(name="fileName",length=1024)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="filePath",length=1024)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=3000)
	public String getObjectiveVersion() {
		return objectiveVersion;
	}

	public void setObjectiveVersion(String objectiveVersion) {
		this.objectiveVersion = objectiveVersion;
	}
	@Column(length=3000)
	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	@LogClass(comments="预计结束时间修改:")
	public String getPreEndTime() {
		return preEndTime;
	}

	public void setPreEndTime(String preEndTime) {
		this.preEndTime = preEndTime;
	}

	public String getCopyLink() {
		return copyLink;
	}

	public void setCopyLink(String copyLink) {
		this.copyLink = copyLink;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_user_plan_test_admin")
	public Admin getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(Admin testPerson) {
		this.testPerson = testPerson;
	}
	@LogClass(comments="目标版本修改:")
	@Column(length=3000)
	public String getObjectiveVersionName() {
		return objectiveVersionName;
	}

	public void setObjectiveVersionName(String objectiveVersionName) {
		this.objectiveVersionName = objectiveVersionName;
	}
	@LogClass(comments="产品版本修改:")
	@Column(length=3000)
	public String getProductVersionName() {
		return productVersionName;
	}

	public void setProductVersionName(String productVersionName) {
		this.productVersionName = productVersionName;
	}
	
	
	
	

	

}
