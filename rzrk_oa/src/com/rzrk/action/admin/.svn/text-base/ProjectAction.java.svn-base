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

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.UserPlanContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Project;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;
import com.rzrk.entity.UserPlan;
import com.rzrk.service.AdminProjectService;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.UserPlanLogService;
import com.rzrk.service.UserPlanService;
import com.rzrk.util.JsonUtil;

@ParentPackage("admin")
public class ProjectAction extends BaseAdminAction{
	/**  */
	private static final long serialVersionUID = -7166296567476722555L;

	private Project project = new Project();

	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "userPlanServiceImpl")
	private UserPlanService userPlanService;

	@Resource(name = "deparmentServiceImpl")
	private DeparmentService deparmentService;
	private List<Admin> adminList = new ArrayList<Admin>();
	
	@Resource(name = "rootContractCategoryServiceImpl")
	private RootContractCategoryService rootContractCategoryService;

	@Resource(name = "userPlanLogServiceImpl")
	private UserPlanLogService userPlanLogService;
	
	/**
	 * 个人项目service
	 */
	@Resource
	private AdminProjectService  adminProjectService;

	/**
	 * 二级分类service
	 */
	@Resource(name = "contractCategoryServiceImpl")
	private ContractCategoryService contractCategoryService;
	
	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	private String nameQuery;
	private String creatorQuery;
	private String responsiborQuery;
	private String statusQuery;
	private String  hasNoRoot;
	
	private String requireId;
	
	public String getRequireId() {
		return requireId;
	}

	public void setRequireId(String requireId) {
		this.requireId = requireId;
	}
	public String getNameQuery() {
		return nameQuery;
	}

	public String getHasNoRoot() {
		return hasNoRoot;
	}

	public void setHasNoRoot(String hasNoRoot) {
		this.hasNoRoot = hasNoRoot;
	}

	public void setNameQuery(String nameQuery) {
		this.nameQuery = nameQuery;
	}

	public String getCreatorQuery() {
		return creatorQuery;
	}

	public void setCreatorQuery(String creatorQuery) {
		this.creatorQuery = creatorQuery;
	}

	public String getResponsiborQuery() {
		return responsiborQuery;
	}

	public void setResponsiborQuery(String responsiborQuery) {
		this.responsiborQuery = responsiborQuery;
	}

	public String getStatusQuery() {
		return statusQuery;
	}

	public void setStatusQuery(String statusQuery) {
		this.statusQuery = statusQuery;
	}

	// 删除
	public String delete() {
		try{
			String deleteMessage = "";
			for(String temp : ids){
				Project deleteProject = projectService.get(temp);
				deleteMessage = deleteProject.getName() +  ", ";
			}
			projectService.deleteByProjectId(ids);
			logInfo = "删除项目: " + deleteMessage;
			return ajax(Status.success, "删除成功!");
	
		}catch(Exception e){
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}
	
	public String save() {
		if(adminList == null){
			adminList = new ArrayList<Admin>();
		}
		project.setProjectMember(new HashSet<Admin>(adminList));
		project.setCreator(getLoginAdmin());
		if(project.getStatus() == Project.Status.已完成){
			project.setProgress(100);
		}
		try{
			projectService.save(project);
			logInfo = "添加项目: " + project.getName();
			return ajax(Status.success, "添加成功!");
		}catch(Exception e){
			return ajax(Status.error, "添加失败!");
		}
		
	}
	
	public String checkIfNameExists(){
		String res = (!projectService.checkIfNameExists(project, project.getName()))+"";
		return ajax(res);
	}
	
	/**
	 * 查看显示需求页面
	 * @return
	 */
	public String showRequestList(){
		   String contractCategoryId = CategoryContants.REQUIREMENT_CATEGORY_ID;
			ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
			if(contractCategory == null){
				return "request_list";
			}
			String[] titleArr = contractCategory.getDefinitionObj().getTitles();
			String[] totalArr = new String[]{};
			totalArr = contractCategory.getDefinitionObj().getTotals();
			getRequest().setAttribute("contractCategoryName", contractCategory.getName());
			getRequest().setAttribute("contractCategoryTitles", JsonUtil.toJson(titleArr));
			getRequest().setAttribute("contractCategoryTotals", JsonUtil.toJson(totalArr));
			getRequest().setAttribute("titleArr", titleArr);
			getRequest().setAttribute("contractCategoryId", contractCategoryId);
			return "request_list";	
	}
	
	// 添加
	public String add() {
        adminList = adminService.getAllList();
		
		List<Deparment> deparmentList = deparmentService.getAllList();
		
		getRequest().setAttribute("rootContractCategoryList", rootContractCategoryService.getAllList());
		
		JSONArray jsonObj = new JSONArray();
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
	        

	        jsonObj.add(map);
		}
		
		getRequest().setAttribute("jsonObj", jsonObj);
		getRequest().setAttribute("projectTypeList", Project.ProjectType.values());
		getRequest().setAttribute("statusList", Project.Status.values());
		getRequest().setAttribute("hasNoRoot", getRequest().getParameter("hasNoRoot")==null? 0 : getRequest().getParameter("hasNoRoot"));

		return INPUT;
	}

	
	// 编辑
	public String edit() {
		project = projectService.get(id);
		adminList = adminService.getAllList();
		
		getRequest().setAttribute("rootContractCategoryList",  rootContractCategoryService.getByType(ROOT_CATEGORY_TYPE.项目模板));
		
		List<Deparment> deparmentList = deparmentService.getAllList();
		
		JSONArray jsonObj = new JSONArray();
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
	        

	        jsonObj.add(map);
		}
		
		getRequest().setAttribute("jsonObj", jsonObj);
		
		getRequest().setAttribute("projectTypeList", Project.ProjectType.values());
		getRequest().setAttribute("statusList", Project.Status.values());
		
		getRequest().setAttribute("logList", userPlanLogService.getByProjectId(id));
        
		return INPUT;
	}

	// 列表
	public String list() {
		JSONArray json = new JSONArray();
		//quanbu
		Map<String,Object> paramAll = new HashMap<String,Object>();
		paramAll.put("key", "全部");
		paramAll.put("value", "");
		json.add(paramAll);
		for(com.rzrk.entity.Project.Status statu : Project.Status.values()){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("key", statu);
			param.put("value", statu);
			json.add(param);
		}
       this.getRequest().setAttribute("statuArray",json);
		getRequest().setAttribute("hasNoRoot", getRequest().getParameter("hasNoRoot")==null? 0 : getRequest().getParameter("hasNoRoot"));
		return LIST;
	}
	

	@InputConfig(resultName = "error")
	public String update() {
		Project persistent = projectService.get(id);
		if(adminList == null){
			adminList = new ArrayList<Admin>();
		}
		if(project.getStatus() == Project.Status.已完成){
			project.setProgress(100);
		}
		project.setProjectMember(new HashSet<Admin>(adminList));
		if(project.getStatus() == Project.Status.已完成){
			project.setProgress(100);
		}
		
//		if(project.getStatus() == Project.Status.已完成){
//			userPlan.setEndTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
//		}
//		
	     try{
	    	 projectService.update(project,persistent,getLoginAdmin(),adminList);
	    	 redirectUrl = "project!list.action";
	 		 logInfo = "编辑项目: " + persistent.getName();
	 		return ajax(Status.success,"保存成功！");
	     }catch(Exception e){
	    	 return ajax(Status.error,"保存成功！");
	     }
		
		
	}
	
	/**
	 * 关注我的项目关注
	 * @return
	 */
	public String projectFollow(){
		Admin login = this.getLoginAdmin();//当前用户
		try{
		String str = projectService.projectFollow(login, id, project);
		return ajax(Status.success,str);
		}catch(Exception e){
			 return ajax(Status.error,e.getMessage());
		}
		
	}
	
	
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
//		List<Criterion> criterionList = new ArrayList<Criterion>();
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Project.class);
//		if(StringUtils.isNotBlank(nameQuery)){
//			detachedCriteria.add(Restrictions.like("name", StringUtils.join("%",nameQuery,"%")));
//		}
//		if(StringUtils.isNotBlank(creatorQuery)){
//			detachedCriteria.createAlias("creator", "c");
//			detachedCriteria.add(Restrictions.like("c.name", StringUtils.join("%",creatorQuery,"%")));
//		}
//		if(StringUtils.isNotBlank(responsiborQuery)){
//			detachedCriteria.createAlias("responsibor", "r");
//			detachedCriteria.add(Restrictions.like("r.name", StringUtils.join("%",responsiborQuery,"%")));
//		}
//		if(StringUtils.isNotBlank(statusQuery)){
//			Project.Status st;
//			try {
//				st = Project.Status.valueOf(statusQuery);
//				detachedCriteria.add(Restrictions.eq("status",st));
//			} catch (Exception e) {
//			}
//		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(nameQuery)){
			queryMap.put("nameQuery", nameQuery);
		}
		if(StringUtils.isNotBlank(creatorQuery)){
			queryMap.put("creatorQuery", creatorQuery);
		}
		if(StringUtils.isNotBlank(responsiborQuery)){
			queryMap.put("responsiborQuery", responsiborQuery);
		}
		if(StringUtils.isNotBlank(statusQuery)){
			queryMap.put("statusQuery", statusQuery);
		}
		queryMap.put("hasNoRoot", hasNoRoot);
		
		projectService.findPager(pager,queryMap);
		List<Project> projectList = (List<Project>) pager.getResult();
		Admin loginAdmin = this.getLoginAdmin();
		List<Project> tempProjectList = adminProjectService.getAdminProjectList(loginAdmin.getId());
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < projectList.size(); i++ ){
			Project temp = projectList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        map.put("projectType",temp.getProjectType());
	        map.put("content",temp.getContent());
	        map.put("beginTime",temp.getBeginTime());
	        map.put("endTime",temp.getEndTime());
	        map.put("progress", temp.getProgress());
	        map.put("status", temp.getStatus()); 
	        map.put("Creator", temp.getCreator().getName());
	        map.put("responsibor", temp.getResponsibor().getName());
	        map.put("deparment",temp.getDeparment().getName());
            map.put("modifyDate",DateUtil.formatDate(temp.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
            map.put("createDate",DateUtil.formatDate(temp.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
            if(tempProjectList.contains(temp)){
            	map.put("isFollow", UserPlanContants.FOLLOW); //是否关注
            }else{
            	map.put("isFollow", UserPlanContants.UN_FOLLOW); //是否关注
            }
// 看起来并没有用到这个
//            if(StringUtils.isNotEmpty(temp.getRequestRowids())){
//            	map.put("requestId", temp.getRequestRowids());
//            }else{
//            	map.put("requestId", "");
//            }
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}
	/**
	 * 如果存在requireId参数，增加 需求选择页面功能
	 * @return
	 */
	public String projectPlanList(){
		JSONArray json = new JSONArray();
		//quanbu
		Map<String,Object> paramAll = new HashMap<String,Object>();
		paramAll.put("key", "全部");
		paramAll.put("value", "");
		json.add(paramAll);
		for(com.rzrk.entity.UserPlan.Status statu : UserPlan.Status.values()){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("key", statu);
			param.put("value", statu);
			json.add(param);
		}
       this.getRequest().setAttribute("statuArray",json);
	    getRequest().setAttribute("projectName", getRequest().getParameter("projectName"));//获取所有部门json数据
		return "plan_list";
	}
	
	
	public String getProjectPlanAjaxList() {
		processAjaxPagerRequestParameter();
		List<UserPlan> userPlanList = userPlanService.getListByProjectId(id,pager);
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < userPlanList.size(); i++ ){
			UserPlan userPlan = userPlanList.get(i);

			Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", userPlan.getId());//计划id
	        map.put("userPlanUUID", String.format("%08d", userPlan.getUserPlanUUID()));//计划编号
	        map.put("name", userPlan.getName());//计划名称
	        map.put("planuser", userPlan.getAdmin().getName());//计划者
            map.put("content", userPlan.getContent());//内容
            map.put("beginTime", userPlan.getBeginTime());//开始时间
            map.put("creator", adminService.get(userPlan.getCreator().getId()).getName());//创建人
            map.put("endTime", userPlan.getEndTime());//结束时间
            map.put("progress", userPlan.getProgress());//进度
            map.put("remark", userPlan.getRemark());//备注
            map.put("status", userPlan.getStatus());//状态
            map.put("project", userPlan.getProject().getName());
            map.put("modifyDate",DateUtil.formatDate(userPlan.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
            map.put("createDate",DateUtil.formatDate(userPlan.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}
	
	@Resource
	ContractService contractService;
	public String initProjectIdForRequire(){
		List<Project> projectList = projectService.getAllList();
		JSONArray resArray = new JSONArray();
		for(Project project : projectList){
			for(String rowId : project.getRequestRowidList()){
				ContractField contractField = contractService.get(rowId);
				if(contractField!=null){
					contractField.setProjectId(project.getId());
					contractService.save(contractField);
					JSONObject res = new JSONObject();
					res.put("projectId", project.getId());
					res.put("rowId",rowId);
					resArray.add(res);
				}
			};
		}
		return ajax(resArray);
	}
}
