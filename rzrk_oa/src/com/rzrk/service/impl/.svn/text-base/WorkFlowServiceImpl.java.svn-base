package com.rzrk.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.WorkFlowDao;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowHistory;
import com.rzrk.service.WorkFlowHistoryService;
import com.rzrk.service.WorkFlowService;


@Transactional
@Service
public class WorkFlowServiceImpl extends BaseServiceImpl<WorkFlow, String> implements WorkFlowService {

	@Resource
	private WorkFlowDao typeDao;

	@Resource(name = "workFlowDaoImpl")
	public void setBaseDao(WorkFlowDao typeDao) {
		super.setBaseDao(typeDao);
	}
	
	/**
	 * 历史Service
	 */
	@Resource
	private WorkFlowHistoryService  historyService;
	/**
	 * 检查工作流表单名称是否唯一
	 * @return
	 */
	@Override
	public List<WorkFlow> checkWorkFlowName(String flowName) {
		String hql = "from WorkFlow where flowName =:flowName and isHistory =:isHistory";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("flowName", flowName).setParameter("isHistory", WorkFlowContants.CURRENT_FLOW).list();
	}
	
	/**
	 * 获取工作流流程
	 */
	@Override
	public List<WorkFlow> getWorkFlowList() {
		String hql = "from WorkFlow  where isDelete <>:isDelete or isDelete is null and isHistory =:isHistory ";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("isDelete", WorkFlowContants.UN_DELETE).setParameter("isHistory", WorkFlowContants.CURRENT_FLOW).list();
	}
	
	
	/**
	 * 获取指定类型的工作流流程
	 */
	
	public List<WorkFlow> getWorkFlowListByWorkFlowTypeId(String id) {
		String hql = "from WorkFlow  where (isDelete <>:isDelete or isDelete is null) and flowType.id =:typeId and isHistory =:isHistory";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("isDelete", WorkFlowContants.UN_DELETE).setParameter("typeId", id).setParameter("isHistory", WorkFlowContants.CURRENT_FLOW).list();
	}

	/**
	 * 获取符合条件的工作流
	 */
	@Override
	public String getMatchFlowId(String workFlowId, Long version) {
		try{
			String sql = "select id  from  rzrk_workflow where id = ? and version = ? ";
			List<String> resultString =  this.getBaseDao().getSession().createSQLQuery(sql).setString(0,workFlowId ).setLong(1, version).list();
			if(resultString != null && resultString.size() > 0){
				return resultString.get(0);
			}else{
				String historySql = "select id  from  rzrk_workflow where currentFlowId = ? and version = ?";
				List<String> historyResult =  this.getBaseDao().getSession().createSQLQuery(historySql).setString(0,workFlowId ).setLong(1, version).list();
				if(historyResult != null && historyResult.size() > 0){
					return historyResult.get(0);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 更新工作流程
	 */
	public void updateWorkFlow(WorkFlow currentWorkFlow,WorkFlow historyWorkFlow){
		this.getBaseDao().update(currentWorkFlow);
		this.getBaseDao().save(historyWorkFlow);
	}


}
