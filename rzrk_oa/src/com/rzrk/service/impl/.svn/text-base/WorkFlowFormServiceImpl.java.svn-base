package com.rzrk.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.WorkFlowFormDao;
import com.rzrk.dao.WorkFlowTypeDao;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.entity.WorkFlowType;
import com.rzrk.service.WorkFlowFormService;
import com.rzrk.service.WorkFlowTypeService;

@Transactional
@Service
public class WorkFlowFormServiceImpl extends BaseServiceImpl<WorkFlowForm, String> implements WorkFlowFormService {

	@Resource
	private WorkFlowFormDao typeDao;

	@Resource(name = "workFlowFormDaoImpl")
	public void setBaseDao(WorkFlowFormDao typeDao) {
		super.setBaseDao(typeDao);
	}
	/**
	 * 检查工作流表单名称是否唯一
	 * @return
	 */
	@Override
	public List<WorkFlowForm> checkWorkFlowFormName(String formName) {
		String hql = "from WorkFlowForm where formName =:formName";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("formName", formName).list();
		
	}
	
	/**
	 * 获取工作流表单集合
	 */
	@Override
	public List<WorkFlowForm> getWorkFlowFormList() {
		String hql = "from WorkFlowForm  where isDelete <>:isDelete or isDelete is null";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("isDelete", WorkFlowContants.UN_DELETE).list();
	}

	@Override
	public List<WorkFlowForm> getByContractCategoryId(String contractCategoryId) {
		String hql = "from WorkFlowForm  where contractCategoryId = :contractCategoryId ";
		return this.getBaseDao().getSession().createQuery(hql).setString("contractCategoryId", contractCategoryId).list();
	}


}
