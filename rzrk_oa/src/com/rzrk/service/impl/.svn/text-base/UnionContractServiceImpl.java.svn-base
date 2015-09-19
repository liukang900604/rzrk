/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rzrk.bean.Pager;
import com.rzrk.common.CheckInfo;
import com.rzrk.common.contract.ContractParseException;
import com.rzrk.common.contract.ContractParseManager;
import com.rzrk.common.contract.ContractParser;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.BaseDao;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.ContractLogDao;
import com.rzrk.dao.UnionContractCategoryDao;
import com.rzrk.dao.UnionContractDao;
import com.rzrk.dao.UserPlanRequireDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.ContractLog;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.UnionContractCategory;
import com.rzrk.entity.WorkFlow;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.UnionContractService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.util.GenerateUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.ContractLogItemVo;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.unioncontract.Field;

/**
 * Service实现类 - 文章分类
 */

@Service("unionContractServiceImpl")
public class UnionContractServiceImpl extends BaseServiceImpl<ContractField, String> implements UnionContractService {
	@Resource(name = "unionContractDaoImpl")
	UnionContractDao unionContractDao;
	
	@Resource(name = "unionContractDaoImpl")
	public void setBaseDao(UnionContractDao unionContractDao) {
		super.setBaseDao(unionContractDao);
	}

	public Pager findPager(Pager pager, String contractCategoryId,Node node){
		return findPager(pager,contractCategoryId,node,new HashMap<String, Object>());
	}
	public Pager findPager(Pager pager, String contractCategoryId,Node node ,Map<String,Object> rowCondMap){
		
		Map<String,Object> dataMap=unionContractDao.findPager(contractCategoryId, pager,node,rowCondMap);
		List<Map<String,String>> datalist = (List<Map<String,String>>) dataMap.get("result");
		int total = (Integer) dataMap.get("total");
		pager.setResult(datalist);
		pager.setTotalCount(total);
		
		
		return pager;
	}


}