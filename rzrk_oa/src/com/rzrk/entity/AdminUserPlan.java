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
 * Purpose: 个人用户工作计划 -我的关注                                                                    
 * @version 1.0                                                        
 * @author kang.liu
 */
@Entity
@Table(name = "rzrk_admin_user_plan")
public class AdminUserPlan extends BaseEntity {

	/**  */
	private static final long serialVersionUID = 6256470272590949832L;
	
    private Admin followAdmin;
    private UserPlan userPlan;
	private String isFollow;//是否关注  1：关注   2：不关注
    
   
	/**
	 * 
	 */
	public AdminUserPlan() {
	}

	/**
	 * @param getFollowAdmin
	 * @param userPlan
	 */
	public AdminUserPlan(Admin followAdmin, UserPlan userPlan) {
		this.followAdmin = followAdmin;
		this.userPlan = userPlan;
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
	@JoinColumn(name="userPlanId")
	public UserPlan getUserPlan() {
		return userPlan;
	}

	public void setUserPlan(UserPlan userPlan) {
		this.userPlan = userPlan;
	}

	@Column(name="isFollow",length=2)
	public String getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}
	
	
	
}
