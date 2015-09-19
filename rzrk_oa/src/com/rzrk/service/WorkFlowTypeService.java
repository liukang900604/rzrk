/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;
import java.util.ArrayList;
import java.util.List;

import com.rzrk.entity.WorkFlowType;


/**
 * 工作流类型接口 
 * @author kang.liu
 */

public interface WorkFlowTypeService extends BaseService<WorkFlowType, String> {
	
	/**
	 * 检查工作流类型名称是否唯一
	 * @return
	 */
	public List<WorkFlowType> checkWorkFlowTypeName(String typeName);
	
	/**
	 * 获取工作流类型集合
	 */
	public List<WorkFlowType> getWorkFlowTypeList();
	
	
	/**
	 * 获取流程树
	 */
	public ArrayList<?> getListWithChild();

}