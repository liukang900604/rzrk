package com.rzrk.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.rzrk.bean.Pager;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ProjectDao;
import com.rzrk.dao.QueryHistoryDao;
import com.rzrk.dao.ViewlayerDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.service.QueryHistoryService;
import com.rzrk.service.ViewlayerService;
import com.rzrk.util.JsUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.NumberUtil;
import com.rzrk.vo.queryhistory.DataItem;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.viewlayer.Definition;
import com.rzrk.vo.viewlayer.Field;

@Service("viewlayerServiceImpl")
public class ViewlayerServiceImpl extends BaseServiceImpl<Viewlayer, String> implements ViewlayerService{
	
	@Resource(name = "viewlayerDaoImpl")
	private ViewlayerDao viewlayerDao;
	
	@Resource(name = "viewlayerDaoImpl")
	public void setBaseDao(ViewlayerDao viewlayerDao) {
		super.setBaseDao(viewlayerDao);
	}
	@Resource
	AdminDao adminDao;
	
	
	public List<Viewlayer> getList(Admin admin){
		List<Viewlayer> treeItemList= new ArrayList<Viewlayer>(admin.getViewlayerSet());
		
		return treeItemList;
	}

	
	public List<Map> getShowList(Admin admin){
		List<Map> treeItemList= new ArrayList<Map>();
		
		return treeItemList;
	}
	
	private Map<String, String> getViewlayerEntity(Definition definition){
		HashMap<String, String> item = new HashMap<String, String>();
		for(Field field : definition){
			item.put(field.getShowField(),"");
		}
		return item;
	}
	public Pager findShowPager(Pager pager, String id,Node node){
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		
		Viewlayer viewlayer = viewlayerDao.get(id);
//		String definitionStr = viewlayer.getDefinition();
		Definition definition = viewlayer.getDefinitionObj();
		Map<String,Object> dataMap=viewlayerDao.findShowPager(definition, pager,node);
		
		List<ContractField> contractFields = (List<ContractField>) dataMap.get("result");
		List<Map> footer = (List<Map>) dataMap.get("footer");
		int total = (Integer) dataMap.get("total");
		pager.setTotalCount(total);
		LinkedHashMap<String,Map<String, String>> noOrderMap = new LinkedHashMap<String,Map<String,String>>();
		for(ContractField field : contractFields){
			if (field.isIndication()){
				Map<String, String> item = getViewlayerEntity(definition);
				item.put("rowId", field.getId());
				noOrderMap.put(field.getRowId(), item);
			}
		}
		for(ContractField field : contractFields){
			Map<String, String> item =  noOrderMap.get(field.getRowId());
			if (item!=null){
				item.put(field.getFieldName(), field.getValue());
			}
		}
		Definition expDef = definition.getExpressionFileds();
        Pattern pattern=Pattern.compile("\\$\\((.+?)\\)");
		for(Map<String, String> entity : noOrderMap.values()){
			for(Field field : expDef){
				String expStr = field.getExpression();
				if(StringUtils.isNotBlank(expStr)){
			        Matcher matcher=pattern.matcher(expStr);
			        StringBuffer sb=new StringBuffer();
			        boolean find=false;
			        while(find=matcher.find()){
			        	String name = matcher.group(1);
			        	String placeholder = entity.get(name);
			        	if(StringUtils.isBlank(placeholder)){
			        		placeholder = "0";
			        	}
			        	matcher.appendReplacement(sb,placeholder);
			        }
			        matcher.appendTail(sb);
			        String expStrTarget = sb.toString();
			        Number num = JsUtil.getNumber(expStrTarget);
			        String res;
			        BigDecimal gd = BigDecimal.valueOf(num.doubleValue());
					if(gd.doubleValue() == gd.longValue()){
						res = gd.toBigInteger().toString();
					}else{
						res = BigDecimal.valueOf(num.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
					}
			        entity.put(field.getShowField(), res);
				}
			}
		}
		
		List<Field> permissionFieldList = viewlayerDao.getPermissionField(definition, loginAdmin);
		List<Map<String,String>> orderlist = new ArrayList<Map<String,String>>(); //有序结果
		
		for(Map<String, String> noOrderEntity : noOrderMap.values()){
			LinkedHashMap<String, String> orderEntity = new LinkedHashMap<String, String>();
			for(Field permissionField : permissionFieldList){
				String key = permissionField.getShowField();
				if(noOrderEntity.containsKey(key)){
					orderEntity.put(key, StringUtils.defaultIfBlank(noOrderEntity.get(key),""));
				}else{
					orderEntity.put(key,"");
				}
			}
			orderEntity.put("rowId", noOrderEntity.get("rowId"));
			orderlist.add(orderEntity);
		}
		
//		for(Map<String, String> item : dataMap.values()){
//			formatRow(item);
//		}
		pager.setResult(orderlist);
		
//		List<Map> footerList= new ArrayList<Map>();
//		Map<String,String> footerMap = new HashMap<String, String>();
//		String primary = definition.get(0).getTableField();
//		footerMap.put(primary, "总计");
//		for(Map fentity : footer){
//			String name = (String) fentity.get("name");
//			Number num = (Number) fentity.get("num");
//			footerMap.put(name, num.toString());
//		}
//		footerList.add(footerMap);
//		pager.setFooter(footerList);
		return pager;

	}
	
	public boolean checkExist(Viewlayer viewlayer,String id){
		Viewlayer _viewlayer = viewlayerDao.find("name", viewlayer.getName());
		if(_viewlayer!=null && !StringUtils.equals(id, _viewlayer.getId())){
			return true;
		}else{
			return false;
		}

	};

	public List<Field> getPermissionField(Definition definition, Admin admin){
		return viewlayerDao.getPermissionField(definition, admin);
	}

}
