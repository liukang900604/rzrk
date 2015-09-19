package com.rzrk.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

import com.rzrk.util.JsonUtil;

@Entity
public class Project extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4666678531921140835L;
	public enum ProjectType{
		开发, 业务
	};
	
	public enum Status{
		已完成,未完成,已作废, 测试中
	}
	
	private String name;
	private ProjectType projectType;
	private String beginTime;
	private String endTime;
	private double progress;
	private Status status;
	private String content;//项目内容
	private Admin creator;//创建人
	private Admin responsibor;//项目责任人
	private Deparment deparment;//项目部门
	private Set<Admin> projectMember = new HashSet<Admin>();//项目成员
	private boolean permissioned; //是否开启权限
	@Deprecated
	private String requestRowids;
	
	private RootContractCategory rootContractCategory;
	
	
	public Project(String id){
		this.id  = id;
	}
	
	public Project(){
		
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_project_root_contract_category")	
	public RootContractCategory getRootContractCategory() {
		return rootContractCategory;
	}
	public void setRootContractCategory(RootContractCategory rootContractCategory) {
		this.rootContractCategory = rootContractCategory;
	}

	@Column(length=1024)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProjectType getProjectType() {
		return projectType;
	}
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_project_creator_admin")
	public Admin getCreator() {
		return creator;
	}
	public void setCreator(Admin creator) {
		this.creator = creator;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_project_responsibor_admin")
	public Admin getResponsibor() {
		return responsibor;
	}
	public void setResponsibor(Admin responsibor) {
		this.responsibor = responsibor;
	}
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_project_deparment")
	public Deparment getDeparment() {
		return deparment;
	}
	public void setDeparment(Deparment deparment) {
		this.deparment = deparment;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_project_admin")
	public Set<Admin> getProjectMember() {
		return projectMember;
	}
	public void setProjectMember(Set<Admin> projectMember) {
		this.projectMember = projectMember;
	}
	@Deprecated
	public String getRequestRowids() {
		return requestRowids;
	}
	@Deprecated
	public void setRequestRowids(String requestRowids) {
		this.requestRowids = requestRowids;
	}
	@Deprecated
	@Transient
	public List<String> getRequestRowidList() {
		if (StringUtils.isBlank(requestRowids)) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(Arrays.asList(requestRowids.split(",")));
	}
	@Deprecated
	@Transient
	public void setRequestRowidList(List<String> list) {
		setRequestRowids(StringUtils.join(list,","));
	}
	public boolean isPermissioned() {
		return permissioned;
	}
	public void setPermissioned(boolean permissioned) {
		this.permissioned = permissioned;
	}
	
	
}
