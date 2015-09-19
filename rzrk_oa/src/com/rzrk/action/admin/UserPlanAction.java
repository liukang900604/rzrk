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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.contants.BasicInformationContants;
import com.rzrk.contants.UserPlanContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.AdminProject;
import com.rzrk.entity.AdminUserPlan;
import com.rzrk.entity.Project;
import com.rzrk.entity.UserPlan;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.service.AdminProjectService;
import com.rzrk.service.AdminService;
import com.rzrk.service.AdminUserPlanService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.UserPlanLogService;
import com.rzrk.service.UserPlanService;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.FileVo;

@ParentPackage("admin")
public class UserPlanAction extends BaseAdminAction{
	/**  */
	private static final long serialVersionUID = -1544563910424226391L;
	private UserPlan userPlan;
	
	private List<UserPlan> userPlanList = new ArrayList<UserPlan>();

	@Resource(name = "userPlanServiceImpl")
	private UserPlanService userPlanService;
	
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "userPlanLogServiceImpl")
	private UserPlanLogService userPlanLogService;
	
	@Resource
	ContractService contractService;
	
	@Resource
	private AdminProjectService  adminProjectService;
	
	@Resource
	private DeparmentService  deparmentService;
	
	@Resource
	private ContractCategoryService contractCategoryService;
	
	/**
	 * 个人工作计划service
	 */
	@Resource
	private AdminUserPlanService adminUserPlanService;
	
	private List<Admin> adminList = new ArrayList<Admin>();
	
	private String projectId;

	private String nameQuery;
	private String creatorQuery;
	private String adminQuery;
	private String statusQuery;
	private String beginDate;
	private String endDate;
	private String[]  filePath;//文件真实路径
	private String[]  fileName;//文件名称
	
	
	private List<String> requireIds = new ArrayList<String>(); // 提交保存时的数据
	private String requireId;  //访问的时候参数
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getRequireIds() {
		return requireIds;
	}

	public void setRequireIds(List<String> requireIds) {
		this.requireIds = requireIds;
	}
	
	public String getRequireId() {
		return requireId;
	}

	public void setRequireId(String requireId) {
		this.requireId = requireId;
	}

	public String getNameQuery() {
		return nameQuery;
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

	public String getAdminQuery() {
		return adminQuery;
	}

	public void setAdminQuery(String adminQuery) {
		this.adminQuery = adminQuery;
	}

	public String getStatusQuery() {
		return statusQuery;
	}

	public void setStatusQuery(String statusQuery) {
		this.statusQuery = statusQuery;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public UserPlan getUserPlan() {
		return userPlan;
	}

	public void setUserPlan(UserPlan userPlan) {
		this.userPlan = userPlan;
	}


	public List<UserPlan> getUserPlanList() {
		return userPlanList;
	}

	public void setUserPlanList(List<UserPlan> userPlanList) {
		this.userPlanList = userPlanList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	public String[] getFileName() {
		return fileName;
	}

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

	/**
	 * 复制链接
	 * @return
	 */
	public String copyLink(){
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error, "计划信息不存在!");
		}
	    UserPlan userPlan = userPlanService.get(id);
	    if(userPlan == null){
	    	return ajax(Status.error, "计划信息不存在!");
	    }
	   String url =  this.getRequest().getRequestURL().toString();
	   String[] urlArray = url.split("/");
	   if(urlArray != null && urlArray.length > 0 ){
		  int index =  url.indexOf(urlArray[urlArray.length-1]);
		  String headUrl =  url.substring(0, index);
		         headUrl += "user_plan!realEdit.action?id=";
		         headUrl += id;
		         userPlan.setCopyLink(headUrl);
		         userPlanService.update(userPlan);
		         return ajax(Status.success, "复制链接成功,可在计划明细中查看!");
	   }else{
		   return ajax(Status.error, "服务器异常，请联系管理员重启服务!");
	   }
	   
	
		
	}
	// 删除
	public String delete() {
		try{
			String deleteMessage = "";
			for(String temp : ids){
				UserPlan deleteProject = userPlanService.get(temp);
				deleteMessage = deleteProject.getName() +  ", ";
			}
			userPlanService.deleteByUserPlanId(ids);
			logInfo = "删除项目计划: " + deleteMessage;
			return ajax(Status.success, "删除成功!");
		}catch(Exception e){
			return ajax(Status.error, "删除失败，请检查计划是否被关注或被关联!");
		}
	}
	
	public String save() {
		//userPlan.setUserPlanUUID(null);
		if (getLoginAdmin()==null) {
			return ajax(Status.error, "添加失败!");
		}
		userPlan.setCreator(getLoginAdmin());
		if(userPlan.getStatus() == UserPlan.Status.已完成){
			userPlan.setProgress(100);
		}
		
		if(userPlan.getStatus() == UserPlan.Status.已关闭){
			userPlan.setEndTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		}
		setCookie("projectId",userPlan.getProject().getId());  //存入项目Id
		 //文件名称和文件真实路径
		 if(fileName != null && filePath != null){
			 if(fileName.length == filePath.length){//判断长度是否一致
				 StringBuffer fileNameBuffer = new StringBuffer();
				StringBuffer filePathBuffer = new StringBuffer();
				 for(String file : filePath){
					filePathBuffer.append(file).append(",");
				 }
				 for(String file: fileName){
					fileNameBuffer.append(file).append(",");
				 }
				 userPlan.setFileName(fileNameBuffer.substring(0,fileNameBuffer.length()-1).toString());//文件名
				 userPlan.setFilePath(filePathBuffer.substring(0,filePathBuffer.length()-1).toString());//文件路径
			 }
		 }	
		userPlanService.save(userPlan,requireIds);
        userPlanService.evict(userPlan); //自增字段的存在，所以要刷新重新查一下
        userPlan = userPlanService.get(userPlan.getId());

		UserPlanLog upl = new UserPlanLog();
		upl.setOperator(getLoginAdmin());
		upl.setContent("创建项目");
		upl.setUserPlan(userPlan);
		
		userPlanLogService.save(upl);
		logInfo = "添加项目计划: " + userPlan.getName();
		
		Map<String,Object>  param = new HashMap<String,Object>();
		param.put("adminId", getLoginAdmin().getId());
		param.put("projectId", userPlan.getProject().getId());
		AdminProject  adminProject =  adminProjectService.getAdminProject(param);
		if(adminProject != null){
			if(UserPlanContants.FOLLOW.equals(adminProject.getIsFollow())){
				AdminUserPlan adminUserPlan = new AdminUserPlan();
				adminUserPlan.setUserPlan(userPlan);
				adminUserPlan.setFollowAdmin(getLoginAdmin());
				adminUserPlan.setIsFollow(UserPlanContants.FOLLOW);
				adminUserPlanService.saveOrUpdate(adminUserPlan);
			}
		}
		

		   String url =  this.getRequest().getRequestURL().toString();
		   String[] urlArray = url.split("/");
		   if(urlArray != null && urlArray.length > 0 ){
			  int index =  url.indexOf(urlArray[urlArray.length-1]);
			  String headUrl =  url.substring(0, index);
			         headUrl += UserPlanContants.FITER_URL;
			         headUrl += "?id=";
			         headUrl += userPlan.getId();
			         userPlan.setCopyLink(headUrl);
			         userPlanService.update(userPlan);
			         return ajax(Status.success, "增加成功");
		   }else{
			       return ajax(Status.error, "服务器异常，请联系管理员重启服务!");
		   }
		
		//return ajax(Status.success, "添加成功!");
	}
	
	// 添加
	public String add() {
		if(getCookie("projectId") != null){
			String cookie = getCookie("projectId");
			System.out.println(cookie);
			getRequest().setAttribute("pId", getCookie("projectId"));//存入上次添加计划的项目id,下面projectId不知道干什么用的    added by huanghui ; 2015.8.11
		}
		JSONArray requireArray = new JSONArray();
		if(StringUtils.isNotBlank(projectId)){
			Project project =  projectService.get(projectId);
			getRequest().setAttribute("project", project);
			List<Map> requireList = contractService.getProjectRelation(projectId);
			for(Map require : requireList){
				JSONObject requireObject = new JSONObject();
				String id =  (String) require.get("id");
				String number =  (String) require.get("number");
				String text =  StringUtils.defaultString((String) require.get("text"),"");
				String showText = "["+number+"]"+StringUtils.abbreviate(text, 0,10);
				requireObject.put("id",id);
				requireObject.put("text", showText);
				requireObject.put("name", showText);
				requireArray.add(requireObject);
			}
		}
		getRequest().setAttribute("projectList", projectService.getAllList());
		getRequest().setAttribute("statusList", UserPlan.Status.values());
        getRequest().setAttribute("severityList", UserPlan.Severity.values());
        getRequest().setAttribute("plantypeList", UserPlan.Plantype.values());
        getRequest().setAttribute("reproducibilityList", UserPlan.Reproducibility.values());
        getRequest().setAttribute("goalList", contractCategoryService.getFiledValueList(BasicInformationContants.GOAL_VERSION_CATEGORY_ID, BasicInformationContants.GOAL_VERSION));
        getRequest().setAttribute("productList", contractCategoryService.getFiledValueList(BasicInformationContants.PRODUCT_VERSION_CATEGORY_ID, BasicInformationContants.PRODUCT_VERSION));
		adminList = adminService.getAllList();
		getRequest().setAttribute("projectId", getRequest().getParameter("projectId"));
		
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ContractField.class);
//		detachedCriteria.add(Restrictions.eq("contractCategoryId", CategoryContants.REQUIREMENT_CATEGORY_ID)).add(Restrictions.eq("indication", true))
//			.add(Restrictions.eq("projectId",projectId)).addOrder(Order.desc("createDate"));
//		List<ContractField> contractFieldList = contractService.find(detachedCriteria);
		getRequest().setAttribute("requireArray", requireArray);
        getRequest().setAttribute("requireIdsList", new ArrayList<String>());
		return INPUT;
	}

	
	// 查看
	public String edit() {
        userPlan = userPlanService.load(id);
		getRequest().setAttribute("project", userPlan.getProject());
        getRequest().setAttribute("statusList", UserPlan.Status.values());
        getRequest().setAttribute("severityList", UserPlan.Severity.values());
        getRequest().setAttribute("plantypeList", UserPlan.Plantype.values());
        getRequest().setAttribute("reproducibilityList", UserPlan.Reproducibility.values());
        getRequest().setAttribute("projectList", projectService.getAllList());
        adminList = adminService.getAllList();
        getRequest().setAttribute("projectId", getRequest().getParameter("projectId"));
        getRequest().setAttribute("logList", userPlanLogService.getByUserPlanId(id));
       //isView 是查看页面还是编辑页面
        getRequest().setAttribute("isView", true);
		List<Map > userPlanRelationList =  contractService.getUserPlanRelation(id);
		List<String> requireNamesList = new ArrayList<String>();
		for(Map require : userPlanRelationList){
			String id =  (String) require.get("id");
			String number =  (String) require.get("number");
			String text =  StringUtils.defaultString((String) require.get("text"),"");
			String showText = "["+number+"]"+StringUtils.abbreviate(text, 0,10);
			requireNamesList.add(showText);
		}
        getRequest().setAttribute("requireNamesStr", StringUtils.join(requireNamesList,"<br>"));
        
        /*if(userPlan.getFileName() != null && userPlan.getFilePath() != null){
			String[] fileName = userPlan.getFileName().split(",");//文件名称
			String[] filePath = userPlan.getFilePath().split(",");//文件路径
			List<FileVo> fileList = new ArrayList<FileVo>();
			 for(int i = 0; i < fileName.length; i++){
				 FileVo vo = new FileVo();
				 vo.setFileName(fileName[i]);
				 vo.setFilePath(filePath[i]);
				 fileList.add(vo);
			 }
			 this.getRequest().setAttribute("fileList",fileList); //文件对象
		}*/
        

		 if(userPlan.getFileName() != null && userPlan.getFilePath() != null){
				String[] fileName = userPlan.getFileName().split(",");//文件名称
				String[] filePath = userPlan.getFilePath().split(",");//文件路径
				List<FileVo> fileList = new ArrayList<FileVo>();
				 for(int i = 0; i < fileName.length; i++){
					 FileVo vo = new FileVo();
					 vo.setFileName(fileName[i]);
					 vo.setFilePath(filePath[i]);
					 fileList.add(vo);
				 }
				 this.getRequest().setAttribute("fileList",fileList); //文件对象
			}
		return INPUT;
	}
	
	// 查看
	public String realEdit() {
        userPlan = userPlanService.load(id);
		getRequest().setAttribute("project", userPlan.getProject());
        getRequest().setAttribute("statusList", UserPlan.Status.values());
        getRequest().setAttribute("severityList", UserPlan.Severity.values());
        getRequest().setAttribute("plantypeList", UserPlan.Plantype.values());
        getRequest().setAttribute("reproducibilityList", UserPlan.Reproducibility.values());
        getRequest().setAttribute("projectList", projectService.getAllList());
        adminList = adminService.getAllList();
        getRequest().setAttribute("projectId", getRequest().getParameter("projectId"));
        getRequest().setAttribute("logList", userPlanLogService.getByUserPlanId(id));
       //isView 是查看页面还是编辑页面
        getRequest().setAttribute("isView", false);
        getRequest().setAttribute("goalList", contractCategoryService.getFiledValueList(BasicInformationContants.GOAL_VERSION_CATEGORY_ID, BasicInformationContants.GOAL_VERSION));
        getRequest().setAttribute("productList", contractCategoryService.getFiledValueList(BasicInformationContants.PRODUCT_VERSION_CATEGORY_ID, BasicInformationContants.PRODUCT_VERSION));
		List<Map > userPlanRelationList =  contractService.getUserPlanRelation(id);
		List<String> requireIdsList = new ArrayList<String>();
		for(Map require : userPlanRelationList){
			requireIdsList.add((String)require.get("id"));
		}
        getRequest().setAttribute("requireIdsList", requireIdsList);
		JSONArray requireArray = new JSONArray();
		List<Map> requireList = contractService.getProjectRelation(userPlan.getProject().getId());
		for(Map require : requireList){
			JSONObject requireObject = new JSONObject();
			String id =  (String) require.get("id");
			String number =  (String) require.get("number");
			String text =  StringUtils.defaultString((String) require.get("text"),"");
			String showText = "["+number+"]"+StringUtils.abbreviate(text, 0,10);
			requireObject.put("id",id);
			requireObject.put("text", showText);
			requireObject.put("name", showText);
			requireArray.add(requireObject);
		}
		getRequest().setAttribute("requireArray", requireArray);
		
		 if(userPlan.getFileName() != null && userPlan.getFilePath() != null){
				String[] fileName = userPlan.getFileName().split(",");//文件名称
				String[] filePath = userPlan.getFilePath().split(",");//文件路径
				List<FileVo> fileList = new ArrayList<FileVo>();
				 for(int i = 0; i < fileName.length; i++){
					 FileVo vo = new FileVo();
					 vo.setFileName(fileName[i]);
					 vo.setFilePath(filePath[i]);
					 fileList.add(vo);
				 }
				 this.getRequest().setAttribute("fileList",fileList); //文件对象
			}
		return INPUT;
	}
	
	/**
	 * 关注我的计划
	 * @return
	 */
	public String userPlanFollow(){
		Admin login = this.getLoginAdmin();//当前用户
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error, "计划信息不存在!");
		}
		userPlan = userPlanService.get(id);
		if(userPlan == null){
			return ajax(Status.error, "计划信息不存在!");
		}
		
		Map<String,Object>  param = new HashMap<String,Object>();
		param.put("adminId", login.getId());
		param.put("userPlanId", userPlan.getId());
		
		AdminUserPlan  adminUserPlan =adminUserPlanService.getAdminUserPlan(param);
		
		try{
			if(adminUserPlan != null){
				adminUserPlanService.delete(adminUserPlan);	
				if(UserPlanContants.FOLLOW.equals(adminUserPlan.getIsFollow())){
					adminUserPlan.setIsFollow(UserPlanContants.UN_FOLLOW);
				}
					
			}else{//新增操作
				adminUserPlan = new AdminUserPlan();
				adminUserPlan.setUserPlan(userPlan);
				adminUserPlan.setFollowAdmin(login);
				adminUserPlan.setIsFollow(UserPlanContants.FOLLOW);//关注
				adminUserPlanService.saveOrUpdate(adminUserPlan);
			}
			if(UserPlanContants.FOLLOW.equals(adminUserPlan.getIsFollow())){
				return ajax(Status.success, "关注成功!");
			}else{
				return ajax(Status.success, "取消关注成功!");
			}
		}catch(Exception e){
			if(UserPlanContants.FOLLOW.equals(adminUserPlan.getIsFollow())){
				return ajax(Status.error, "关注失败!");
			}else{
				return ajax(Status.error, "取消关注失败!");
			}
		}
		
	}

	
	// 列表
	public String list() {
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
		return LIST;
	}
	
	
	// 所有计划列表
	public String getAllList() {
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
       this.getRequest().setAttribute("showAll","1");
		return LIST;
	}
	
	
	//关注列表
	public String follow() {
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
		return "follow";
	}
		

	@InputConfig(resultName = "error")
	public String update() {
		UserPlan persistent = userPlanService.get(id);
		getRequest().setAttribute("statusList", UserPlan.Status.values());
		if(userPlan.getStatus() == UserPlan.Status.已完成){
			userPlan.setProgress(100);
		}
		 //文件名称和文件真实路径
		 if(fileName != null && filePath != null){
			 if(fileName.length == filePath.length){//判断长度是否一致
				 StringBuffer fileNameBuffer = new StringBuffer();
				StringBuffer filePathBuffer = new StringBuffer();
				 for(String file : filePath){
					filePathBuffer.append(file).append(",");
				 }
				 for(String file: fileName){
					fileNameBuffer.append(file).append(",");
				 }
				 persistent.setFileName(fileNameBuffer.substring(0,fileNameBuffer.length()-1).toString());//文件名
				 persistent.setFilePath(filePathBuffer.substring(0,filePathBuffer.length()-1).toString());//文件路径
			 }
		 }	
		userPlanService.update(userPlan,requireIds, persistent, getLoginAdmin());
		redirectUrl = "user_plan!list.action";
		logInfo = "更新项目计划: " + persistent.getName();
		return ajax(Status.success,"保存成功！");
	}
	
	
	public String getAjaxList() {
		List<String> planIdList = new ArrayList<String>();
		String showAll = this.getRequest().getParameter("showAll");
		if(StringUtils.isNotBlank(requireId)){
			List<UserPlan> userPlanList = userPlanService.getRequireRelation(requireId);
			for(UserPlan userPlan : userPlanList){
				planIdList.add(userPlan.getId());
			}
		}
		processAjaxPagerRequestParameter();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserPlan.class);
		
		if(StringUtils.isNotEmpty(projectId)){ //按项目查询
			Project project = projectService.get(projectId);
			detachedCriteria.add(Restrictions.eq("project", project));
		}else{	//按处理人,测试人查询
			if(StringUtils.isEmpty(showAll)){
				pager.setUserPlanAdminId(getLoginAdmin().getId());
			}
		}
		if(StringUtils.isNotBlank(nameQuery)){
			detachedCriteria.add(Restrictions.like("name", StringUtils.join("%",nameQuery,"%")));
		}
		if(StringUtils.isNotBlank(creatorQuery)){
			detachedCriteria.createAlias("creator", "c");
			detachedCriteria.add(Restrictions.in("c.id", creatorQuery.trim().split(",")));
		}
		if(StringUtils.isNotBlank(adminQuery)){
			detachedCriteria.createAlias("admin", "r");
			detachedCriteria.add(Restrictions.in("r.id", adminQuery.trim().split(",")));
		}
		if(StringUtils.isNotBlank(statusQuery)){
			ArrayList<UserPlan.Status> statusList = new ArrayList<UserPlan.Status>();
			for(String statusQueryStr : statusQuery.split(",")){
				if(StringUtils.isNotBlank(statusQueryStr)){
					UserPlan.Status st;
					try {
						st = UserPlan.Status.valueOf(statusQueryStr);
						statusList.add(st);
					} catch (Exception e) {
					}
				}
			}
			if(!statusList.isEmpty()){
				detachedCriteria.add(Restrictions.in("status",statusList));
			}
		}
		if(StringUtils.isNotBlank(beginDate)){
			detachedCriteria.add(Restrictions.ge("createDate", DateUtils.parseDate(beginDate,"yyyy-MM-dd")));
		}
		if(StringUtils.isNotBlank(endDate)){
			detachedCriteria.add(Restrictions.lt("createDate", org.apache.commons.lang3.time.DateUtils.addDays(DateUtils.parseDate(endDate,"yyyy-MM-dd"),1)));
		}

//		List<Criterion> criterionList = new ArrayList<Criterion>();
//		if(StringUtils.isNotEmpty(projectId)){ //按项目查询
//			Project project = projectService.get(projectId);
//			detachedCriteria.add(Restrictions.eq("project", project));
//		}else{	//按处理人查询
//			pager.setUserPlanAdminId(getLoginAdmin().getId());
//		}
//		if(StringUtils.isNotBlank(pager.getKeyword())){
//			String searchBy = pager.getSearchBy();
//			String keyword = StringUtils.defaultString(pager.getKeyword(),"").trim();
//			if(StringUtils.equals(searchBy, "name")){
//				detachedCriteria.add(Restrictions.like("name", "%"+keyword+"%"));
//			}else if(StringUtils.equals(searchBy, "creator")){
//				detachedCriteria.createAlias("creator", "c");
//				detachedCriteria.add(Restrictions.like("c.name",  "%"+keyword+"%"));
//			}else if(StringUtils.equals(searchBy, "admin")){
//				detachedCriteria.createAlias("admin", "a");
//				detachedCriteria.add(Restrictions.like("a.name", "%"+keyword+"%"));
//			}else if(StringUtils.equals(searchBy, "status")){
//				UserPlan.Status st;
//				try {
//					st = UserPlan.Status.valueOf(keyword);
//					detachedCriteria.add(Restrictions.eq("status", st));
//				} catch (Exception e) {
//				}
//			}else{
//				detachedCriteria.add(Restrictions.eq(searchBy, pager.getKeyword()));
//			}
//		}else{
//		}
//		pager.setSearchBy("");
//		pager.setKeyword("");
		pager = userPlanService.findPager(pager,detachedCriteria);
		List<UserPlan> userPlanList = (List<UserPlan>) pager.getResult();
		Admin loginAdmin = this.getLoginAdmin();
		List<UserPlan> tempPlanList = adminUserPlanService.getAdminUserPlanList(loginAdmin.getId());
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < userPlanList.size(); i++ ){
			UserPlan userPlan = userPlanList.get(i);

			Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", userPlan.getId());//计划id
	        map.put("userPlanUUID", String.format("%08d", userPlan.getUserPlanUUID()));//计划编号
	        map.put("name", userPlan.getName());//计划名称
	        map.put("planuser", userPlan.getAdmin().getName());//计划者
	        if(userPlan.getTestPerson()!=null){
	        	map.put("testPerson", userPlan.getTestPerson().getName());//测试人
	        }
            map.put("content", userPlan.getContent());//内容
            map.put("beginTime", userPlan.getBeginTime());//开始时间
            map.put("preEndTime", userPlan.getPreEndTime());//预计结束时间
            map.put("endTime", userPlan.getEndTime());//结束时间
            map.put("progress", userPlan.getProgress());//进度
            map.put("remark", userPlan.getRemark());//备注
            map.put("status", userPlan.getStatus());//状态
            map.put("project", userPlan.getProject().getName());
            map.put("modifyDate",DateUtils.formatDateTime(userPlan.getModifyDate()));
            map.put("createDate",DateUtils.formatDateTime(userPlan.getCreateDate()));
            map.put("creator", adminService.get(userPlan.getCreator().getId()).getName());//创建人
            if(tempPlanList.contains(userPlan)){
            	map.put("isFollow", UserPlanContants.FOLLOW); //是否关注
            }else{
            	map.put("isFollow", UserPlanContants.UN_FOLLOW); //是否关注
            }
            if(planIdList.contains(userPlan.getId())){
            	map.put("isChecked", true);
            }else{
            	map.put("isChecked", false);
            }
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}
	
	
	/**
	 * 获取关注列表
	 * @return
	 */
	public String getFollowAjaxList() {
		processAjaxPagerRequestParameter();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdminUserPlan.class);
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Admin loginAdmin  = this.getLoginAdmin();
		detachedCriteria.add(Restrictions.eq("isFollow", UserPlanContants.FOLLOW));
		detachedCriteria.add(Restrictions.eq("followAdmin", loginAdmin));
		if(StringUtils.isNotBlank(pager.getKeyword())){
			String searchBy = pager.getSearchBy();
			String keyword = StringUtils.defaultString(pager.getKeyword(),"").trim();
			if(StringUtils.equals(searchBy, "name")){
				detachedCriteria.createAlias("userPlan", "user");
				detachedCriteria.add(Restrictions.like("user.name", "%"+keyword+"%"));
			}else if(StringUtils.equals(searchBy, "creator")){
				detachedCriteria.createAlias("userPlan", "user");
				detachedCriteria.createAlias("user.creator", "c");
				detachedCriteria.add(Restrictions.like("c.name",  "%"+keyword+"%"));
			}else if(StringUtils.equals(searchBy, "admin")){
				detachedCriteria.createAlias("userPlan", "user");
				detachedCriteria.createAlias("user.admin", "a");
				detachedCriteria.add(Restrictions.like("a.name", "%"+keyword+"%"));
			}else if(StringUtils.equals(searchBy, "status")){
				UserPlan.Status st;
				try {
					st = UserPlan.Status.valueOf(keyword);
					detachedCriteria.createAlias("userPlan", "user");
					detachedCriteria.add(Restrictions.eq("user.status", st));
				} catch (Exception e) {
				}
			}else{
				detachedCriteria.add(Restrictions.eq(searchBy, pager.getKeyword()));
			}
		}else{
		}
		
		if(StringUtils.isNotBlank(pager.getOrderBy())){
			String orderBy = pager.getOrderBy();
			if(StringUtils.equalsIgnoreCase(orderBy, "planuser")){
				detachedCriteria.createAlias("userPlan", "user");
				detachedCriteria.createAlias("user.admin", "a");
				
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("a.name"));
				}else{
					detachedCriteria.addOrder(Order.desc("a.name"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "creator")){
				detachedCriteria.createAlias("userPlan", "user");
				detachedCriteria.createAlias("user.creator", "c");
				
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("c.name"));
				}else{
					detachedCriteria.addOrder(Order.desc("c.name"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "beginTime")){
				detachedCriteria.createAlias("userPlan", "user");
				
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.beginTime"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.beginTime"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "progress")){
				detachedCriteria.createAlias("userPlan", "user");
				
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.progress"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.progress"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "project")){
				detachedCriteria.createAlias("userPlan", "user");
				detachedCriteria.createAlias("userPlan.project", "p");
				
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("p.name"));
				}else{
					detachedCriteria.addOrder(Order.desc("p.name"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "userPlanUUID")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.userPlanUUID"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.userPlanUUID"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "name")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.name"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.name"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "remark")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.remark"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.remark"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "modifyDate")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.modifyDate"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.modifyDate"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "preEndTime")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.preEndTime"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.preEndTime"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "endTime")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.endTime"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.endTime"));
				}
				pager.setOrderBy("");
			}else if(StringUtils.equalsIgnoreCase(orderBy, "status")){
				detachedCriteria.createAlias("userPlan", "user");
				if(pager.getOrder()==com.rzrk.bean.Pager.Order.asc){
					detachedCriteria.addOrder(Order.asc("user.status"));
				}else{
					detachedCriteria.addOrder(Order.desc("user.status"));
				}
				pager.setOrderBy("");
			}
			
			
		}
		
		pager.setSearchBy("");
		pager.setKeyword("");
		pager = userPlanService.findPager(pager,detachedCriteria);
		List<AdminUserPlan> adminUserPlanList = (List<AdminUserPlan>) pager.getResult();
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < adminUserPlanList.size(); i++ ){
			UserPlan userPlan = adminUserPlanList.get(i).getUserPlan();

			Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", userPlan.getId());//计划id
	        map.put("userPlanUUID", userPlan.getUserPlanUUID());//计划编号
	        map.put("name", userPlan.getName());//计划名称
	        map.put("planuser", userPlan.getAdmin().getName());//计划者
            map.put("content", userPlan.getContent());//内容
            map.put("beginTime", userPlan.getBeginTime());//开始时间
            map.put("endTime", userPlan.getEndTime());//结束时间
            map.put("preEndTime", userPlan.getPreEndTime());//预计结束时间
            map.put("progress", userPlan.getProgress());//进度
            map.put("remark", userPlan.getRemark());//备注
            map.put("status", userPlan.getStatus());//状态
            map.put("project", userPlan.getProject().getName());
            map.put("modifyDate",DateUtil.formatDate(userPlan.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
            map.put("creator", adminService.get(userPlan.getCreator().getId()).getName());//创建人
           /* if(StringUtils.isNotEmpty(adminUserPlanList.get(i).getIsFollow())){
            	map.put("isFollow", adminUserPlanList.get(i).getIsFollow()); //是否关注
            }else{
            	map.put("isFollow", UserPlanContants.UN_FOLLOW); //是否关注
            }*/
            
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}

	public String addPlansForRequire(){
		try{
			String addPlansForRequireMessage = "";
			ids = ObjectUtils.defaultIfNull(ids, new String[]{});
			for(String temp : ids){
				UserPlan deleteProject = userPlanService.get(temp);
				addPlansForRequireMessage = deleteProject.getName() +  ", ";
			}
			userPlanService.addPlansForRequire(requireId, ids);
			logInfo = "归入需求: "+requireId+" " + addPlansForRequireMessage;
			return ajax(Status.success, "关联需求成功!");
		}catch(Exception e){
			return ajax(Status.error, "关联需求失败"+e.getMessage());
		}
	}
}
