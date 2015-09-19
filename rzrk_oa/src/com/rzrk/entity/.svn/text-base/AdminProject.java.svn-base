package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose: 个人项目关注                                                           
 * @version 1.0                                                        
 * @author kang.liu
 */
@Entity
@Table(name = "rzrk_admin_project")
public class AdminProject extends BaseEntity {

	/**  */
	private static final long serialVersionUID = 6256470272590949832L;
	
    private Admin followAdmin;
    private Project project;
	private String isFollow;//是否关注  1：关注   2：不关注
    
   
	/**
	 * 
	 */
	public AdminProject() {
	}



	/**
	 * @param followAdmin
	 * @param project
	 * @param isFollow
	 */
	public AdminProject(Admin followAdmin, Project project, String isFollow) {
		this.followAdmin = followAdmin;
		this.project = project;
		this.isFollow = isFollow;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="followAdminId")
	public Admin getFollowAdmin() {
		return followAdmin;
	}

	public void setFollowAdmin(Admin followAdmin) {
		this.followAdmin = followAdmin;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="projectId")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	

	@Column(name="isFollow",length=2)
	public String getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}


	

	
	
	
}
