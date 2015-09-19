package com.rzrk.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ForeignKey;

@Entity
public class Group extends BaseEntity {
	
	enum GROUPTYPE{
		system//系统组
	}//组属性枚举
	
	private String groupName;// 组名
	private String indexNo;//排序号
	private String status;//状态
	private String groupType;//组类型
	private String groupPrivlege;//权限属性 0，公开组，1，似有组
	private Set<Deparment> belongsDepartments = new HashSet<Deparment>();// 所属范围
	private String desciption;//描述
	private GroupInfo groupInfo;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getGroupPrivlege() {
		return groupPrivlege;
	}
	public void setGroupPrivlege(String groupPrivlege) {
		this.groupPrivlege = groupPrivlege;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_group_deparment")
	public Set<Deparment> getBelongsDepartments() {
		return belongsDepartments;
	}

	public void setBelongsDepartments(Set<Deparment> belongsDepartments) {
		this.belongsDepartments = belongsDepartments;
	}

	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public GroupInfo getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}
	
	

}
