/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.Log;
import com.rzrk.service.LogService;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class LogAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private Log log;

	@Resource(name = "logServiceImpl")
	private LogService logService;
	
	// 删除
	public String delete() {
		logService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 列表
	public String list() {
		//pager = logService.findPager(pager);
		return LIST;
	}
	
	
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
		pager = logService.findPager(pager);
		List<Log> logList = (List<Log>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < logList.size(); i++ ){
		    Log temp = logList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("operation", temp.getOperation());
	        map.put("operator",temp.getOperator());
	        map.put("ip", temp.getIp());
	        map.put("info", temp.getInfo());
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}