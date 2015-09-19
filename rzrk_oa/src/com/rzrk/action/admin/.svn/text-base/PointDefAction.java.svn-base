/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.WorkFlow;
import com.rzrk.service.WorkFlowService;




/**
 * 工作流-节点定义
 * @author kang.liu
 */

@ParentPackage("admin")
public class PointDefAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	private String id; //接收主键
	private WorkFlow workFlow;//工作流程对象
    
	@Resource
	private WorkFlowService  workFlowService;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String edit(){
		workFlow = workFlowService.get(id);
		if(workFlow != null){
			workFlow = new WorkFlow();
		}
		return INPUT;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}
	
	

}