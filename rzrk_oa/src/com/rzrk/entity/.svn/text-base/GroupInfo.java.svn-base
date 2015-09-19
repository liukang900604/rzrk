package com.rzrk.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ForeignKey;

@Entity
public class GroupInfo extends BaseEntity {
	
	private Group group;
	
	private Set<Admin> groupMembers = new HashSet<Admin>();
	
	private Admin groupLeader;//组领导
	
	private Admin groupAssociatedAdmin;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_group_info_group")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	public Set<Admin> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(Set<Admin> groupMembers) {
		this.groupMembers = groupMembers;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_group_info2_admin")
	public Admin getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(Admin groupLeader) {
		this.groupLeader = groupLeader;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_group_info_admin")
	public Admin getGroupAssociatedAdmin() {
		return groupAssociatedAdmin;
	}
	
	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	public void setGroupAssociatedAdmin(Admin groupAssociatedAdmin) {
		this.groupAssociatedAdmin = groupAssociatedAdmin;
	}
	
	

}
