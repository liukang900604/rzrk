package com.rzrk.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 实体类 - 审批记录
 * 
 * @author kang.liu
 */

@Entity
@Table(name = "rzrk_approvalRecord")
public class ApprovalRecord extends BaseEntity {

	private static final long serialVersionUID = -7519486823153844426L;

	private String approvalPerson;// 审批人
	private String pointName; // 节点名称
	private String status; // 审批状态 （通过，驳回,重新提交）
	private Work  work; //工作记录
	private String advice;//审批建议

	/**
	 * 无参构造方法
	 */
	public ApprovalRecord() {
	}

	@Column(name="approvalPerson",length=40)
	public String getApprovalPerson() {
		return approvalPerson;
	}

	public void setApprovalPerson(String approvalPerson) {
		this.approvalPerson = approvalPerson;
	}

	@Column(name="pointName",length=40)
	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	@Column(name="status",length=20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="workId")
	public Work getWork() {
		return work;
	}
	public void setWork(Work work) {
		this.work = work;
	}

	@Column(name="advice",length=200)
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	
	

}