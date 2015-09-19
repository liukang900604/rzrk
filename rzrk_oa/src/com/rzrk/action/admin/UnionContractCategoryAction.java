package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Product;
import com.rzrk.entity.Project;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.UnionContractCategory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.QueryHistoryService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.UnionContractCategoryService;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;

@ParentPackage("admin")
public class UnionContractCategoryAction extends BaseAdminAction {

	@Resource
	private UnionContractCategoryService unionContractCategoryService;
	@Resource
	AdminService adminService;
	@Resource
	RootContractCategoryService rootContractCategoryService;
	@Resource(name = "deparmentServiceImpl")
	private DeparmentService deparmentService;

	UnionContractCategory unionContractCategory = new UnionContractCategory();
	
	private List<RootContractCategory> rootContractCategoryList;
	 private List<Deparment> deparmentList = new ArrayList<Deparment>();//部门集合
	

	public UnionContractCategory getUnionContractCategory() {
		return unionContractCategory;
	}
	public void setUnionContractCategory(UnionContractCategory unionContractCategory) {
		this.unionContractCategory = unionContractCategory;
	}

	
	public List<Deparment> getDeparmentList() {
		return deparmentList;
	}
	public void setDeparmentList(List<Deparment> deparmentList) {
		this.deparmentList = deparmentList;
	}
	public List<RootContractCategory> getRootContractCategoryList() {
		return rootContractCategoryList;
	}
	public void setRootContractCategoryList(
			List<RootContractCategory> rootContractCategoryList) {
		this.rootContractCategoryList = rootContractCategoryList;
	}
	//TODO 待优化
	private JSONArray getChildJsonBuiltIn(Deparment parent){
		JSONArray jsonArray = new JSONArray();
		if(parent!=null){
			for(Deparment dep : parent.getChildDeparments()){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", dep.getId());
				jsonObj.put("text", dep.getName());
				jsonObj.put("children", getChildJsonBuiltIn(dep));
				jsonArray.add(jsonObj);
			}
		}
		return jsonArray;
	}
	private JSONArray getAjaxTree4BuiltIn() {
		JSONArray jsonRootArray = new JSONArray();
		List<Deparment> deparmentList = deparmentService.getRootDeparment();
	    if(deparmentList != null){
	    	for(Deparment root : deparmentList){
	    		if(root!=null){
	    			JSONObject jsonRoot = new JSONObject();
	    			jsonRoot.put("id", root.getId());
	    			jsonRoot.put("text", root.getName());
	    			jsonRoot.put("children", getChildJsonBuiltIn(root));
	    			jsonRootArray.add(jsonRoot);
	    		}
	    	}
	    }
		
		return jsonRootArray;
	}
	private JSONArray getAdmin4BuiltIn() {
		List<Admin> adminList = adminService.getAllList();
        JSONArray adminArr = new JSONArray();
		for(Admin admin : adminList){
			JSONObject adminObj = new JSONObject();
			adminObj.put("id", admin.getId());
			adminObj.put("name", admin.getName());
			adminArr.add(adminObj);
		}
		return adminArr;
	}
	// 编辑
	public String add() {
		rootContractCategoryList = rootContractCategoryService.getAllList();
		ArrayList<?> categoryTree= rootContractCategoryService.getListWithChildAndFields();
		String categoryTreeStr = JsonUtil.toJson(categoryTree);
		getRequest().setAttribute("categoryTree", categoryTreeStr);
	    List<Deparment> deparmentList = deparmentService.getAllList();
	    JSONArray departmentJobList = new JSONArray();
		for(int i = 0; i < deparmentList.size(); i++ ){
			Deparment temp = deparmentList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        Set<Job> deparmentJob = temp.getDeparmentJob();
	        
	        JSONArray jsonJobObj = new JSONArray();
	        for (Iterator iter = deparmentJob.iterator(); iter.hasNext();) {
	        	Map<String,Object> mapJob = new HashMap<String,Object>(); 
	        	Job job = (Job) iter.next();
	        	mapJob.put("id", job.getId());
	        	mapJob.put("name", job.getJobName());
	        	jsonJobObj.add(mapJob);
	        }
	        map.put("id", temp.getId());
	        map.put("name", temp.getName());
	        
	        Map<String,Object> jobMap = new HashMap<String,Object>(); 
	        jobMap.put("id", temp.getId());
	        jobMap.put("jobList", jsonJobObj);
	        map.put("data", jobMap);
	        if(null != temp.getParent()){
	        	map.put("parent", temp.getParent().getId());
	        }else{
	        	map.put("parent", "");
	        }
	        

	        departmentJobList.add(map);
		}
		getRequest().setAttribute("admin4Easyui", getAdmin4BuiltIn());
		getRequest().setAttribute("depTree4Easyui", getAjaxTree4BuiltIn());
		getRequest().setAttribute("departmentJobList", departmentJobList);
		getRequest().setAttribute("deparmentList", deparmentList);
		return INPUT;
	}
	// 编辑
	public String edit() {
		rootContractCategoryList = rootContractCategoryService.getAllList();
		unionContractCategory = unionContractCategoryService.get(id);
		ArrayList<?> categoryTree= rootContractCategoryService.getListWithChildAndFields();
		String categoryTreeStr = JsonUtil.toJson(categoryTree);
		getRequest().setAttribute("categoryTree", categoryTreeStr);
	    List<Deparment> deparmentList = deparmentService.getAllList();
	    JSONArray departmentJobList = new JSONArray();
		for(int i = 0; i < deparmentList.size(); i++ ){
			Deparment temp = deparmentList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        Set<Job> deparmentJob = temp.getDeparmentJob();
	        
	        JSONArray jsonJobObj = new JSONArray();
	        for (Iterator iter = deparmentJob.iterator(); iter.hasNext();) {
	        	Map<String,Object> mapJob = new HashMap<String,Object>(); 
	        	Job job = (Job) iter.next();
	        	mapJob.put("id", job.getId());
	        	mapJob.put("name", job.getJobName());
	        	jsonJobObj.add(mapJob);
	        }
	        map.put("id", temp.getId());
	        map.put("name", temp.getName());
	        
	        Map<String,Object> jobMap = new HashMap<String,Object>(); 
	        jobMap.put("id", temp.getId());
	        jobMap.put("jobList", jsonJobObj);
	        map.put("data", jobMap);
	        if(null != temp.getParent()){
	        	map.put("parent", temp.getParent().getId());
	        }else{
	        	map.put("parent", "");
	        }
	        

	        departmentJobList.add(map);
		}
		getRequest().setAttribute("admin4Easyui", getAdmin4BuiltIn());
		getRequest().setAttribute("depTree4Easyui", getAjaxTree4BuiltIn());
		getRequest().setAttribute("departmentJobList", departmentJobList);
		getRequest().setAttribute("deparmentList", deparmentList);
		getRequest().setAttribute("definitionJson", JsonUtil.toJson(unionContractCategory.getDefinitionObj()));
		return INPUT;
	}

	boolean checkExist(UnionContractCategory unionContractCategory){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UnionContractCategory.class);
		detachedCriteria.add(Restrictions.eq("name", unionContractCategory.getName()));
		if(unionContractCategoryService.unique(detachedCriteria)!=null){
			return true;
		}else{
			return false;
		}
	}
	
    @InputConfig(resultName = "error")
	public String save() {
		Admin admin = getLoginAdmin();
		if(admin!=null){
			try {
				if(checkExist(unionContractCategory)){
					throw new RuntimeException(unionContractCategory.getName()+"已存在");
				};
				unionContractCategory.setDeparmentSet(new HashSet<Deparment>(deparmentList));
				unionContractCategoryService.save(unionContractCategory);
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
			UnionContractCategory persistent = unionContractCategoryService.get(id);
			BeanUtils.copyProperties(unionContractCategory, persistent, new String[] {"id", "createDate", "modifyDate", "deparmentList","admin", "queryHistorySet"});
			persistent.setDeparmentSet(new HashSet<Deparment>(deparmentList));
			unionContractCategoryService.update(persistent);
  			return ajax(Status.success, "修改成功！");
		} catch (Exception e) {
  			return ajax(Status.error, "修改失败！"+e.getMessage());
		}

	}

	// 删除
	public String delete() {
		try {
			for (String id : ids) {
				UnionContractCategory viewlayer = unionContractCategoryService.get(id);
				unionContractCategoryService.delete(viewlayer);
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
		processAjaxPagerRequestParameter();
		
		String orderBy = getRequest().getParameter("sort");// 排序字段
		String orderStr = getRequest().getParameter("order");// 排序方式
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UnionContractCategory.class);
		pager.setOrderBy("");
//		pager = contractCategoryService.findPager(pager,Restrictions.eq("recyle", false));
		
		if(StringUtils.isNotEmpty(orderBy)){
			if(StringUtils.equals(orderBy, "rootContractCategoryName")){
				detachedCriteria.createAlias("rootContractCategory", "r");
				orderBy = "r.name";
			}
	    	if (orderStr.equals("asc")){
	    		detachedCriteria.addOrder(Order.asc(orderBy));
	    	}else{
	    		detachedCriteria.addOrder(Order.desc(orderBy));
	    	}
		}
		pager = unionContractCategoryService.findPager(pager,detachedCriteria);
		List<UnionContractCategory> ccList = (List<UnionContractCategory>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < ccList.size(); i++ ){
			UnionContractCategory temp = ccList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        map.put("fields",StringUtils.join(temp.getDefinitionObj().getTitles(),","));
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        RootContractCategory catrgory =  temp.getRootContractCategory();
	        if(catrgory != null){
	        	if(catrgory.getName() != null){
	        		map.put("rootContractCategoryName", temp.getRootContractCategory().getName());
	        	}
	        }else{
	        	map.put("rootContractCategoryName", "");
	        }
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
		
	}

}
