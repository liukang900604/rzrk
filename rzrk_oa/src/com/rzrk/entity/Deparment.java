package com.rzrk.entity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

@Entity
public class Deparment extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//部门名称
	private Deparment parent;//default null
    private String departmentCode;//部门代码
    private int sortNo;//排序号
    private int duplicateSortDeal;// 重复序号处理 0，插入；1，排序
    private int status;//状态
    private int isCreateSpace = 0;//是否创建部门空间
    private Admin deparmentLeader;//部门主管
    private Admin departmentOtherLeader; //部门分管领导
//   @Deprecated
//    private Admin deparmentAdmin;//部门人员
	private Set<Job> deparmentJob = new HashSet<Job>();// 岗位集合
	private Set<Deparment> childDeparments = new HashSet<Deparment>();//子集合
    private String description;//描述
    private String deparmentAlisa; //部门别名
    private Set<Admin> deparmentAdmins = new LinkedHashSet<Admin>();//部门人员
    
    @ManyToMany(mappedBy="deparmentSet")
    public Set<Admin> getDeparmentAdmins() {
		return deparmentAdmins;
	}
	public void setDeparmentAdmins(Set<Admin> deparmentAdmins) {
		this.deparmentAdmins = deparmentAdmins;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getDuplicateSortDeal() {
		return duplicateSortDeal;
	}

	public void setDuplicateSortDeal(int duplicateSortDeal) {
		this.duplicateSortDeal = duplicateSortDeal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_deparmentment_parent")
	public Deparment getParent() {
		return parent;
	}

	public void setParent(Deparment parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	public Set<Deparment> getChildDeparments() {
		return childDeparments;
	}

	public void setChildDeparments(Set<Deparment> childDeparments) {
		this.childDeparments = childDeparments;
	}

	public String getDepartmentCode() {
		return departmentCode ==null ? "" : departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public int getIsCreateSpace() {
		return isCreateSpace ;
	}

	public void setIsCreateSpace(int isCreateSpace) {
		this.isCreateSpace = isCreateSpace;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@ForeignKey(name = "fk_deparmentLeader_admin")
	public Admin getDeparmentLeader() {
		return deparmentLeader;
	}

	public void setDeparmentLeader(Admin deparmentLeader) {
		this.deparmentLeader = deparmentLeader;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_departmentOtherLeader_admin")
	public Admin getDepartmentOtherLeader() {
		return departmentOtherLeader;
	}

	public void setDepartmentOtherLeader(Admin departmentOtherLeader) {
		this.departmentOtherLeader = departmentOtherLeader;
	}
//	@ManyToOne(fetch = FetchType.LAZY)
//	@ForeignKey(name = "fk_deparment_admin")
//	public Admin getDeparmentAdmin() {
//		return deparmentAdmin;
//	}
//
//	public void setDeparmentAdmin(Admin deparmentAdmin) {
//		this.deparmentAdmin = deparmentAdmin;
//	}

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_deparment_job")
	public Set<Job> getDeparmentJob() {
		return deparmentJob;
	}

	public void setDeparmentJob(Set<Job> deparmentJob) {
		this.deparmentJob = deparmentJob;
	}

	public String getDescription() {
		return description==null ? "" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeparmentAlisa() {
		return deparmentAlisa;
	}
	public void setDeparmentAlisa(String deparmentAlisa) {
		this.deparmentAlisa = deparmentAlisa;
	}
    
    
    
}
