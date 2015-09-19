/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;













































import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.common.Returns.Return2;
import com.rzrk.common.Returns.Return3;
import com.rzrk.common.contract.ContractParseManager;
import com.rzrk.common.contract.ContractParser;
import com.rzrk.dao.ArticleCategoryDao;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.WorkFlowFormDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Product;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.ArticleCategoryService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.WorkFlowFormService;
import com.rzrk.util.FreemarkerUtils;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;

import freemarker.template.TemplateException;

/**
 * Service实现类 - 文章分类
 */

@Service("contractCategoryServiceImpl")
public class ContractCategoryServiceImpl extends BaseServiceImpl<ContractCategory, String> implements ContractCategoryService {

	@Resource(name = "contractCategoryDaoImpl")
	private ContractCategoryDao contractCategoryDao;
	
	@Resource(name = "contractDaoImpl")
	private ContractDao contractDao;
	
	@Resource(name = "contractCategoryDaoImpl")
	public void setBaseDao(ContractCategoryDao contractCategoryDao) {
		super.setBaseDao(contractCategoryDao);
	}
	
	@Resource(name = "contractParseManager")
	ContractParseManager contractParseManager;
	@Resource
	WorkFlowFormService workFlowFormService;

	public String getContractFieldParserNames(){
		Map<String, ContractParser> map = contractParseManager.getContractParserList();
		return StringUtils.join(map.keySet(),",");
	}


	@Override
	public HSSFWorkbook getDownloadTemp(String id,Admin admin) {
		ContractCategory contractCategory = contractCategoryDao.load(id);
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
 //       String fields = contractCategory.getFields();
        if(CollectionUtils.isNotEmpty(contractCategory.getDefinitionObj())){
    		String[] fields = com.rzrk.util.StringUtils.fromCollection(
    				CollectionUtils.collect(contractCategoryDao.getPermissionField(contractCategory,admin),new Transformer() {
    					@Override
    					public Object transform(Object arg0) {
    						return ((Field)arg0).getName();
    					}
    		}));
        	int i=0;
        	for(String fieldName : fields){
                HSSFCell cell = row.createCell((short) i++);  
                cell.setCellValue(fieldName);
                cell.setCellStyle(titleStyle);
        	}
        }
		return wb;
	}
	
	public void deleteFromId(String id){
		ContractCategory persistent = contractCategoryDao.get(id);
//		persistent.setRecyle(true);
//		contractCategoryDao.update(persistent);
		if(persistent!=null){
			contractDao.deleteFromContractCategoryId(id);
			contractCategoryDao.delete(persistent);
		}
	}


	@Override
	public void saveWithCheck(ContractCategory contractCategory){
		ContractCategory _contractCategory = contractCategoryDao.find("name", contractCategory.getName());
		if(_contractCategory!=null){
			if(_contractCategory.isRecyle()){
				throw new PersonalException(contractCategory.getName()+" 已被删除，请先在回收站清空");
			}else{
				throw new PersonalException(contractCategory.getName()+"已存在");
			}
		}else{
			contractCategoryDao.save(contractCategory);
			for(ContractCategory  child: contractCategory.getChildSet()){
				contractCategoryDao.get(child.getId()).setParent(contractCategory);
			}
		}
	}


	@Override
	public boolean isExistByCategoryname(String name) {
		return contractCategoryDao.isExistByCategoryname(name);
	}
	
	/**
	 * 根据名称获取二级分类
	 * @param name
	 * @return
	 */
	public ContractCategory getContractCategoryByCategoryname(String name){
		return contractCategoryDao.getContractCategoryByCategoryname(name);
	}

	/**
	 * 判断登录用户是否可以修改记录
	 * @param contractCategeoryId
	 * @return
	 */
	@Override
	public Boolean canUpdateContractRecord(String contractCategoryId, Admin admin) {
		
		if(admin.getAllAdminContractCategory().contains(contractCategoryId)){
			return true;
		}
		
		return false;
	}


//	@Override
//	public boolean deleteWithCheck(String  contractCategoryId) {
//		return contractCategoryDao.deleteWithCheck(contractCategoryId);
//	}
	
	
	public List<String> getExpressionScriptList(final String id) {
		ContractCategory  contractCategory = contractCategoryDao.get(id);
		Definition expDef = contractCategory.getDefinitionObj();
		List<String> expressionScriptList = new ArrayList<String>();
		for(Field field : expDef){
			if(!field.isBuilt() && StringUtils.equals(field.getType(),"表达式")&& StringUtils.isNotBlank(field.getExpression())){
				final String showFieldName = field.getName();
				final LinkedHashSet<String> inputFieldNameList = new LinkedHashSet<String>();
				String expStr = field.getExpression();
				Pattern pattern=Pattern.compile("\\$\\((.+?)\\)");
				Matcher matcher=pattern.matcher(expStr);
		        while(matcher.find()){
		        	String name = matcher.group(1);
		        	inputFieldNameList.add(name);
		        }
				String resScript;
				try {
					resScript = FreemarkerUtils.processFile("/WEB-INF/template/admin/contract_category_script.ftl", new HashMap<String, Object>(){{
						put("contractCategoryId",id);
						put("showFieldName",showFieldName);
						put("inputFieldNameList",inputFieldNameList);
					}});
				} catch (Exception e) {
					e.printStackTrace();
					throw new PersonalException("freemaker 创建contract_category_script 失败："+e.getMessage());
				}
				expressionScriptList.add(resScript);
			}
		}
		return expressionScriptList;
	};

	public List<Field> getPermissionField(ContractCategory  contractCategory, Admin admin){
		return contractCategoryDao.getPermissionField(contractCategory, admin);
	}
	
	@Resource
	WorkFlowFormDao workFlowFormDao;
	public List<WorkFlowForm> getWorkFlowFormList(ContractCategory  contractCategory){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkFlowForm.class).add(Restrictions.eq("contractCategoryId", contractCategory.getId()));
		return workFlowFormDao.find(detachedCriteria);
	};
	
	
	public List<ContractCategory> getContentProviders(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ContractCategory.class).add(Restrictions.eq("contentProvider", true));
		return contractCategoryDao.find(detachedCriteria);
	}
	
	public void saveOrUpdate(ContractCategory ContractCategory,Collection<ContractCategory> childList){
		Collection<String> requestIdList =  CollectionUtils.collect(childList, new Transformer() {
			@Override
			public Object transform(Object arg0) {
				return ((ContractCategory)arg0).getId();
			}
		});
		for(ContractCategory child : ContractCategory.getChildSet()){
			if(!requestIdList.contains(child.getId())){
				child.setParent(null);
			}
		}
//		ContractCategory.getChildSet().clear();
//		ContractCategory.getChildSet().addAll(childList);
		for(String childId : requestIdList){
			ContractCategory contractCategoryChild = get(childId);
			contractCategoryChild.setParent(ContractCategory);
		}
	}
	
	/**
	 * 获取二级分类下某个字段的所有值
	 * @param contractCategoryId
	 * @param feildName
	 * @return
	 */
	public List<String> getFiledValueList(String contractCategoryId, String feildName){
		String sql = "select value from rzrk_contract_field where contract_category_id =:contractCategoryId and field_name =:feildName";
		return this.getBaseDao().getSession().createSQLQuery(sql).setParameter("contractCategoryId", contractCategoryId).setParameter("feildName", feildName).list();
	}
	@Override
	public Return3<Boolean, String,String> deletesWithCheck(String[] contractCategoryIds) {
		boolean res=true;
		StringBuffer errorStringBuffer = new StringBuffer();
		StringBuffer logInfoStringBuffer = new StringBuffer("删除类型");
		for (String id : contractCategoryIds) {
			ContractCategory cc = get(id);
			ContractField  contractField = contractDao.unique(DetachedCriteria.forClass(ContractField.class).add(Restrictions.eq("contractCategoryId", id)));
			if (contractField!=null) {
				errorStringBuffer.append(cc.getName()).append("数据被引用!");
				res=false;
				break;
			}
			
			List<WorkFlowForm> workFlowFormList =  workFlowFormService.find(DetachedCriteria.forClass(WorkFlowForm.class).add(Restrictions.eq("contractCategoryId", id)));
			if(workFlowFormList.size()>0){
				res=false;
				break;
			}
		}
		if(res){
			for(String id : contractCategoryIds){
				ContractCategory cc = get(id);
				deleteFromId(id);
				logInfoStringBuffer.append(" ").append(cc.getName());
			}
		}
		return new Return3<Boolean, String, String>(res, errorStringBuffer.toString(), logInfoStringBuffer.toString());
	}


}