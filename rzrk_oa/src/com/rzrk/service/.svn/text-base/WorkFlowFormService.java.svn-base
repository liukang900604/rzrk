/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;
import java.util.List;

import com.rzrk.entity.WorkFlowForm;


/**
 * 工作流表单接口 
 * @author kang.liu
 */

public interface WorkFlowFormService extends BaseService<WorkFlowForm, String> {
	
	/**
	 * 检查工作流表单名称是否唯一
	 * @return
	 */
	public List<WorkFlowForm> checkWorkFlowFormName(String typeName);
	
	
    /**
     * 获取所有工作流表单
     */
	public List<WorkFlowForm> getWorkFlowFormList();
	
	public List<WorkFlowForm> getByContractCategoryId(String contractCategoryId);

	
}