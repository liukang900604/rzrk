package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Product;
import com.rzrk.entity.Project;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.QueryHistoryService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.ViewlayerService;
import com.rzrk.util.JsonUtil;
import com.rzrk.vo.queryhistory.TreeItem;

@ParentPackage("admin")
public class ViewlayerAction extends BaseAdminAction {

	Viewlayer viewlayer = new Viewlayer();
	
	public Viewlayer getViewlayer() {
		return viewlayer;
	}

	public void setViewlayer(Viewlayer viewlayer) {
		this.viewlayer = viewlayer;
	}

	@Resource
	private ViewlayerService viewlayerService;
	@Resource
	AdminService adminService;
	@Resource
	ContractCategoryService contractCategoryService;
	@Resource
	RootContractCategoryService rootContractCategoryService;

	
	// 编辑
	public String add() {
		ArrayList<?> categoryTree= rootContractCategoryService.getListWithChild();
		String categoryTreeStr = JsonUtil.toJson(categoryTree);
		getRequest().setAttribute("categoryTree", categoryTreeStr);
		return INPUT;
	}
	// 编辑
	public String edit() {
		viewlayer = viewlayerService.get(id);
		ArrayList<?> categoryTree= rootContractCategoryService.getListWithChild();
		String categoryTreeStr = JsonUtil.toJson(categoryTree);
		getRequest().setAttribute("categoryTree", categoryTreeStr);
		return INPUT;
	}


	
    @InputConfig(resultName = "error")
	public String save() {
		Admin admin = getLoginAdmin();
		if(admin!=null){
			try {
				if(viewlayerService.checkExist(viewlayer,id)){
					throw new RuntimeException(viewlayer.getName()+"已存在");
				};
				viewlayer.setAdmin(admin);
				viewlayerService.save(viewlayer);
//			admin.getViewlayerSet().add(viewlayer);
//			adminService.save(admin);
				return ajax(Status.success, "添加成功!");
			} catch (Exception e) {
	  			return ajax(Status.error, "添加失败！"+e.getMessage());
			}
		}else{
			return ajax(Status.error, "未登录!");
		}
	}

    @InputConfig(resultName = "error")
	public String update() {
		try {
			if(viewlayerService.checkExist(viewlayer,id)){
				throw new RuntimeException(viewlayer.getName()+"已存在");
			};
			Viewlayer persistent = viewlayerService.get(id);
			BeanUtils.copyProperties(viewlayer, persistent, new String[] {"id", "createDate", "modifyDate", "admin", "queryHistorySet"});
			viewlayerService.update(persistent);
  			return ajax(Status.success, "修改成功！");
		} catch (Exception e) {
  			return ajax(Status.error, "修改失败！"+e.getMessage());
		}

	}

	// 删除
	public String delete() {
		try {
			for (String id : ids) {
				Viewlayer viewlayer = viewlayerService.get(id);
				viewlayerService.delete(viewlayer);
			}
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
  			return ajax(Status.error, "删除失败！"+e.getMessage());
		}
	}

	// 列表
	public String list() {
		return LIST;
	}

	public String getAjaxList() {
		Admin admin = getLoginAdmin();
		List<Viewlayer> vlist = viewlayerService.getList(admin);
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < vlist.size(); i++ ){
			Viewlayer temp = vlist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        map.put("definition",temp.getDefinition());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total",vlist.size()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}

}
