package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.service.AdminService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.QueryHistoryService;
import com.rzrk.util.JsonUtil;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.queryhistory.ViewlayerItem;

@ParentPackage("admin")
public class QueryHistoryAction extends BaseAdminAction {
	QueryHistory queryHistory = new QueryHistory();
	
	
	
	public QueryHistory getQueryHistory() {
		return queryHistory;
	}

	public void setQueryHistory(QueryHistory queryHistory) {
		this.queryHistory = queryHistory;
	}

	@Resource
	private QueryHistoryService queryHistoryService;
	@Resource
	AdminService adminService;
 
	public String save() {
		Admin admin = getLoginAdmin();
		if(admin!=null){
			
			try {
				if(queryHistoryService.checkExist(admin,queryHistory,id)){
					throw new RuntimeException(queryHistory.getName()+"已存在");
				};
				queryHistory.setAdmin(admin);
				queryHistoryService.save(queryHistory);
				
				
//			admin.getQueryHistorySet().add(queryHistory);
//			adminService.saveOrUpdate(admin);
				return ajax(Status.success, "添加成功!");
			} catch (Exception e) {
				return ajax(Status.error, "添加失败!"+e.getMessage());
			}
		}else{
			return ajax(Status.error, "未登录!");
		}
	}

	// 列表
	public String list() {
		return LIST;
	}
	
	// 删除
	public String delete() {
		for (String id : ids) {
			QueryHistory queryHistory = queryHistoryService.load(id);
			queryHistoryService.delete(queryHistory);
		}
		return ajax(Status.success, "删除成功!");
	}

	public String getAjaxList() {
		Admin admin = getLoginAdmin();
		List<TreeItem> treeItemList = queryHistoryService.getQueryHistoryTree(admin);
		return ajax(treeItemList);
	}

	public String getAjaxList4Viewlayer() {
		Admin admin = getLoginAdmin();
		List<ViewlayerItem> treeItemList = queryHistoryService.getQueryHistoryTree4Viewlayer(admin);
		return ajax(treeItemList);
	}


}
