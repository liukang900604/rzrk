///**
// * Project Name: rzrk Web
// * Confidential and Proprietary                                                                
// * Copyright (C) 2013 By                                                                                     
// * rzrk Company                 
// * All Rights Reserved                                                                                                                                                                                                                       
// */
//package com.rzrk.action.admin;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import net.sf.json.JSONArray;
//
//import org.apache.struts2.convention.annotation.ParentPackage;
//
//import com.opensymphony.xwork2.ModelDriven;
//import com.rzrk.action.admin.BaseAdminAction.Status;
//import com.rzrk.entity.Admin;
//import com.rzrk.entity.Deparment;
//import com.rzrk.entity.Job;
//import com.rzrk.entity.ProjectCategory;
//import com.rzrk.entity.RootContractCategory;
//import com.rzrk.entity.SystemMessage;
//import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;
//import com.rzrk.entity.SystemMessage.RedType;
//import com.rzrk.entity.SystemMessage.SystemmessageType;
//import com.rzrk.service.ProjectCategoryService;
//import com.rzrk.service.RootContractCategoryService;
//
///**
// * 后台Action类 - 日志
// */
//
//@ParentPackage("admin")
//public class ProjectCategoryAction extends BaseAdminAction {
//
//	private static final long serialVersionUID = 8784555891643520648L;
//	private ProjectCategory projectCategory;
//
//	@Resource(name = "projectCategoryServiceImpl")
//	private ProjectCategoryService projectCategoryService;
//	
//	@Resource(name = "rootContractCategoryServiceImpl")
//	private RootContractCategoryService rootContractCategoryService;
//	
//	private List<RootContractCategory> rootContractCategoryList;
//		
//	public ProjectCategory getProjectCategory() {
//		return projectCategory;
//	}
//
//	public void setProjectCategory(ProjectCategory projectCategory) {
//		this.projectCategory = projectCategory;
//	}
//
//	public List<RootContractCategory> getRootContractCategoryList() {
//		return rootContractCategoryList;
//	}
//
//	public void setRootContractCategoryList(List<RootContractCategory> rootContractCategoryList) {
//		this.rootContractCategoryList = rootContractCategoryList;
//	}
//	/**
//	 * 删除方法
//	 * @return
//	 */
//	public String delete() {
//		try {
//			projectCategoryService.delete(ids);
//			return ajax(Status.success, "删除成功!");
//
//		} catch (Exception e) {
//			return ajax(Status.error, "删除失败，请检查是否被关联!");
//		}
//	}
//
//	/**
//	 * 增加方法
//	 * @return
//	 */
//	public String save() {
//		projectCategoryService.save(projectCategory);
//		return ajax(Status.success, "添加成功!");
//	}
//	
//	
//	/**
//	 * 获取所有消息数据
//	 * @return
//	 */
//	public String getAjaxList() {
//		processAjaxPagerRequestParameter();
//		pager = projectCategoryService.findPager(pager);
//		List<ProjectCategory> list = (List<ProjectCategory>) pager.getResult();
//
//		JSONArray jsonObj = new JSONArray();
//		for (int i = 0; i < list.size(); i++) {
//			ProjectCategory temp = list.get(i);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("id", temp.getId());
//			map.put("name", temp.getName());
//			map.put("root", temp.getRootContractCategory().getName());
//			map.put("remark", temp.getRemark());
//			jsonObj.add(map);
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("total", pager.getTotalCount());
//		map.put("rows", jsonObj);
//
//		return ajax(map);
//	}
//	// 编辑
//		public String edit() {
//			projectCategory = projectCategoryService.get(id);
//			rootContractCategoryList = rootContractCategoryService.getByType(ROOT_CATEGORY_TYPE.项目模板);
//			return INPUT;
//		}
//		// 更新
//	public String update() {	
//		projectCategoryService.update(projectCategory);
//		return ajax(Status.success, "更新成功!");
//	}
//	/**
//	 * 增加页面跳转
//	 * @return
//	 */
//	public String add() {
//		rootContractCategoryList = rootContractCategoryService.getByType(ROOT_CATEGORY_TYPE.项目模板);
//		return INPUT;
//	}
//	/**
//	 * 消息页面跳转
//	 * @return
//	 */
//	public String list() {
//		return LIST;
//	}
//}