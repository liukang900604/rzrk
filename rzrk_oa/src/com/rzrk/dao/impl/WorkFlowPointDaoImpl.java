/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.WorkFlowDao;
import com.rzrk.dao.WorkFlowFormDao;
import com.rzrk.dao.WorkFlowPointDao;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.entity.WorkFlowPoint;

/**
 * Dao实现类 - 工作流节点
 */

@Repository
public class WorkFlowPointDaoImpl extends BaseDaoImpl<WorkFlowPoint, String> implements WorkFlowPointDao {


	public WorkFlowPoint get(String id,Long version){
		//先根据id获取,id未找到从历史流程中寻找
		 WorkFlowPoint point = null;
		 if(StringUtils.isEmpty(id)){
			 return point;
		 }
		 point = get(id);
		 if(point == null){
			 id += "-"+version; 
			 String hql = "from WorkFlowPoint where workFlowPointId =:workFlowPointId";
			 point = (WorkFlowPoint) getSession().createQuery(hql).setParameter("workFlowPointId", id).setMaxResults(1).uniqueResult();
		 }
		 return point;
	}
}