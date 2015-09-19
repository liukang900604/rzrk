package com.rzrk.entity;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 实体类 - 工作流类型
 * @author kang.liu
 */


@Entity
@Table(name="rzrk_workflow_type")
public class WorkFlowType extends BaseEntity{

	private static final long serialVersionUID = -7519486823153844426L;
	
	
	private String typeName;// 类型名称
    private Set<WorkFlow> workFlowSet = new HashSet<WorkFlow>();//工作流
    private String  isDelete;//是否可以删除    特殊工作流使用 1:可以  2：不可以

	/**
	 * 无参构造方法
	 */
	public WorkFlowType() {
	}


	@Column(name = "typeName",length =50)
	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

   @OneToMany(mappedBy="flowType")
	public Set<WorkFlow> getWorkFlowSet() {
		return workFlowSet;
	}


	public void setWorkFlowSet(Set<WorkFlow> workFlowSet) {
		this.workFlowSet = workFlowSet;
	}

   @Column(name="isDelete",length=2)
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	
	
	

   
	
	
}