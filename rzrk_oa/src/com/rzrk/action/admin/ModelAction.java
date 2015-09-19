/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.JSONUtil;
import org.hibernate.mapping.Array;
import org.springframework.beans.BeanUtils;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.common.contract.ContractParserUser;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.JobLevel;
import com.rzrk.entity.Role;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.JobLevelService;
import com.rzrk.service.JobService;
import com.rzrk.service.RoleService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.util.CommonUtil;
import com.rzrk.util.HttpClientUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.SettingUtil;
import com.rzrk.util.StrUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.UserInModelPo;
import com.rzrk.vo.UserLoginfPO;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.KeyValueCollection;
import com.rzrk.vo.querytree.Node;

/**
 * 后台Action类 - 人员
 */

@ParentPackage("admin")
public class ModelAction extends BaseAdminAction {
	
	//modelPo各个属性过滤条件容器
	private Map<String,String> user = new HashMap<String, String>();
	private Map<String,String> account_type = new HashMap<String, String>();
	private Map<String,String> count = new HashMap<String, String>();
	private Map<String,String> expiretime = new HashMap<String, String>();
	private Map<String,String> etp_user = new HashMap<String, String>();
	private Map<String,String> interval = new HashMap<String, String>();
	private Map<String,String> isactive = new HashMap<String, String>();
	private Map<String,String> isupload = new HashMap<String, String>();
	private Map<String,String> modeltype = new HashMap<String, String>();
	private Map<String,String> type = new HashMap<String, String>();
	private LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(); 
	
	private String preQuery;
	
	public void setPreQuery(String preQuery){
		this.preQuery = preQuery;
	}

	public String getUser(){
		return "userlist";
	}
	
	public String getOnlineUser(){
			return "onlineUserList";
	}
	
	
	
	public String getAjaxList(){
//		String url = "http://localhost:8080/modeltest/getUsersByOA.action";
		String param = null;
		
		try{
			
			processAjaxPagerRequestParameter();
			String url = SettingUtil.getSetting().getModelUserUrl();
			if(preQuery!=null){
				if(pager.getOrderBy()!=null && pager.getOrder()!=null){
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize()+
							"&orderField="+pager.getOrderBy()+"&orderDirection="+pager.getOrder()+
							"&preQuery="+URLEncoder.encode(preQuery, "utf-8");
				}else{
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize()+"&preQuery="+URLEncoder.encode(preQuery, "utf-8");
				}
			}else{
				if(pager.getOrderBy()!=null && pager.getOrder()!=null){
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize()+
							"&orderField="+pager.getOrderBy()+"&orderDirection="+pager.getOrder();
				}else{
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize();
				}
			}
			String responseInfo = HttpClientUtil.sendPost(url, param);
			String total = responseInfo.substring(responseInfo.indexOf("*")+1);
			responseInfo = responseInfo.substring(0,responseInfo.indexOf("*"));
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(URLDecoder.decode(responseInfo,"UTF-8"));
			//List<Map<String,String>> result = new ArrayList<Map<String,String>>(); 
			List<UserInModelPo> result = new ArrayList<UserInModelPo>();
			int ct ;
			for (ct = 0; ct < jsonArray.size(); ct++) {
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(ct);
				com.alibaba.fastjson.JSONObject _id = (com.alibaba.fastjson.JSONObject) jsonObject.get("_id");
				UserInModelPo userModel = new UserInModelPo();
				
				String oid = (String) _id.get("$oid");userModel.set_id(oid);;
				String user= (String) jsonObject.get("user");userModel.setUser(user);
				String account_type= (String) jsonObject.get("account_type");userModel.setAccount_type(account_type);
				
				String count = null;
				Object object = jsonObject.get("count");
				if(jsonObject.get("count") instanceof Integer){
					count= ((Integer) jsonObject.get("count")).toString();
				}else if(jsonObject.get("count") instanceof BigDecimal){
					count= ((BigDecimal) jsonObject.get("count")).toString();
				}
				userModel.setCount(count);
				String etp_user= (String) jsonObject.get("etp_user");userModel.setEtp_user(etp_user);
				BigDecimal expiretime = ((BigDecimal) jsonObject.get("expiretime"));
				long longValue = expiretime.longValue();
//				String time = expiretime.indexOf(".") == -1 ? expiretime : expiretime.substring(0, expiretime.indexOf("."));
				Long timestamp = longValue * 1000;
				String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(timestamp));
				userModel.setExpiretime(date);
				String interval = null;
				if(jsonObject.get("interval") instanceof Integer){
					interval= ((Integer) jsonObject.get("interval")).toString();
				}else if(jsonObject.get("interval") instanceof BigDecimal){
					interval= ((BigDecimal) jsonObject.get("interval")).toString();
				}
				userModel.setInterval(interval);
				
				String isactive = null;
				if(jsonObject.get("isactive") instanceof Integer){
					isactive= ((Integer) jsonObject.get("isactive")).toString();
				}else if(jsonObject.get("interval") instanceof BigDecimal){
					isactive= ((BigDecimal) jsonObject.get("isactive")).toString();
				}
				userModel.setIsactive(isactive);
				
				String isupload = null;
				if(jsonObject.get("isupload") instanceof Integer){
					isupload= ((Integer) jsonObject.get("isupload")).toString();
				}else if(jsonObject.get("isupload") instanceof BigDecimal){
					isupload= ((BigDecimal) jsonObject.get("isupload")).toString();
				}
				userModel.setIsupload(isupload);
				
				String modeltype = null;
				if(jsonObject.get("modeltype") instanceof Integer){
					modeltype= ((Integer) jsonObject.get("modeltype")).toString();
				}else if(jsonObject.get("modeltype") instanceof BigDecimal){
					modeltype= ((BigDecimal) jsonObject.get("modeltype")).toString();
				}
				userModel.setModeltype(modeltype);
				
				String pass= (String) jsonObject.get("pass");userModel.setPass(pass);
				//String reguser= (String) jsonObject.get("reguser");userModel.setReguser(reguser);
				
				String type = null;
				if(jsonObject.get("type") instanceof Integer){
					type= ((Integer) jsonObject.get("type")).toString();
				}else if(jsonObject.get("type") instanceof BigDecimal){
					type= ((BigDecimal) jsonObject.get("type")).toString();
				}
				userModel.setType(type);
				//String modelauth= (String) jsonObject.get("modelauth");userModel.setModelauth(modelauth);
				result.add(userModel);
			}
			
			Map<String,Object> map = new HashMap<String,Object>(); 
			//processAjaxPagerRequestParameter();
			map.put("total", total); 
			map.put("rows", result);
			return ajax(map);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return ajax(Status.error,e.getMessage());
		}
	}
	

	/*private void processNode(Node node) {
		if(node.getCond()==null){
			Cond cond = new Cond();
			cond.setKeyword("");
			cond.setOperate("");
			cond.setSearchBy("");
			node.setCond(cond);
			
		}
		if(node.getChildren()!=null && node.getChildren().size() > 0){
			List<Node> children = node.getChildren();
			for (Node child : children) {
				processNode(child);
			}
		}
		if(node.getCond()!=null){
			String searchBy = node.getCond().getSearchBy();
			if(searchBy.equals("用户名")){
				node.getCond().setSearchBy("user");
			}else if(searchBy.equals("账户类型")){
				node.getCond().setSearchBy("account_type");
			}else if(searchBy.equals("最大同时登陆数量")){
				node.getCond().setSearchBy("count");
			}else if(searchBy.equals("过期时间")){
				node.getCond().setSearchBy("expiretime");
			}else if(searchBy.equals("用户平台")){
				node.getCond().setSearchBy("etp_user");
			}else if(searchBy.equals("间隔")){
				node.getCond().setSearchBy("interval");
			}else if(searchBy.equals("是否激活")){
				node.getCond().setSearchBy("isactive");
			}else if(searchBy.equals("是否上传")){
				node.getCond().setSearchBy("isupload");
			}else if(searchBy.equals("模型类型")){
				node.getCond().setSearchBy("modeltype");
			}else if(searchBy.equals("类型")){
				node.getCond().setSearchBy("type");
			}
		}
	}*/

	
	/**
	 * 获取模型在线用户数据
	 * @return
	 */
	public String getAjaxOnlineList(){
//		String url = "http://localhost:8080/modeltest/getUserLoginfoByOA.action";
		String param = null;
		
		
		try{
			processAjaxPagerRequestParameter();
			if(preQuery!=null){
				if(pager.getOrderBy()!=null && pager.getOrder()!=null){
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize()+
							"&orderField="+pager.getOrderBy()+"&orderDirection="+pager.getOrder()+
							"&preQuery="+URLEncoder.encode(JsonUtil.toJson(preQuery), "utf-8");
				}else{
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize()+"&preQuery="+URLEncoder.encode(preQuery, "utf-8");
				}
			}else{
				if(pager.getOrderBy()!=null && pager.getOrder()!=null){
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize()+
							"&orderField="+pager.getOrderBy()+"&orderDirection="+pager.getOrder();
				}else{
					param = "pageNum="+pager.getPageNumber()+"&numPerPage="+pager.getPageSize();
				}
			}
			String url = SettingUtil.getSetting().getModelOnlineUserUrl();
			String responseInfo = HttpClientUtil.sendPost(url, param);
			String total = responseInfo.substring(responseInfo.indexOf("*")+1);
			responseInfo = responseInfo.substring(0,responseInfo.indexOf("*"));
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(URLDecoder.decode(responseInfo,"UTF-8"));
			List<UserLoginfPO> result = new ArrayList<UserLoginfPO>();
			int count ;
			for (count = 0; count < jsonArray.size(); count++){
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(count);
				com.alibaba.fastjson.JSONObject _id = (com.alibaba.fastjson.JSONObject) jsonObject.get("_id");
				UserLoginfPO userLogininfPo = new UserLoginfPO();
				String oid = (String) _id.get("$oid");userLogininfPo.setId(oid);
				String proxyip = (String) jsonObject.get("proxyip");userLogininfPo.setProxyip(proxyip);
				String user = (String) jsonObject.get("user");userLogininfPo.setUser(user);
				String clientip = (String) jsonObject.get("clientip");userLogininfPo.setClientip(clientip);
				String clientport = ((Integer) jsonObject.get("clientport")).toString();userLogininfPo.setClientport(clientport);
				String datetime = (String) jsonObject.get("datetime");userLogininfPo.setDatetime(datetime);
				result.add(userLogininfPo);
			}
			Map<String,Object> map = new HashMap<String,Object>(); 
			map.put("total", total); 
			map.put("rows", result);
			return ajax(map);
		}catch(Exception e){
			return ajax(Status.error,"读取模型数据错误");
		}
	}

}