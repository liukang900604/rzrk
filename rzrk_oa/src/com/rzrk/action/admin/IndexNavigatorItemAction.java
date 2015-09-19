/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.bean.Pager.Order;
import com.rzrk.entity.Article;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.IndexNavigatorItem;
import com.rzrk.entity.Job;
import com.rzrk.entity.Log;
import com.rzrk.entity.ProductDailyRecord;
import com.rzrk.entity.Project;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.SystemMessage;
import com.rzrk.entity.UserPlan;
import com.rzrk.entity.Work;
import com.rzrk.service.ArticleService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.IndexNavigatorItemService;
import com.rzrk.service.LogService;
import com.rzrk.service.ProductDailyRecordService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.SystemMessageService;
import com.rzrk.service.UserPlanService;
import com.rzrk.service.WorkService;
import com.rzrk.vo.querytree.Node;

/**                                                                                                                                    
 * Purpose: IndexNavigatorItemAction                                                               
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-7-10 
 */
@ParentPackage("admin")
public class IndexNavigatorItemAction extends BaseAdminAction{

	/**  */
	private static final long serialVersionUID = 4354269460294295515L;
	

	private IndexNavigatorItem indexNavigatorItem;

	@Resource(name = "indexNavigatorItemServiceImpl")
	private IndexNavigatorItemService indexNavigatorItemService;
	
	@Resource(name="rootContractCategoryServiceImpl")
	private RootContractCategoryService rootContractCategoryService;
	
	@Resource(name = "contractServiceImpl")
	private ContractService contractService;
	
	@Resource(name = "userPlanServiceImpl")
	private UserPlanService userPlanService;
	
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	
	@Resource(name = "contractCategoryServiceImpl")
	private ContractCategoryService contractCategoryService;
	
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	
	@Resource(name = "systemMessageServiceImpl")
	private SystemMessageService systemService;
	
	@Resource(name = "productDailyRecordServiceImpl")
	private ProductDailyRecordService productDailyRecordService;
	
	@Resource
	private WorkService workService;
	
	private String[] itemArray;
	

	public String[] getItemArray() {
		return itemArray;
	}


	public void setItemArray(String[] itemArray) {
		this.itemArray = itemArray;
	}


	public IndexNavigatorItem getIndexNavigatorItem() {
		return indexNavigatorItem;
	}


	public void setIndexNavigatorItem(IndexNavigatorItem indexNavigatorItem) {
		this.indexNavigatorItem = indexNavigatorItem;
	}

	public String index(){
		getRequest().setAttribute("loginAdmin", getLoginAdmin());
		getRequest().setAttribute("itemList", indexNavigatorItemService.getAllUserList(getLoginAdmin()));
		getRequest().setAttribute("rootContractCagetoryList", rootContractCategoryService.getAllList());
		return "index";
	}
	
	
	public String itemList(){
		indexNavigatorItem = indexNavigatorItemService.get(id);
		
		int listMaxCount = Integer.parseInt(getRequest().getParameter("listMaxCount")==null ? "10" : getRequest().getParameter("listMaxCount"));
		String url = indexNavigatorItem.getUrl();
		
		pager.setPageSize(listMaxCount);
		
		List<HashMap<String, String>> listResult =  new ArrayList<HashMap<String, String>>();
		
		
		if(url.contains("contract!list.action")){//二级分类
			String categoryId = url.split("contractCategoryId=")[1];
			Node node = new Node();
			List<HashMap<String, String>> list = (List<HashMap<String, String>>) contractService.findPager(pager, categoryId, node).getResult();
			for(HashMap<String, String> temp : list){
				Object[] titleList = (Object[]) temp.keySet().toArray();
				String name = "";
				if (titleList.length == 1){
					name = temp.get(titleList[0]);
				}else{
					name = temp.get(titleList[0]) + " " + temp.get(titleList[1]);
				}
				//jjliu修改，返回值顺序调整了下，首个为主键字段，rowId靠后
				String accessUrl = "contract!edit.action?rowId=" + temp.get("rowId") + "&contractCategoryId=" + categoryId;
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", name); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", temp.get("modifyDate")); 
		        
		        listResult.add(map);
			}

			
		}else if(url.contains("project!projectPlanList")){//项目类型
			String projectId = url.split("id=")[1];
			List<UserPlan> userPlanList = userPlanService.getListByProjectId(projectId,pager);
			for(UserPlan temp : userPlanList){
				String accessUrl = "user_plan!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getName()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}else if(url.contains("article!list.action")){//文章管理
			List<Article> articleList = (List<Article>) articleService.findPager(pager).getResult();
			for(Article temp : articleList){
				String accessUrl = "article!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getTitle()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}else if(url.contains("root_contract_category!list.action")){//一级分类
			List<RootContractCategory> rootContractCategoryList = (List<RootContractCategory>) rootContractCategoryService.findPager(pager).getResult();
			for(RootContractCategory temp : rootContractCategoryList){
				String accessUrl = "root_contract_category!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getName()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}else if(url.contains("contract_category!list.action")){//二级分类
			List<ContractCategory> contractCategoryList = (List<ContractCategory>) contractCategoryService.findPager(pager).getResult();
			for(ContractCategory temp : contractCategoryList){
				String accessUrl = "contract_category!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getName()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}else if(url.contains("project!list.action?hasNoRoot=0")){//项目管理
			pager.setHasNoRoot("0");
			List<Project> projectList = (List<Project>) projectService.findPager(pager).getResult();
			for(Project temp : projectList){
				String accessUrl = "project!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getName()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
//		else if(url.contains("log!list.action")){//查看日志
//			List<Log> logList = logService.getAllList();
//			for(Log temp : logList){
//				String accessUrl = "project!edit.action?id="+temp.getId();
//				
//		        HashMap<String, String> map = new HashMap<String,String>(); 
//		        
//		        map.put("name", temp.getName()); 
//		        map.put("accessUrl", accessUrl); 
//		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
//		        
//		        listResult.add(map);
//			}
//		}
	else if(url.contains("systemmessage!list.action")){//系统消息
			List<SystemMessage> projectList = (List<SystemMessage>) systemService.findPager(pager).getResult();
			for(SystemMessage temp : projectList){
				String accessUrl = "systemmessage!query.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getTitle()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
		else if(url.contains("product_daily_record!list.action?sort=productName&order=asc")){//产品每日汇总列表
			pager.setOrderBy("productName");
			pager.setOrder(Order.asc);
			List<ProductDailyRecord> projectList = (List<ProductDailyRecord>) productDailyRecordService.findPager(pager).getResult();
			for(ProductDailyRecord temp : projectList){
				String accessUrl = "product_daily_record!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getProductName() + "  " + temp.getBeforeReduceNetValue()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
		else if(url.contains("project!list.action?hasNoRoot=1")){//项目管理
			pager.setHasNoRoot("1");
			List<Project> projectList = (List<Project>) projectService.findPager(pager).getResult();
			for(Project temp : projectList){
				String accessUrl = "project!edit.action?id="+temp.getId();
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", temp.getName()); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
		else if(url.contains("check!getWorkCheck.action")){//需求开发审批
			JSONArray jsonArray = (JSONArray) workService.getRequestAjaxWorkList(pager, "1", getLoginAdmin()).get("rows");
			for(Object temp : jsonArray){
				Map<String, Object> tempMap = (Map<String, Object>) temp;
				String accessUrl = "";
				if(tempMap.get("workStatu").equals("3")){
					accessUrl = "work!editWorkCheck.action?id="+tempMap.get("id");
				}else{
					accessUrl = "work!viewMyWork.action?id="+tempMap.get("id");
				}
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", tempMap.get("workName") + " " + tempMap.get("flowName") + " " + tempMap.get("pointName")); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate",  DateUtil.formatDate((Date) tempMap.get("modifyDate"), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
		else if(url.contains("getAjaxWorkList.action")){//我的工作
			JSONArray jsonArray = (JSONArray) workService.getAjaxWorkList(pager, "3", getLoginAdmin()).get("rows");
			for(Object temp : jsonArray){
				Map<String, Object> tempMap = (Map<String, Object>) temp;
				String accessUrl = "work!viewMyWork.action?id="+tempMap.get("id");
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", tempMap.get("workName") + " " + tempMap.get("flowName") + " " + tempMap.get("pointName")); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate((Date) tempMap.get("modifyDate"), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
		else if(url.contains("work!getWorkCheck.action")){//工作审批
			JSONArray jsonArray = (JSONArray) workService.getAjaxWorkList(pager, "4", getLoginAdmin()).get("rows");
		for(Object temp : jsonArray){
				Map<String, Object> tempMap = (Map<String, Object>) temp;
				String accessUrl = "";
				if(tempMap.get("workStatu").equals("3")){
					accessUrl = "work!editWorkCheck.action?id="+tempMap.get("id");
				}else{
					accessUrl = "work!viewMyWork.action?id="+tempMap.get("id");
				}
				
	        HashMap<String, String> map = new HashMap<String,String>(); 
	        
		        map.put("name", tempMap.get("flowName") + " " + tempMap.get("pointName")); 
		        map.put("accessUrl", accessUrl); 
	        map.put("modifyDate", DateUtil.formatDate((Date) tempMap.get("modifyDate"), "yyyy-MM-dd HH:mm:ss")); 
	        
	        listResult.add(map);
		}
		}
		else if(url.contains("work!getWorkQuery.action")){//工作查询
			JSONArray jsonArray = (JSONArray) workService.getAjaxWorkList(pager, "3", getLoginAdmin()).get("rows");
			for(Object temp : jsonArray){
				Map<String, Object> tempMap = (Map<String, Object>) temp;
				String accessUrl = "work!viewMyWork.action?id="+tempMap.get("id");

		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", tempMap.get("workName") + " " + tempMap.get("flowName") + " " + tempMap.get("pointName")); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate((Date) tempMap.get("modifyDate"), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}else if(url.contains("work!getMyProcessWork.action")){//进展中的工作
			JSONArray jsonArray = (JSONArray) workService.getAjaxWorkList(pager, null, getLoginAdmin()).get("rows");
			for(Object temp : jsonArray){
				Map<String, Object> tempMap = (Map<String, Object>) temp;
				String accessUrl = "work!viewMyWork.action?id="+tempMap.get("id");
				
		        HashMap<String, String> map = new HashMap<String,String>(); 
		        
		        map.put("name", tempMap.get("workName") + " " + tempMap.get("flowName") + " " + tempMap.get("pointName")); 
		        map.put("accessUrl", accessUrl); 
		        map.put("modifyDate", DateUtil.formatDate((Date) tempMap.get("modifyDate"), "yyyy-MM-dd HH:mm:ss")); 
		        
		        listResult.add(map);
			}
		}
		
		
        getRequest().setAttribute("listResult", listResult);
	    return "itemlist";
	}
	
	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer();
		for(String temp : ids){
			IndexNavigatorItem deleteItem = indexNavigatorItemService.get(temp);
			logInfoStringBuffer.append(deleteItem.getName()+  " ");
		}
		try {
			indexNavigatorItemService.delete(ids);
			logInfo = "删除首页模块: " + logInfoStringBuffer.toString();
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			logInfo = "删除首页模块失败: " + e.getMessage();
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}

	public String add() {
		getRequest().setAttribute("jobTypeList", Job.JobType.values());
		return INPUT;
	}
	
	public String edit() {
		indexNavigatorItem = indexNavigatorItemService.load(id);
		getRequest().setAttribute("jobTypeList", Job.JobType.values());
		return INPUT;
	}
	
	
	//itemArray=[{"url": xxxx, "name": xxxx}, ]	
	public String save(){
		JSONArray jsonObj = new JSONArray();
		String name = "";
		
		//先是全部清空
		List<String> allUrlList = indexNavigatorItemService.getAllUrlList(getLoginAdmin());
		
		if(itemArray != null){
			for(String temp : itemArray){
				JSONObject tempJson = JSONObject.fromObject(temp);
				if(allUrlList.contains(tempJson.getString("url"))){
					IndexNavigatorItem iniItemTemp = indexNavigatorItemService.getOneByUrl(tempJson.getString("url"),getLoginAdmin());
			        Map<String,Object> map = new HashMap<String,Object>(); 
			        
			        map.put("id", iniItemTemp.getId()); 
			        map.put("originalId", tempJson.getString("id")); 
			        map.put("url", iniItemTemp.getUrl());
			        map.put("name",iniItemTemp.getName());
			        jsonObj.add(map);
			        
			        allUrlList.remove(tempJson.getString("url"));//删除之后剩下的即代表用户要删除
			        
					continue;
				}else{
					IndexNavigatorItem iniItemTemp = new IndexNavigatorItem();
					iniItemTemp.setUrl(tempJson.getString("url"));
					iniItemTemp.setName(tempJson.getString("name"));
					iniItemTemp.setAdmin(getLoginAdmin());
					indexNavigatorItemService.save(iniItemTemp);
			        Map<String,Object> map = new HashMap<String,Object>(); 
			        
			        map.put("id", iniItemTemp.getId()); 
			        map.put("originalId", tempJson.getString("id")); 
			        map.put("url", iniItemTemp.getUrl());
			        map.put("name",iniItemTemp.getName());
			        name =  name + ", ";
			        jsonObj.add(map);
				}
				
				

			}
		}
		
		if(itemArray == null){
			indexNavigatorItemService.deleteItemByAdmin(getLoginAdmin());
		}
		for(String temp : allUrlList){
			indexNavigatorItemService.deleteItemByUrl(temp, getLoginAdmin());
		}
		
		logInfo = "保存首页模块: " + name;
		
      Map<String,Object> map = new HashMap<String,Object>(); 
      
      map.put("status", Status.success); 
      map.put("userId", getLoginAdmin().getId()); 
      if(null != itemArray){
    	  map.put("result", jsonObj); 
      }else{
    	  map.put("result", ""); 
      }
      
      JSONObject  root = JSONObject.fromObject(map);
	  return ajax(root);
	}
	
//	public static void main(String[] args){
//		String tempArray = ["{\"url\": \"xxxx\", \"name\": \"xxxx\"}", "{\"url\":\"xxxx\", \"name\": \"xxxx\"}" ];
//	}

	// 列表
	public String list() {
		//pager = logService.findPager(pager);
		return LIST;
	}

}
