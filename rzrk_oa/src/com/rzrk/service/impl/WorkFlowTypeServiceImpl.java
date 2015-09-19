package com.rzrk.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.WorkFlowTypeDao;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowType;
import com.rzrk.service.WorkFlowService;
import com.rzrk.service.WorkFlowTypeService;

@Transactional
@Service
public class WorkFlowTypeServiceImpl extends BaseServiceImpl<WorkFlowType, String> implements WorkFlowTypeService {

	@Resource
	private WorkFlowTypeDao typeDao;

	@Resource(name = "workFlowTypeDaoImpl")
	public void setBaseDao(WorkFlowTypeDao typeDao) {
		super.setBaseDao(typeDao);
	}
	
	/**
	 * 工作流流程接口
	 */
	@Resource
	private WorkFlowService  workFlowService;
	/**
	 * 检查工作流类型名称是否唯一
	 * @return
	 */
	@Override
	public List<WorkFlowType> checkWorkFlowTypeName(String typeName) {
		String hql = "from WorkFlowType where typeName =:typeName";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("typeName", typeName).list();
		
	}
	
	/**
	 * 获取工作流类型集合
	 */
	@Override
	public List<WorkFlowType> getWorkFlowTypeList() {
		String hql = "from WorkFlowType where isDelete <>:isDelete or isDelete is null";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("isDelete", WorkFlowContants.UN_DELETE).list();
	}
	
	/**
	 * 获取流程树
	 */
	public ArrayList<?> getListWithChild(){
		List<WorkFlowType> typeList =  this.getAllList();
		ArrayList<Map<?,?>> rootList = new ArrayList<Map<?,?>>();
		for(WorkFlowType type : typeList){
			 Map<String,Object> mapRoot = new HashMap<String,Object>(); 
			 mapRoot.put("id", type.getId()); 
		     mapRoot.put("name", type.getTypeName());
		     mapRoot.put("text", type.getTypeName());
		     ArrayList<Map<?,?>> childList = new ArrayList<Map<?,?>>();
		    List<WorkFlow> workFlowList =  workFlowService.getWorkFlowListByWorkFlowTypeId(type.getId());
		     if(workFlowList != null && workFlowList.size() > 0){
		         for(WorkFlow workFlow : workFlowList ){
		        	 Map<String,Object> childRoot = new HashMap<String,Object>();
		        	 childRoot.put("id", workFlow.getId()); 
		        	 childRoot.put("name", workFlow.getFlowName());
		        	 childRoot.put("text", workFlow.getFlowName());
		        	 childList.add(childRoot);
		         }
		     }
		     	mapRoot.put("children", childList);
		        rootList.add(mapRoot);
		}
		return rootList;
		
	}
	


}
