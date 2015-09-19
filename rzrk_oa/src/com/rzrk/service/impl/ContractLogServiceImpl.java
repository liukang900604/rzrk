package com.rzrk.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractLogDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractLog;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractLogService;
import com.rzrk.vo.ContractLogItemVo;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;

@Service("contractLogServiceImpl")
public class ContractLogServiceImpl  extends BaseServiceImpl<ContractLog, String> implements ContractLogService {
	
	
	@Resource(name = "contractLogDaoImpl")
	private ContractLogDao contractLogDao;
	@Resource
	private ContractCategoryDao contractCategoryDao;

	@Resource
	private ContractCategoryService contractCategoryService;

	@Resource(name = "contractLogDaoImpl")
	public void setBaseDao(ContractLogDao articleDao) {
		super.setBaseDao(articleDao);
	}
	

	public List<ContractLog> queryContractLog(String contractCategoryId,String rowId,Admin admin){
		List<ContractLog> filterList = new ArrayList<ContractLog>();
		ContractCategory contractCategory = contractCategoryDao.get(contractCategoryId);
		List<Field> fieldList = contractCategoryService.getPermissionField(contractCategory, admin);
		Definition definition = new Definition(fieldList);
		List<ContractLog> logList =  contractLogDao.queryContractLog(contractCategoryId, rowId);
		for(ContractLog log : logList){
			List<ContractLogItemVo> tempContractLogItemVoList = new ArrayList<ContractLogItemVo>();
			List<ContractLogItemVo> contractLogItemVoList = log.getContractLogItemList();
			for(ContractLogItemVo item : contractLogItemVoList){
				if(definition.getField(item.getFieldName())!=null || StringUtils.equals(item.getFieldName(), "所属项目")){
					tempContractLogItemVoList.add(item);
				}
				
			};
			if(!tempContractLogItemVoList.isEmpty()){
				ContractLog filterLog = new ContractLog();
				try {
					PropertyUtils.copyProperties(filterLog, log);
					filterLog.setContractLogItemList(tempContractLogItemVoList);
					filterList.add(filterLog);
				} catch (Exception e) {
					throw new PersistenceException("二级分类日志内容有误："+e.getMessage());
				}
			}
		}
		return filterList;
	}

}
