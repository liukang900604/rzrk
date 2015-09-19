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
import com.rzrk.entity.WorkFlowPoint;


/**
 * 工作流节点接口 
 * @author kang.liu
 */

public interface WorkFlowPointService extends BaseService<WorkFlowPoint, String> {
	
	/**
	 * 检查工作流节点名称是否唯一
	 * @return
	 */
	public List<WorkFlowPoint> checkWorkFlowPointName(String pointName,String workFlowId);
	
	/**
	 * 检查工作流表单序号是否唯一
	 * @return
	 */
	public List<WorkFlowPoint> checkWorkFlowPointSort(String pointSort,String workFlowId); 
	
	/**
	 *  检查节点位置（开始、结束）
	 * @param pointLocation
	 * @param workFlowId
	 * @return
	 */
 
	public List<WorkFlowPoint> checkWorkFlowLocation(String pointLocation ,String workFlowId);
	
	/**
	 *  获取开始位置节点制定的节点
	 * @param pointLocation
	 * @param workFlowId
	 * @return
	 */
 
	public List<WorkFlowPoint> getFlowPointList(String workFlowId);
	
	/**
	 * 查出子节点
	 * @param node
	 * @param workFlowId
	 * @return
	 */
	public List<WorkFlowPoint> getPointListByNextPoint(String str,String workFlowId);
	
	/**
	 * 保存多个实体
	 */
	public void  saveManyEntity(List<WorkFlowPoint> pointList);
	/**
	 * 删除流程的子对象
	 *//*
	public void deleteSonObject(String id);*/
	
	/**
	 * 获取工作指定的工作流节点
	 * @return
	 */
	public WorkFlowPoint getWorkFlowPoint(String pointLocation,String pointSort ,String workId);
	
	/**
	 * 根据工作流程ID获取指定的节点
	 * @param pointLocation
	 * @param pointSort
	 * @param workFlowId
	 * @return
	 */
	public WorkFlowPoint getWorkFlowPointByWorkFlow(String pointLocation,String pointSort ,String workFlowId);
	
	/**
	 * 修改数据
	 */
	public void changeData();
	/**
	 * 修改单节点数据
	 */
	public void  changePointData();
	
	public WorkFlowPoint get(String id,Long version);
}