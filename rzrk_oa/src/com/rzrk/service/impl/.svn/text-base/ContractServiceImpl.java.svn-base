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
import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.ContractLogDao;
import com.rzrk.dao.UserPlanRequireDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.ContractLog;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.WorkFlow;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.util.GenerateUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.ContractLogItemVo;
import com.rzrk.vo.contract.ContractEntity;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;
import com.rzrk.vo.querytree.Node;

/**
 * Service实现类 - 文章分类
 */

@Service("contractServiceImpl")
public class ContractServiceImpl extends BaseServiceImpl<ContractField, String> implements ContractService {

	@Resource(name = "contractDaoImpl")
	private ContractDao contractDao;
	
	@Resource(name = "contractCategoryDaoImpl")
	private ContractCategoryDao contractCategoryDao;

	@Resource(name = "contractDaoImpl")
	public void setBaseDao(ContractDao contractDao) {
		super.setBaseDao(contractDao);
	}
	
	@Resource(name = "contractParseManager")
	ContractParseManager contractParseManager;
	
	@Resource
	ContractLogDao contractLogDao;
	
	@Resource
	UserPlanRequireDao userPlanRequireDao;
	
	/**
	 * 二级分类service
	 */
	@Resource
	private ContractCategoryService  contractCategoryService;
	
	/**
	 * 工作流流程service
	 * @param row
	 */
	@Resource
	private WorkFlowService  workFlowService;
	@Resource
	private ProjectService projectService;


	private void parseRow(ContractCategory contractCategory,Map<String, String> row) {
		Map<String, String> tmpMap = new LinkedHashMap<String, String>();
		Definition definition = contractCategory.getDefinitionObj();
		Field primaryField = definition.getPrimary();
		Assert.notNull(primaryField, "Definition 有误");
		String primaryKey = primaryField.getName();
		for(Entry<String, String> entry : row.entrySet()){
			if(StringUtils.equals(entry.getKey(),primaryKey)){
				String primaryValue = entry.getValue();
				if(StringUtils.isBlank(primaryValue)){
					primaryValue = GenerateUtil.createPrimary(contractCategory.getId());
					entry.setValue(primaryValue);
				}
			}else if(StringUtils.isNotEmpty(entry.getValue())){
				String fieldKey = entry.getKey();
				Field field = definition.getField(fieldKey);
				if(field!=null && field.isBuilt()){
					ContractParser contractParser = contractParseManager.getContractParser(field.getBuiltsign());
					if(contractParser!=null){
						String value = contractParser.parse(entry.getValue());
						if(StringUtils.isEmpty(value)){
							throw new PersonalException(fieldKey+"解析失败,不存在该"+entry.getValue());
						}
						tmpMap.put("__id__"+fieldKey, value);
					}
				}
			}
		}
		row.putAll(tmpMap);
	}
	@Deprecated //不用再格式化
	private void formatRow(Map<String, String> row){
//		Map<String, ContractParser> contractParserMap = contractParseManager.getContractParserList();
//		for(Entry<String, String> entity : row.entrySet()){
//			ContractParser contractParser = contractParserMap.get(entity.getKey());
//			if(contractParser!=null){
//				String value = contractParser.format(entity.getValue());
//				entity.setValue(value);
//			}
//		}
	}
	@Deprecated
	public LinkedHashMap<String, HashMap<String, String>> getContractRows(String ccid,String[] titleArr){
		if(titleArr==null){
			ContractCategory contractCategory = contractCategoryDao.get(ccid);
//			String fields = contractCategory.getFields();
			titleArr= contractCategory.getDefinitionObj().getTitles();
		}
		List<ContractField> contractFields = contractDao.getContractFields(ccid, titleArr);
		LinkedHashMap<String, HashMap<String, String>> dataMap = new LinkedHashMap<String, HashMap<String,String>>();
		for(ContractField field : contractFields){
			if (field.isIndication()){
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("rowId", field.getId());
				dataMap.put(field.getId(), item);
			}
		}
		for(ContractField field : contractFields){
			HashMap<String, String> item =  dataMap.get(field.getRowId());
			if (item!=null){
				item.put(field.getFieldName(), field.getValue());
			}
		}
//		for(Map<String, String> item : dataMap.values()){
//			formatRow(item);
//		}
		
		return dataMap;
	}
	
	public ContractEntity<String, String> getContractRow(String rowId,String ccid,String[] titleArr){
		if(titleArr==null){
			ContractCategory contractCategory = contractCategoryDao.get(ccid);
//			String fields = contractCategory.getFields();
			titleArr= contractCategory.getDefinitionObj().getTitles();
		}
		
		List<ContractField> contractFields = contractDao.getContractField(rowId, titleArr);
		ContractEntity<String, String> item = new ContractEntity<String, String>();
		for(ContractField field : contractFields){
			item.put(field.getFieldName(), field.getValue());
		}
		item.setProjectId(contractFields.get(0).getProjectId());
//		formatRow(item);
		return item;
	}
	
	/**
	 * 查询rowId 的字段
	 * @param rowId
	 * @return
	 */
	public List<ContractField> getContractFieldList(String rowId){
		String hql = "from ContractField where rowId =:rowId";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("rowId", rowId).list();
	}

	private boolean checkNullRow(HSSFRow row,int len){
		for(int i=0;i<len;i++){
			HSSFCell _cell = row.getCell(i);
			String _cellStr = com.rzrk.util.StringUtils.parseExcel(_cell);
			if(!StringUtils.isEmpty(_cellStr)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 主键重复
	 * 关键字段解析失败
	 * 
	 * @param hssfWorkbook
	 * @throws Exception 
	 */
	
	
	public String uploadContract(String contractCategoryId,HSSFWorkbook hssfWorkbook,Admin loginAdmin){
		ContractCategory contractCategory  = contractCategoryDao.get(contractCategoryId);
		StringBuffer buffer = new StringBuffer();
		List<ContractEntity<String, String>> resultList = new ArrayList<ContractEntity<String, String>>();//字段显示集合
		int keyindex=-1;
		CheckInfo parseInfo = new CheckInfo();
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
all:	do{
			if(hssfSheet==null){
				parseInfo.addError("sheet 为空");
				break all;
			}
			HSSFRow titleRow = hssfSheet.getRow(0);
			if(titleRow==null){
				parseInfo.addError("标题栏 为空");
				break all;
			}
			ArrayList<String> titleList = new ArrayList<String>();
			for(int i = 0;; i++){
				HSSFCell cell = titleRow.getCell(i);
				String title = com.rzrk.util.StringUtils.parseExcel(cell);
				if(StringUtils.isEmpty(title)){
					break;
				}else{
					titleList.add(title);
				}
			}
			if(titleList.size()==0){
				parseInfo.addError("标题栏 为空");
				break all;
			}
			int coloumNum = titleList.size();
			String majorField = contractCategoryDao.getMajorField(contractCategoryId);
			if(StringUtils.isEmpty(majorField)){
				parseInfo.addError("没有找到该模板配置");
				break all;
			}
			
			for(int i=0;i<titleList.size();i++){
				String title = titleList.get(i);
				if(StringUtils.equals(title, majorField)){
					keyindex=i;
					break;
				}
			}
			if(keyindex == -1){
				parseInfo.addError("主键和模板配置不一致");
				break all;
			}
			int rowLastNum = hssfSheet.getLastRowNum();
			if(!parseInfo.isError()){
				ArrayList<String> tmpErrorList = new ArrayList<String>();
				boolean tailnull = true; //持续尾巴空行
				HashSet<String> indentifySet = new HashSet<String>();
				for(int j=rowLastNum;j>=1;j--){
					HSSFRow valueRow = hssfSheet.getRow(j);
					HSSFCell identifyCell = valueRow.getCell(keyindex);
					String identifyStr = com.rzrk.util.StringUtils.parseExcel(identifyCell);
					if(StringUtils.isEmpty(identifyStr)){
						if(tailnull){
							if(checkNullRow(valueRow,titleList.size())){
								//TODO 发现空行
								rowLastNum--;
							}else{
								tailnull=false;
								tmpErrorList.add("第"+(j+1)+"行  主键不能为空 ");
							}
						}else{
							tmpErrorList.add("第"+(j+1)+"行  主键不能为空 ");
						}
						continue;
					}else{
						tailnull=false;
					}
					if(indentifySet.contains(identifyStr)){
						tmpErrorList.add("第"+(j+1)+"行 "+identifyStr+" 重复");
					}else{
						indentifySet.add(identifyStr);
					}
				}
				for(int y = tmpErrorList.size()-1;y>=0;y--){
					parseInfo.addError(tmpErrorList.get(y));
				}
			}
			
			if(!parseInfo.isError()){
				for(int j=1;j<=rowLastNum;j++){
					ContractEntity<String, String> entity = new ContractEntity<String, String>();
					HSSFRow valueRow = hssfSheet.getRow(j);
					HSSFCell identifyCell = valueRow.getCell(keyindex);
					String identifyStr = com.rzrk.util.StringUtils.parseExcel(identifyCell);
					entity.put(titleList.get(keyindex), identifyStr);
					
					
					for(int i=0;i<coloumNum;i++){
						if(i==keyindex)continue;
						HSSFCell valueCell = valueRow.getCell(i);
						String valueStr = com.rzrk.util.StringUtils.parseExcel(valueCell);
						entity.put(titleList.get(i), valueStr);
					}
					try {
						parseRow(contractCategory,entity);
					} catch (Exception e) {
						parseInfo.addError("第"+(j+1)+"行 :"+e.getMessage());
					}
					if(!parseInfo.isError()){
						resultList.add(entity);
						String ids = contractDao.saveOrUpdateExt(contractCategoryId, entity,loginAdmin);
						buffer.append(ids);//分类字段ID
						entity.setRowId(ids.split(",")[0]);
						entity.setContractCategoryId(contractCategoryId);
					}
				}
			}

		}while(false);
		
		if(parseInfo.isError()){
			throw new PersonalException(parseInfo.errorMessage());
		}
		ContractCategory category = null;
		category = contractCategoryService.get(contractCategoryId);
		if(category != null){
			//检测流程完整性
			if(WorkFlowContants.CHECK.intValue() == category.getApprovalNeeded()){//需要审核
				if(StringUtils.isNotEmpty(category.getWorkFlowId())){
					WorkFlow tempFlow = workFlowService.get(category.getWorkFlowId());
					if(tempFlow != null){
						if(!(tempFlow.getWorkFlowPointSet() != null && tempFlow.getWorkFlowPointSet().size() > 0)){
							throw new PersonalException("所属二级分类审批流程节点未定义!");
						}
					}else{
						throw new PersonalException("所属二级分类流程不存在 ,请选择其他流程!");
					}
					
				}else{
					throw new PersonalException("所属二级分类审批流程未创建!");
				}
				//创建工作
				contractDao.createWork(buffer.toString(),loginAdmin,category,WorkFlowContants.UN_DELETE,resultList,WorkFlowContants.SAVE_MESSAGE);
			}
		}
		
		return buffer.toString();

	}

	
//	@Override
//	public void uploadContractCategoryAndData(String contractCategoryName, HSSFWorkbook hssfWorkbook,Admin loginAdmin) throws Exception {
//		String contractCategoryId;
//		int keyindex=0;
//		CheckInfo parseInfo = new CheckInfo();
//		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
//all:	do{
//			if(hssfSheet==null){
//				parseInfo.addError("sheet 为空");
//				break all;
//			}
//			HSSFRow titleRow = hssfSheet.getRow(0);
//			if(titleRow==null){
//				parseInfo.addError("标题栏 为空");
//				break all;
//			}
//			ArrayList<String> titleList = new ArrayList<String>();
//			for(int i = 0;; i++){
//				HSSFCell cell = titleRow.getCell(i);
//				String title = com.rzrk.util.StringUtils.parseExcel(cell);
//				if(StringUtils.isEmpty(title)){
//					break;
//				}else{
//					titleList.add(title);
//				}
//			}
//			if(titleList.size()==0){
//				parseInfo.addError("标题栏 为空");
//				break all;
//			}
//			int coloumNum = titleList.size();
//			ContractCategory _contractCategory = contractCategoryDao.find("name", contractCategoryName);
//			if(_contractCategory!=null){
//				parseInfo.addError("类型"+contractCategoryName+"已存在，请更换excel文件名");
//				break all;
//			}else{
//				ContractCategory contractCategory = new ContractCategory();
//				contractCategory.setName(contractCategoryName);
//				contractCategory.setFields(StringUtils.join(titleList,","));
//				contractCategoryDao.save(contractCategory);
//				contractCategoryId = contractCategory.getId();
//			}
//			
//			int rowLastNum = hssfSheet.getLastRowNum();
//			if(!parseInfo.isError()){
//				ArrayList<String> tmpErrorList = new ArrayList<String>();
//				boolean tailnull = true; //持续尾巴空行
//				HashSet<String> indentifySet = new HashSet<String>();
//				for(int j=rowLastNum;j>=1;j--){
//					HSSFRow valueRow = hssfSheet.getRow(j);
//					HSSFCell identifyCell = valueRow.getCell(keyindex);
//					String identifyStr = com.rzrk.util.StringUtils.parseExcel(identifyCell);
//					if(StringUtils.isEmpty(identifyStr)){
//						if(tailnull){
//							if(checkNullRow(valueRow,titleList.size())){
//								//TODO 发现空行
//								rowLastNum--;
//							}else{
//								tailnull=false;
//								tmpErrorList.add("第"+(j+1)+"行  主键不能为空 ");
//							}
//						}else{
//							tmpErrorList.add("第"+(j+1)+"行  主键不能为空 ");
//						}
//						continue;
//					}else{
//						tailnull=false;
//					}
//					if(indentifySet.contains(identifyStr)){
//						tmpErrorList.add("第"+(j+1)+"行 "+identifyStr+" 重复");
//					}else{
//						indentifySet.add(identifyStr);
//					}
//				}
//				for(int y = tmpErrorList.size()-1;y>=0;y--){
//					parseInfo.addError(tmpErrorList.get(y));
//				}
//			}
//			
//			if(!parseInfo.isError()){
//				for(int j=1;j<=rowLastNum;j++){
//					LinkedHashMap<String, String> entity = new LinkedHashMap<String, String>();
//					HSSFRow valueRow = hssfSheet.getRow(j);
//					HSSFCell identifyCell = valueRow.getCell(keyindex);
//					String identifyStr = com.rzrk.util.StringUtils.parseExcel(identifyCell);
//					entity.put(titleList.get(keyindex), identifyStr);
//					
//					
//					for(int i=0;i<coloumNum;i++){
//						if(i==keyindex)continue;
//						HSSFCell valueCell = valueRow.getCell(i);
//						String valueStr = com.rzrk.util.StringUtils.parseExcel(valueCell);
//						entity.put(titleList.get(i), valueStr);
//					}
//					try {
//						parseRow(entity);
//					} catch (Exception e) {
//						parseInfo.addError("第"+(j+1)+"行 :"+e.getMessage());
//					}
//					if(!parseInfo.isError()){
//						contractDao.saveOrUpdateExt(contractCategoryId, entity,loginAdmin);
//					}
//				}
//			}
//
//		}while(false);
//		
//		if(parseInfo.isError()){
//			throw new Exception(parseInfo.errorMessage());
//		}
//
//	}
	public List<String> selectForRowName(String contractCategoryId, String[] rowIds){
		return contractDao.selectForRowName(contractCategoryId, rowIds);
	}

	@Override
	public void deleteForRowIds(String contractCategoryId, String[] rowIds) {
			contractDao.deleteForRowIds(contractCategoryId, rowIds);
	}
	
	/**
	 * 获取需要删除的id
	 * @param contractCategoryId
	 * @param indicates
	 * @throws Exception 
	 */
	public String getFieldList(String contractCategoryId, String[] indicates,Admin loginAdmin) {
		
		StringBuffer buffer = new StringBuffer();
		List<ContractEntity<String, String>> resultList = new ArrayList<ContractEntity<String, String>>();//字段显示集合
		for(String indicate : indicates){
			Map<String,Object> resultMap = contractDao.getFieldList(contractCategoryId, indicate);
			buffer.append(resultMap.get("fieldId"));
			resultList.add((ContractEntity<String, String>) resultMap.get("entity"));
		}
		
		ContractCategory category = null;
		category = contractCategoryService.get(contractCategoryId);
		if(category != null){
			//检测流程完整性
			if(WorkFlowContants.CHECK.intValue() == category.getApprovalNeeded()){//需要审核
				if(StringUtils.isNotEmpty(category.getWorkFlowId())){
					WorkFlow tempFlow = workFlowService.get(category.getWorkFlowId());
					if(tempFlow != null){
						if(!(tempFlow.getWorkFlowPointSet() != null && tempFlow.getWorkFlowPointSet().size() > 0)){
							throw new PersonalException("所属二级分类审批流程节点未定义!");
						}
					}else{
						throw new PersonalException("所属二级分类流程不存在 ,请选择其他流程!");
					}
					
				}else{
					throw new PersonalException("所属二级分类审批流程未创建!");
				}
				//创建工作
				contractDao.createWork(buffer.toString(),loginAdmin,category,WorkFlowContants.DELETE,resultList,WorkFlowContants.DELETE_MESSAGE);
			}
		}
		return buffer.toString();
	}
	@Deprecated
	public HSSFWorkbook getDownload(String contractCategoryId,String[] titleArr){
		ContractCategory contractCategory = contractCategoryDao.get(contractCategoryId);
		if(titleArr==null){
//			String fields = contractCategory.getFields();
			titleArr= contractCategory.getDefinitionObj().getTitles();
		}

		LinkedHashMap<String, HashMap<String, String>> datamap = getContractRows(contractCategoryId,titleArr);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFFont titleFont = wb.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);
		titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);// 设置背景色  
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFSheet sheet = wb.createSheet(contractCategory.getName());  
        HSSFRow row = sheet.createRow(0);
        int i=0;
    	for(String fieldName : titleArr){
            HSSFCell cell = row.createCell((short) i++);  
            cell.setCellValue(fieldName);
            cell.setCellStyle(titleStyle);
    	}
    	int j=1;
    	for(HashMap<String, String> entity : datamap.values()){
            row = sheet.createRow(j++);
            for(int m=0; m<titleArr.length;m++ ){
            	String value = entity.get(titleArr[m]);
                HSSFCell cell = row.createCell((short)m);  
                cell.setCellValue(value);
            }
    	}
		return wb;
	
	}

	
	public String save(String parentRowId,String tempRowId,String contractCategoryId,ContractEntity<String, String> entity,Admin adminList ) {
		ContractCategory contractCategory  = contractCategoryDao.get(contractCategoryId);
		parseRow(contractCategory,entity);
		return contractDao.save(parentRowId,tempRowId,contractCategoryId,entity,adminList);
	}

//	public String save4work(String contractCategoryId,LinkedHashMap<String, String> entity) throws Exception{
//		parseRow(entity);
//		return contractDao.save4work(contractCategoryId,entity);
//	}

	public String update(String parentRowId,String rowId,String contractCategoryId,ContractEntity<String, String> entity,Admin adminList) {
		ContractCategory contractCategory  = contractCategoryDao.get(contractCategoryId);
		parseRow(contractCategory,entity);
		ContractEntity<String, String> oldEntity = getContractRow(rowId, contractCategoryId, null);
		List<ContractLogItemVo> logitemList = new ArrayList<ContractLogItemVo>();
		if(oldEntity!=null){
			for(Entry<String, String> item : entity.entrySet()){
				String fieldName = item.getKey();
				String newValue = item.getValue();
				String oldValue = oldEntity.get(fieldName);
				if(StringUtils.startsWith(fieldName, "__") || StringUtils.equals(newValue, oldValue) || (StringUtils.isEmpty(newValue) && StringUtils.isEmpty(oldValue))){
					//内置字段，相同字段，同是null或“”的无视
				}else{
					ContractLogItemVo logitem = new ContractLogItemVo();
					logitem.setFieldName(fieldName);
					logitem.setNewValue(StringUtils.defaultIfEmpty(newValue,""));
					logitem.setOldValue(StringUtils.defaultIfEmpty(oldValue,""));
					logitemList.add(logitem);
				}
			}
			if(StringUtils.equals(contractCategoryId, CategoryContants.REQUIREMENT_CATEGORY_ID)){
				String oldProjectId = StringUtils.defaultIfBlank(oldEntity.getProjectId(), "");
				String newProjectId = StringUtils.defaultIfBlank(entity.getProjectId(), "");
				if(!StringUtils.equals(oldProjectId, newProjectId)){
					ContractLogItemVo logitem = new ContractLogItemVo();
					logitem.setFieldName("所属项目");
					try {
						logitem.setNewValue(StringUtils.isEmpty(newProjectId)?"":(StringUtils.defaultIfEmpty(projectService.get(newProjectId).getName(),"")));
					} catch (Exception e) {
					}
					try {
						logitem.setOldValue(StringUtils.isEmpty(oldProjectId)?"":(StringUtils.defaultIfEmpty(projectService.get(oldProjectId).getName(),"")));
					} catch (Exception e) {
					}
					logitemList.add(logitem);
				}
				
			}
			
			if(!logitemList.isEmpty()){
				ContractLog log = new ContractLog();
				log.setContractLogItemList(logitemList);
				log.setOperator(adminList);
				log.setContractCategory(contractCategoryDao.get(contractCategoryId));
				log.setRowId(rowId);
				contractLogDao.save(log);
			}
		}
		if(StringUtils.equals(contractCategoryId, CategoryContants.REQUIREMENT_CATEGORY_ID)){
			String projectId = entity.getProjectId();
			this.joinProject(rowId, projectId,adminList);
		}

		return contractDao.update(parentRowId,rowId,contractCategoryId,entity,adminList);
	}
	public Pager findPager(Pager pager, String contractCategoryId,Node node){
		return findPager(pager,contractCategoryId,node,new HashMap<String, Object>());
	}
	public Pager findPager(Pager pager, String contractCategoryId,Node node ,Map<String,Object> rowCondMap){
		
		Map<String,Object> dataMap=contractDao.findPager(contractCategoryId, pager,node,rowCondMap);
		List<Map<String,String>> datalist = (List<Map<String,String>>) dataMap.get("result");
		int total = (Integer) dataMap.get("total");
		pager.setResult(datalist);
		pager.setTotalCount(total);
		
		
		return pager;
	}

	 /**
     * 更新项目关联
     * @param fieldString
     */
	@Deprecated
	public int  updateProjectRelation(String fieldString){
    	
    	return contractDao.updateProjectRelation(fieldString);
    	
    }
    
	public List<Field> getPermissionField(ContractCategory  contractCategory, Admin admin){
		return contractCategoryDao.getPermissionField(contractCategory, admin);
	}
	
	public List<ContractField> getFieldListByColumn(String contractCategoryId,String fieldName){
		return contractDao.getFieldListByColumn(contractCategoryId, fieldName);
	};
	public void joinProject(String rowId,String projectId,Admin admin){
		DetachedCriteria detachedCriteriaIndicate = DetachedCriteria.forClass(ContractField.class);
		detachedCriteriaIndicate.add(Restrictions.eq("id", rowId));
		ContractField contractFieldIndicate = contractDao.unique(detachedCriteriaIndicate);
		Assert.notNull(contractFieldIndicate, "没有该记录");
		String oldProjectId = StringUtils.defaultIfBlank(contractFieldIndicate.getProjectId(), "");
		String newProjectId = StringUtils.defaultIfBlank(projectId, "");
		if(!StringUtils.equals(oldProjectId, newProjectId)){
			ContractLog log = new ContractLog();
			List<ContractLogItemVo> logitemList = new ArrayList<ContractLogItemVo>();
			ContractLogItemVo logitem = new ContractLogItemVo();
			logitem.setFieldName("所属项目");
			try {
				logitem.setNewValue(StringUtils.isEmpty(newProjectId)?"":(StringUtils.defaultIfEmpty(projectService.get(newProjectId).getName(),"")));
			} catch (Exception e) {
			}
			try {
				logitem.setOldValue(StringUtils.isEmpty(oldProjectId)?"":(StringUtils.defaultIfEmpty(projectService.get(oldProjectId).getName(),"")));
			} catch (Exception e) {
			}
			logitemList.add(logitem);
			log.setContractLogItemList(logitemList);
			log.setOperator(admin);
			log.setContractCategory(contractCategoryDao.get(contractFieldIndicate.getContractCategoryId()));
			log.setRowId(rowId);
			contractLogDao.save(log);
		}
		
		
		contractFieldIndicate.setProjectId(projectId);
		userPlanRequireDao.removeByRequire(rowId);
	}

	public List<Map> getUserPlanRelation(String userPlanId){
		return contractDao.getUserPlanRelation(userPlanId);
	};
	public List<Map> getProjectRelation(String projectId){
		return contractDao.getProjectRelation(projectId);
	}

}