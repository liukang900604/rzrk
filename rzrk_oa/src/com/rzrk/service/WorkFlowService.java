/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;
import java.util.List;

import com.rzrk.entity.WorkFlow;


/**
 * 工作流流程接口 
 * @author kang.liu
 */

public interface WorkFlowService extends BaseService<WorkFlow, String> {
	
	/**
	 * 检查工作流流程名称是否唯一
	 * @return
	 */
	public List<WorkFlow> checkWorkFlowName(String flowName);
	
	/**
	 * 获取工作流流程(特殊流程除外)
	 * @return
	 */
	public List<WorkFlow> getWorkFlowList(); 
	
	/**
	 * 获取指定类型的工作流流程
	 */
	
	public List<WorkFlow> getWorkFlowListByWorkFlowTypeId(String id);
	
	public String  getMatchFlowId(String workFlowId,Long version);
	
	/**
	 * 当前流程
	 * @param currentWorkFlow
	 * @param historyWorkFlow
	 */
	public void updateWorkFlow(WorkFlow currentWorkFlow,WorkFlow historyWorkFlow);
	
	
	
  
	
}