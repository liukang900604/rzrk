/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.junit.Test;

import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ApprovalRecord;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Project;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.entity.WorkFlowType;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.MailService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.SmsService;
import com.rzrk.service.SystemMessageService;
import com.rzrk.service.WorkFlowPointService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.service.WorkFlowTypeService;
import com.rzrk.service.WorkService;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.FileVo;
import com.rzrk.vo.workflow.Require4workflow;


/**
 * 工作Action(我的工作、工作审核 工作查询)
 * @author kang.liu
 */

@ParentPackage("admin")
public class WorkAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

//	private String id; //接收主键
	private Work work;//封装我的工作
	private String flowTypeId;//工作流类型id 
	private List<Admin>  adminList;//接收审批人
	private ApprovalRecord record;//审批记录
	private String[]  filePath;//文件真实路径
	private String[]  fileName;//文件名称
	private String fieldId;
	private String isDelete;//是否删除合同字段   1：删除  2：不删除
	
	/**
	 * 系统消息service
	 */
	@Resource
	private SystemMessageService systemService;
	
	/**
	 * 短信发送接口
	 */
	@Resource
	private SmsService  smsService;
	
	/**
	 * 邮件发送接口
	 */
	@Resource
	private MailService mailService;
	/**
	 * 二级分类字段service
	 */
	@Resource
	private ContractService contractService;
	/**
	 * 工作流接口service
	 */
	@Resource
	private WorkService workService;
	
	/**
	 * 工作流类型service
	 */
	@Resource
	private WorkFlowTypeService workFlowTypeService;
	
	/**
	 * 工作流节点service
	 */
	@Resource
	private WorkFlowPointService workFlowPointService;
	
	/**
	 * 工作流流程service
	 */
	@Resource
	private WorkFlowService workFlowService;
	
	/**
	 * 人员service
	 */
	@Resource
	private AdminService adminService;
	/**
	 * 部门service
	 */
	@Resource
	private DeparmentService deparmentService;

	@Resource
	ProjectService projectService;
	
	/**
	 * 获取我的工作集合 
	 * @return
	 */
	public String getAjaxWorkList(){
		
		processAjaxPagerRequestParameter();
		Admin loginAdmin = getLoginAdmin(); //获取登录账户
		//查询条件
		Map<String,Object> param = new HashMap<String,Object>();//精确查询map
		Map<String,Object> params = new HashMap<String,Object>();//模糊查询map
		String status = this.getRequest().getParameter("statu");//获取状态
		if(StringUtils.isNotEmpty(status)){
			if(WorkFlowContants.WORK_CHECK.equals(status)){//查询我的审核
				param.put("isComplete", WorkFlowContants.WAIT);//待办状态
				params.put("currentId", loginAdmin.getId());//模糊查询审批人
			}else if(WorkFlowContants.WAIT.equals(status) || WorkFlowContants.COMPLETE.equals(status) || WorkFlowContants.HAS_CHECK.equals(status)){//查询我的工作            
				if(WorkFlowContants.HAS_CHECK.equals(status)){
					params.put("checkPerson", loginAdmin.getId());
				}else{
					param.put("isComplete", status);
					param.put("admin.id",loginAdmin.getId());
				}
				
			}else if(WorkFlowContants.WORK_QUERY.equals(status)){
				pager.setAdminId(loginAdmin.getId());
				pager.setCurrentId(loginAdmin.getId());
			}
		}else{
			param.put("isComplete", WorkFlowContants.WAIT);//待办状态
			param.put("admin.id",loginAdmin.getId());
		}
		
		//pager.setFlowList(CategoryContants.REQUIREMENT_APPROVE_ARRAY);//设置流程集合
		//pager.setFlowType(WorkFlowContants.NORMAL_FLOW);//正常流程类型
	
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("rows", getWorkData(param,params));
        map.put("total", pager.getTotalCount()); 
      
		return ajax(map);
		
	}
	
	
	public JSONArray getWorkData(Map<String,Object> param,Map<String,Object> params){
		String spell = this.getRequest().getParameter("spell");//拼音首字母
		String flow = this.getRequest().getParameter("flow");//流程id
		String type = this.getRequest().getParameter("type");//流程类型id
		String workStatu = this.getRequest().getParameter("workStatu");//工作状态
		String dateStatu = this.getRequest().getParameter("dateStatu");//日期选择
		String beginDate = this.getRequest().getParameter("beginDate");//开始日期
		String endDate = this.getRequest().getParameter("endDate");//结束日期
		String queryType = this.getRequest().getParameter("queryType");//查询类型
		String queryText = this.getRequest().getParameter("queryText");//查询关键字
		
		if(StringUtils.isNotEmpty(spell)){//首字母查询
			if(!"ALL".equals(spell)){
				params.put("wokrCode", spell.toLowerCase());
			}
		}
		
		if(StringUtils.isNotEmpty(flow) && !"0".equals(flow)){
			param.put("flowId", flow);//流程id
		}
		if(StringUtils.isNotEmpty(type) && !"0".equals(type)){
			param.put("flowTypeId", type);//流程类型id
		}
		if(StringUtils.isNotEmpty(workStatu) && !"0".equals(workStatu)){
			param.put("status", workStatu);//状态
		}
		//日期选择
		if(StringUtils.isNotEmpty(dateStatu) ){
			if("0".equals(dateStatu)){//创建时间
				pager.setDateType("createDate");
				if(StringUtils.isNotEmpty(beginDate)){
					pager.setBeginDate(beginDate);
				}
				if(StringUtils.isNotEmpty(endDate)){
					pager.setEndDate(endDate);
				}
			}else if("1".equals(dateStatu)){//修改时间
				pager.setDateType("modifyDate");
				
				if(StringUtils.isNotEmpty(beginDate)){
					pager.setBeginDate(beginDate);
				}
				if(StringUtils.isNotEmpty(endDate)){
					pager.setEndDate(endDate);
				}
			}
		}
		
		//查询字段
		if(StringUtils.isNotEmpty(queryType)){
			if("0".equals(queryType)){//工作名称
				pager.setSearchBy("workName");
			}else if("1".equals(queryType)){//发起人
				pager.setSearchBy("admin.name");
			}else if("2".equals(queryType)){//按内容
				pager.setSearchBy("formContent");
			}else if("3".equals(queryType)){//按最后审批人
				pager.setSearchBy("lastAdmin.name");
			}
		}
		
		//查询内容
		if(StringUtils.isNotEmpty(queryText)){
			/*try {
				//pager.setKeyword(new String(queryText.getBytes("ISO-8859-1"),"UTF-8").trim());
				
			} catch (UnsupportedEncodingException e) {
				
			}*/
			pager.setKeyword(queryText);
		}
		
		pager.setNumberParam(param);
		pager.setParam(params);
		pager = workService.findPager(pager);
		
		
		List<Work> alist = (List<Work>) pager.getResult();
		
			
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			Work temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId());
	        if(temp.getCurrentPointName() != null){
	        	map.put("pointName", temp.getCurrentPointName());
	        }else{
	        	map.put("pointName", "");
	        }
	        if(StringUtils.isEmpty(temp.getStatus())){
	        	map.put("status", "");
	        	map.put("jsStatus", "0");
	        	map.put("endDate","-");
	        }else{
	        	map.put("jsStatus", temp.getStatus());
	        	if(temp.getStatus().equals("1")){
	        		map.put("status", "取消");
	        		map.put("endDate", DateUtils.formatDateTime(temp.getEndDate()));
	        	}else if(temp.getStatus().equals("2")){
	        		map.put("status", "正常结束");
	     	        map.put("pointName", "-");
	     	       map.put("endDate", DateUtils.formatDateTime(temp.getEndDate()));
	        	}else if(temp.getStatus().equals("3")){
	        		map.put("status", "待"+temp.getCurrentAdminName()+"审批");
	        		map.put("endDate","-");
	        	}else if(temp.getStatus().equals("4")){
	        		map.put("status", "被撤回");
	        		map.put("endDate","-");
	        	}else{
	        		map.put("status", "");
	        		map.put("endDate","-");
	        	}
	        }
	        if(StringUtils.isNotEmpty(temp.getFlowName())){
	        	 map.put("flowName", temp.getFlowName());//流程名称
	        }else{
	        	 map.put("flowName", "");//流程名称
	        }
	     
	       
	       
	        if(temp.getAdmin() != null){
	        	 map.put("name", temp.getAdmin().getName());//流程发起人
	        	 map.put("adminId", temp.getAdmin().getId());//流程发起人id
	        }else{
	        	 map.put("name", "");//流程发起人
	        	 map.put("adminId", "");//流程发起人id
	        }
	        map.put("currentId", temp.getCurrentId() == null ? "" : temp.getCurrentId());//处理人id
	        map.put("workName", temp.getWorkName());
	        map.put("internal", temp.getIsInternal() == null ? "" : temp.getIsInternal());
	       map.put("createDate", DateUtils.formatDateTime(temp.getCreateDate()));
	        if(temp.getLastAdmin() != null){
	        	 map.put("lastName", temp.getLastAdmin().getName());//最后审批人
	        }else{
	        	 map.put("lastName", "");//最后审批人
	        }
	       
	        jsonObj.add(map);
		}
		return jsonObj;
	}
	
	
	/**
	 * 获取正在进行中的工作集合
	 * @return
	 */
	public String getProcessAjaxWorkList(){
		
		processAjaxPagerRequestParameter();
		//查询条件
		Map<String,Object> param = new HashMap<String,Object>();//精确查询map
		Map<String,Object> params = new HashMap<String,Object>();//模糊查询map
		param.put("isComplete", WorkFlowContants.WAIT);//待办状态
		
        Map<String,Object> map = new HashMap<String,Object>(); 
       
        map.put("rows", getWorkData(param,params)); 
        map.put("total", pager.getTotalCount()); 
		return ajax(map);
		
	}
    
	/**
	 * 获取工作流类型的联动数据
	 * @return
	 */
	public String getAjaxDataByWorkFlowId(){
		 Map<String,Object> map = new HashMap<String,Object>(); //json数据集合
		if(StringUtils.isNotEmpty(flowTypeId)){
	      WorkFlowType flowType = workFlowTypeService.get(flowTypeId);
	      List<WorkFlow> workFlowList = new ArrayList<WorkFlow>(flowType.getWorkFlowSet());

	      
	      // 
	       if(workFlowList != null && workFlowList.size() > 0){
	    	   
	    	   JSONArray jsonObj1 = new JSONArray();
		  		for(int i = 0; i < workFlowList.size(); i++ ){
		  			WorkFlow temp = workFlowList.get(i);
		  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
		  	        
		  	        map1.put("id", temp.getId()); 
		  	        map1.put("flowName", temp.getFlowName());
		  	        if(StringUtils.isNotEmpty(temp.getIsDelete())){
		  	        	 map1.put("isDelete", temp.getIsDelete());//是否删除
		  	        }else{
		  	        	 map1.put("isDelete", "");//是否删除
		  	        }
		  	       
                      
		  	        jsonObj1.add(map1);
		  		}
	    	   map.put("workFlowList", jsonObj1);//流程集合
	    	   
	    	    //获取第一个流程的表单的内容 表单如果不存在默认空
	    	   if(workFlowList.get(0).getFlowForm() != null){
	    		   String str="";
         	               if(StringUtils.isNotEmpty(workFlowList.get(0).getFlowForm().getFormContent())){
         		              str=workFlowList.get(0).getFlowForm().getFormContent().replace("nowrap=\"nowrap\"","");//去掉表格样式
         		              map.put("content", str);
         	              }else{
         	            	 map.put("content", workFlowList.get(0).getFlowForm().getFormContent());
         	               }
	    		   
	    	   }else{
	    		   map.put("content", "");
	    	   }
	    	 
	    	  
	    	   Set<WorkFlowPoint> pointShowList = workFlowList.get(0).getWorkFlowPointSet();//获取流程下所有节点
	    	   if(pointShowList != null && pointShowList.size() > 0){
	    		  JSONArray jsonObj2 = new JSONArray();
	    		  for( WorkFlowPoint temp : pointShowList){
	    			  Map<String,Object> map1 = new HashMap<String,Object>(); 
			  	        map1.put("isBranch", temp.getIsBranch());
			  	      if(StringUtils.isNotEmpty(temp.getUserId())){
			  	    	 map1.put("userId", temp.getUserId());
			  	      }else{
			  	    	 map1.put("userId", "");
			  	      }
			  	     
			  	        if(StringUtils.isNotEmpty(temp.getSearchName())){
			  	          map1.put("searchName", temp.getSearchName());
			  	        }else{
			  	          map1.put("searchName", "");
			  	        }
			  	        if(StringUtils.isNotEmpty(temp.getUserName())){
			  	        	map1.put("userName", temp.getUserName());
			  	        }else{
			  	        	map1.put("userName", "");
			  	        }
			  	       if(StringUtils.isNotEmpty(temp.getShowCondition())){
			  	    	 map1.put("showCondition", temp.getShowCondition());
		  	           }else{
		  	        	 map1.put("showCondition", "");
		  	           }
			  	        map1.put("id", temp.getId());
			  	        if(StringUtils.isNotEmpty(temp.getName())){
			  	        	map1.put("name", temp.getName());
			  	        }else{
			  	        	map1.put("name", "");
			  	        }
			  	        
			  	        jsonObj2.add(map1);
	    		  }
	    		  map.put("pointShowList", jsonObj2);
	    	  }else{
	    		  map.put("pointShowList", "");//流程图节点
	    	  }
	    	  
	    	  
	    	  //获取开始节点指定的节点集合
	    	  List<WorkFlowPoint> pointList = workFlowPointService.getFlowPointList(workFlowList.get(0).getId());

	    	  
	    	  if(pointList != null && pointList.size() > 0){
	    		  
	    		  JSONArray jsonObj2 = new JSONArray();
			  		for(int i = 0; i < pointList.size(); i++ ){
			  			WorkFlowPoint temp = pointList.get(i);
			  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
			  	        
			  	        map1.put("id", temp.getId()); 
			  	        map1.put("pointName", temp.getPointName());

			  	        jsonObj2.add(map1);
			  		}
	    		  
	    		  
		    	  List<Admin> adminList = new ArrayList<Admin>(pointList.get(0).getWorkFlowSet());
    			  
				  JSONArray jsonObj = new JSONArray();
			  		for(int i = 0; i < adminList.size(); i++ ){
			  			Admin temp = adminList.get(i);
			  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
			  	        
			  	        map1.put("id", temp.getId()); 
			  	        map1.put("name", temp.getName());

			  	        jsonObj.add(map1);
			  		}
	    		  map.put("pointList", jsonObj2);  //节点集合
	    		  map.put("adminList", jsonObj);//列表集合
	    	  }else{
	    		  map.put("adminList", ""); //列表集合
	    		  map.put("pointList", ""); //节点集合
	    		  
	    	  }
	    	   
	       }else{
	    	      map.put("workFlowList", "");//流程集合
				  map.put("pointList", ""); //节点集合
	    		  map.put("adminList", ""); //列表集合
	    		  map.put("content", "");
	    		  map.put("pointShowList", "");//流程图节点
	       } 
	       
	      
		}else{
			  map.put("workFlowList", "");//流程集合
			  map.put("pointList", ""); //节点集合
    		  map.put("adminList", ""); //列表集合
    		  map.put("content", "");
    		  map.put("pointShowList", "");//流程图节点
		}
          
		return ajax(map);	
	}
	
	
	/**
	 * 获取驳回ajax数据
	 * @return
	 */
	public String getAjaxData(){
		String  workFlowId = this.getRequest().getParameter("workFlowId");//获取工作流程id
		String  flowPointId = this.getRequest().getParameter("flowPointId");//获取工作流节点id
		 Map<String,Object> map = new HashMap<String,Object>(); //json数据集合
		if(StringUtils.isNotEmpty(flowTypeId)){
	      WorkFlowType flowType = workFlowTypeService.get(flowTypeId);
	      List<WorkFlow> workFlowList = new ArrayList<WorkFlow>(flowType.getWorkFlowSet());

	      
	      // 
	       if(workFlowList != null && workFlowList.size() > 0){
	    	   
	    	   JSONArray jsonObj1 = new JSONArray();
		  		for(int i = 0; i < workFlowList.size(); i++ ){
		  			WorkFlow temp = workFlowList.get(i);
		  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
		  	        
		  	        map1.put("id", temp.getId()); 
		  	        map1.put("flowName", temp.getFlowName());

		  	        jsonObj1.add(map1);
		  		}
	    	   map.put("workFlowList", jsonObj1);//流程集合
	    	   
	    	/*    //获取第一个流程的表单的内容 表单如果不存在默认空
	    	   if(workFlowList.get(0).getFlowForm() != null){
	    		   map.put("content", workFlowList.get(0).getFlowForm().getFormContent());
	    	   }else{
	    		   map.put("content", "");
	    	   }*/
	    	   WorkFlow  workFlow = null;
	    	   if(StringUtils.isNotEmpty(workFlowId)){//工作流程id
	    		   workFlow = workFlowService.get(workFlowId);//获取系统默认的工作流流程
//	               if(workFlow != null){
//	            	   String str="";
//	            	      if(StringUtils.isNotEmpty(workFlow.getFlowForm().getFormContent())){
//	            		     str=workFlow.getFlowForm().getFormContent().replace("nowrap=\"nowrap\"","");//去掉表格样式
//	            		     map.put("content", str);
//	            	     }else{
//	            	         map.put("content", workFlow.getFlowForm().getFormContent());
//	            	     }
//	               }else{
//	            	   map.put("content", "");
//	               }
	    		   
	    	   }else{
//	    		   map.put("content", "");
	    	   }
	    	  Work work =  workService.get(id);
   		   	map.put("content", work.getFormContent());
	    	   //获取开始节点指定的节点集合
	    	  List<WorkFlowPoint> pointList = null;
	    	   if(StringUtils.isNotEmpty(flowPointId)){//当前节点不为空
	    		   WorkFlowPoint tempPoint = workFlowPointService.get(flowPointId);//获取当前节点
	    		    //如果能获取到节点对象
	    		   if(tempPoint != null){
	    			   pointList =  workFlowPointService.getPointListByNextPoint(tempPoint.getNextPonit(),workFlow.getId());
	    		    }
	    	   }else{
	    		   pointList =  workFlowPointService.getFlowPointList(workFlow.getId());
	    	   }

	    	  if(pointList != null && pointList.size() > 0){
	    		  
	    		  JSONArray jsonObj2 = new JSONArray();
			  		for(int i = 0; i < pointList.size(); i++ ){
			  			WorkFlowPoint temp = pointList.get(i);
			  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
			  	        
			  	        map1.put("id", temp.getId()); 
			  	        map1.put("pointName", temp.getPointName());

			  	        jsonObj2.add(map1);
			  		}
	    		  
	    		  
		    	  List<Admin> adminList = new ArrayList<Admin>(pointList.get(0).getWorkFlowSet());
    			  
				  JSONArray jsonObj = new JSONArray();
			  		for(int i = 0; i < adminList.size(); i++ ){
			  			Admin temp = adminList.get(i);
			  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
			  	        
			  	        map1.put("id", temp.getId()); 
			  	        map1.put("name", temp.getName());

			  	        jsonObj.add(map1);
			  		}
	    		  map.put("pointList", jsonObj2);  //节点集合
	    		  map.put("adminList", jsonObj);//列表集合
	    	  }else{
	    		  map.put("adminList", ""); //列表集合
	    		  map.put("pointList", ""); //节点集合
	    		  
	    	  }
	    	   
	       }else{
	    	      map.put("workFlowList", "");//流程集合
				  map.put("pointList", ""); //节点集合
	    		  map.put("adminList", ""); //列表集合
	    		  map.put("content", "");
	       } 
	       
	      
		}else{
			  map.put("workFlowList", "");//流程集合
			  map.put("pointList", ""); //节点集合
    		  map.put("adminList", ""); //列表集合
    		  map.put("content", "");
		}
          
		return ajax(map);	
	}
	
	
	
	
	  /**
	   * 根据版本、工作流获取工作流
	   * @return
	   */
		public String getWorkFlowPointByFlowId(){	   
			String version = this.getRequest().getParameter("version");
			return ajax(workService.getAjaxWorkFlowDataByWorkFlowId(flowTypeId,Long.parseLong(version)));	
		}
		
		/**
		 * 获取工作流表单的联动数据
		 * @return
		 */
		public String getAjaxWorkFlowDataByWorkFlowId(){	   
			return ajax(workService.getAjaxWorkFlowDataByWorkFlowId(flowTypeId));	
		}
	

		/*
		 * 编辑开发工作
		 */
		public String editDevelopmentWork(){
			if(StringUtils.isNotEmpty(id)){
				work = workService.get(id);
			}else{
				work = new Work();
			}
			this.getRequest().setAttribute("isEdit", true);
			WorkFlowPoint tempPoint = null;
			if(StringUtils.isNotEmpty(work.getCurrentPointId())){
				tempPoint = workFlowPointService.get(work.getCurrentPointId(),work.getVersion());
			}
			 String flowId =  workFlowService.getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
			 
			 List<WorkFlowPoint> flowPoint = null;
			 if(tempPoint == null){
				 flowPoint = workFlowPointService. getFlowPointList(flowId);//获取首节点
			 }else{
				 flowPoint =	workFlowPointService.getPointListByNextPoint(tempPoint.getNextPonit(),flowId); //查找下一节点
			 }
		     if(flowPoint != null && flowPoint.size() > 0){
		    	 this.getRequest().setAttribute("flowPoint", flowPoint.get(0));
		     }
		     
		     adminList = adminService.getAllList();
			if(work.getFileName() != null && work.getFilePath() != null){
				String[] fileName = work.getFileName().split(",");//文件名称
				String[] filePath = work.getFilePath().split(",");//文件路径
				List<FileVo> fileList = new ArrayList<FileVo>();
				 for(int i = 0; i < fileName.length; i++){
					 FileVo vo = new FileVo();
					 vo.setFileName(fileName[i]);
					 vo.setFilePath(filePath[i]);
					 fileList.add(vo);
				 }
				 this.getRequest().setAttribute("fileList",fileList); //文件对象
			}
			if(StringUtils.equals(work.getWorkType(),WorkFlowContants.INSIDE_REQUIRE_FLOW) 
					||StringUtils.equals(work.getWorkType(),WorkFlowContants.INSIDE_REQUIRE_FLOW) ){
				
				List<Project> projectList = projectService.getAllList();
				JSONArray belongProjectOpts = new JSONArray();
				for(Project project : projectList){
					JSONObject jobj = new JSONObject();
					jobj.put("id", project.getId());
					jobj.put("text", project.getName());
					jobj.put("name", project.getName());
					belongProjectOpts.add(jobj);
				}
				getRequest().setAttribute("belongProjectOpts", JsonUtil.toJson(belongProjectOpts));
				String expand = work.getExpand();
				if(StringUtils.isNotBlank(expand)){
					Require4workflow require4workflow = JsonUtil.toObject(expand, Require4workflow.class);
					getRequest().setAttribute("belongProject", require4workflow.getProjectId());
				}
			}
			this.pageUrl = "/WEB-INF/template/admin/check_editdevelopmentwork.ftl";
			return PAGEURL;
		}
	

		/**
		 * 获取工作流节点的联动数据
		 * @return
		 */
		public String getAjaxWorkFlowPointDataByWorkFlowId(){
			 Map<String,Object> map = new HashMap<String,Object>(); //json数据集合
			 if(StringUtils.isNotEmpty(flowTypeId)){//工作流节点Id
				 WorkFlowPoint   workFlowPoint = workFlowPointService.get(flowTypeId);
				 if(workFlowPoint != null){
					 List<Admin> adminList = new ArrayList<Admin>(workFlowPoint.getWorkFlowSet());
		   			  
					  JSONArray jsonObj = new JSONArray();
				  		for(int i = 0; i < adminList.size(); i++ ){
				  			Admin temp = adminList.get(i);
				  	        Map<String,Object> map1 = new HashMap<String,Object>(); 
				  	        
				  	        map1.put("id", temp.getId()); 
				  	        map1.put("name", temp.getName());

				  	        jsonObj.add(map1);
				  		}
				  		map.put("adminList", jsonObj);//列表集合
				 }else{
					 map.put("adminList", "");//列表集合
				 }
			 }else{
				 map.put("adminList", "");//列表集合
			 }
			return ajax(map);	
		}
	
		/**
		 * 更新工作审批
		 * @return
		 */
		public String updateWorkCheck(){
			if(StringUtils.isEmpty(work.getId())){
				return ajax(Status.error, "查找的信息不存在!");
			}
			
			Admin loginAdmin = getLoginAdmin(); //获取登录账户
			try{
	        	String str = workService.updateWorkCheck(work, loginAdmin, record,adminList);
	        	if("success".equals(str)){
	      		  	  return ajax(Status.success, "审批通过!");
	      		  	}else if("false".equals(str)) {
	      		  	  return ajax(Status.success, "审批失败!");
	      		  	}else{
	      		  	return ajax(Status.error,str);
	      		  	}
			}catch(PersonalException e){
      			return ajax(Status.error,e.getMessage());
      		}
			catch(Exception e){
				return ajax(Status.error,"审批失败!");
			}
		
			//return ajax(Status.error, "工作名称不能为空!");
		}
		
		
		/**
		 * 取消工作
		 * @return
		 */
		 public String cancleMyWork(){
			 if(StringUtils.isEmpty(id)){
					return ajax(Status.error, "查找的信息不存在!");
				}
			
			   Admin loginAdmin = getLoginAdmin(); //获取登录账户
			 
			 try{
				String str = workService.cancleMyWork(id, loginAdmin);
				if("success".equals(str)){
	      		  	  return ajax(Status.success, "取消成功!");
	      		  	}else{
	      		  	  return ajax(Status.success, "取消失败!");
	      		  	}
				}catch(Exception e){
					return ajax(Status.error, "取消失败!");
				}
			
		 }
		/**
		 * 驳回工作审批
		 * @return
		 */
		public String quitWorkCheck(){
			if(StringUtils.isEmpty(work.getId())){
				return ajax(Status.error, "查找的信息不存在!");
			}
			String type = (String) this.getRequest().getParameter("type");//获取驳回类型
			Admin loginAdmin = getLoginAdmin(); //获取登录账户
			try{
			   String str = workService.quitWorkCheck(work, loginAdmin, record,type);
				if("success".equals(str)){
	      		  	  return ajax(Status.success, "驳回成功!");
	      		  	}else{
	      		  	  return ajax(Status.success, "驳回失败!");
	      		  	}
			}catch(PersonalException e){
      			return ajax(Status.error,e.getMessage());
      		}catch(Exception e){
				return ajax(Status.error, "驳回失败!");
			}
		
			//return ajax(Status.error, "工作名称不能为空!");
		}
		 /**
	  	 * 保存工作流节点
	  	 * @return
	  	 */
	      public String save(){
	      	//判断新增还是修改
	      	if(StringUtils.isEmpty(work.getId())){
	      		return insert();
	      	}else{
	      		return update();
	      	}
	      }
	     
	    
	      /**
	       * 新增工作流节点
	       * @return
	       */
	      public String insert(){
	      	if(StringUtils.isEmpty(work.getWorkName())){
	  			return ajax(Status.error, "工作名称不能为空!");
	  		}
	      	Admin loginAdmin = getLoginAdmin(); //获取登录账户
	      	//检查工作名称是否唯一
	  		List<Work> workList = workService.checkWorkName(work.getWorkName());
	      		 if(workList != null && workList.size() > 0){
	      			return ajax(Status.error, "工作名称已存在!");
	      		  }
	      		 String fieldId = this.getRequest().getParameter("fieldId");
	      		 
	      	  try{
	      		  	workService.insertWork(work, fileName, filePath, fieldId, loginAdmin, adminList);
	      		  logInfo = "新增我的工作: " + work.getWorkName();
	      		  	return ajax(Status.success, "保存成功!");
	      		}catch(PersonalException e){
	      			return ajax(Status.error,e.getMessage());
	      		}catch(Exception e){
	      			return ajax(Status.error,"保存失败!");
	      		}
	  		
	      }
	     
	       
	      /**
	       * 编辑工作流节点
	       * @return
	       */
	      public String update(){
	      	if(StringUtils.isEmpty(work.getWorkName())){
	  			return ajax(Status.error, "工作名称不能为空!");
	  		}
	      	
	      //检查工作名称是否唯一
	  		List<Work> workList = workService.checkWorkName(work.getWorkName());
	      	Work  temp = workService.get(work.getId());//获取工作
   		     if(workList != null && workList.size() > 0){
   			 for(Work type : workList){
   				 //如果id 不相等
   				 if(!temp.getId().equals(type.getId())){
   					 return ajax(Status.error, "工作名称已存在!");
   				 	}
   			 	}
     		  }
   		    Admin loginAdmin = this.getLoginAdmin();
	      		try{
	      			String str = workService.updateWork(work, temp, adminList,loginAdmin,fileName, filePath);
	      			logInfo = "修改我的工作: " + work.getWorkName();
		      		return ajax(Status.success, "保存成功!");
		      		
	      		}catch(PersonalException e){
	      			return ajax(Status.error,e.getMessage());
	      		}catch(Exception e){
	      			return ajax(Status.error, "保存失败!");
	      		}
	  		
	      }
	   
	    
		      
		  /**
			  	 * 我的进展工作
			  	 * @return
			  	 */
			public String getMyProcessWork(){
			  return "myProcessWork";
			} 
	      
	      /**
	  	 * 我的工作
	  	 * @return
	  	 */
	  	public String getMyWork(){
	  		return "myWork";
	  	}
	  	
	  	/**
	  	 * 工作审批
	  	 * @return
	  	 */
	  	public String getWorkCheck(){
	  		return "workCheck";
	  	}
	  	
	  	/**
	  	 * 工作查询
	  	 * @return
	  	 */
	  	public String getWorkQuery(){
	  	
	  		return "workQuery";
	  	}

	    
	    	
		
		
		
	/**
	 * 创建我的工作
     */
	public String addWork() {

		work = new Work();
		getRequest().setAttribute("workFlowTree", JsonUtil.toJson(workFlowTypeService.getListWithChild()));
		this.getRequest().setAttribute("isDelete", isDelete);
		this.getRequest().setAttribute("fieldId", fieldId);
		//this.getRequest().setAttribute("flowTypeList", flowTypeList);
		this.getRequest().setAttribute("isEdit", false);
		return INPUT;
	}
	
	
	/**
	 * 编辑我的工作
     */
	public String editWork() {
		if(StringUtils.isNotEmpty(id)){
			work = workService.get(id);
		}else{
			work = new Work();
		}
		return INPUT;
	}
	
	/**
     * 删除我的工作
     * @return
     */
    public String delete(){
    	if(StringUtils.isEmpty(id)){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	Work work = workService.get(id);
    	if(work == null){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	try{
    		workService.delete(id);
    		this.logInfo = "删除我的工作：" + work.getWorkName();
    		return ajax(Status.success, "删除成功!");
    	}catch(Exception e){
    		return ajax(Status.error, "流程被引用，不能删除!");
    	}
    	
    }
    
    
    
    /*
	 * 编辑工作审批
	 */
	public String editWorkCheck(){
		if(StringUtils.isNotEmpty(id)){
			work = workService.get(id);
			List<ApprovalRecord> recordList =  new ArrayList<ApprovalRecord>(work.getRecord()); 
			this.getRequest().setAttribute("recordList", recordList);
			WorkFlowPoint tempPoint = null;
			if(StringUtils.isNotEmpty(work.getCurrentPointId())){
				tempPoint = workFlowPointService.get(work.getCurrentPointId(),work.getVersion());
			}
			if(tempPoint ==  null){
				tempPoint = new WorkFlowPoint();
			}
			 String flowId =  workFlowService.getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
		     List<WorkFlowPoint> flowPoint =	workFlowPointService.getPointListByNextPoint(tempPoint.getNextPonit(),flowId); //查找下一节点
		     if(flowPoint != null && flowPoint.size() > 0){
		    	 this.getRequest().setAttribute("flowPoint", flowPoint.get(0));
		     }
		     
		}else{
			work = new Work();
		}
		   adminList = adminService.getAllList();
		if(work.getFileName() != null && work.getFilePath() != null){
			String[] fileName = work.getFileName().split(",");//文件名称
			String[] filePath = work.getFilePath().split(",");//文件路径
			List<FileVo> fileList = new ArrayList<FileVo>();
			 for(int i = 0; i < fileName.length; i++){
				 FileVo vo = new FileVo();
				 vo.setFileName(fileName[i]);
				 vo.setFilePath(filePath[i]);
				 fileList.add(vo);
			 }
			 this.getRequest().setAttribute("fileList",fileList); //文件对象
		}
		this.getRequest().setAttribute("work", work);
		this.getRequest().setAttribute("admin", getLoginAdmin()); //获取登录账户);
		return "editworkCheck";
	}
	
	
	/*
	 * 查看我的工作
	 */
	public String viewMyWork(){
		if(StringUtils.isNotEmpty(id)){
			work = workService.get(id);
			if(work!=null && StringUtils.isNotEmpty(work.getFormContent())){
				String fc=work.getFormContent().replace("nowrap=\"nowrap\"", "");
				work.setFormContent(fc);
			}
			List<ApprovalRecord> recordList =  new ArrayList<ApprovalRecord>(work.getRecord()); 
			this.getRequest().setAttribute("recordList", recordList);
			
		}else{
			work = new Work();
		}
		 adminList = adminService.getAllList();
		if(work.getFileName() != null && work.getFilePath() != null){
			String[] fileName = work.getFileName().split(",");//文件名称
			String[] filePath = work.getFilePath().split(",");//文件路径
			List<FileVo> fileList = new ArrayList<FileVo>();
			 for(int i = 0; i < fileName.length; i++){
				 FileVo vo = new FileVo();
				 vo.setFileName(fileName[i]);
				 vo.setFilePath(filePath[i]);
				 fileList.add(vo);
			 }
			 this.getRequest().setAttribute("fileList",fileList); //文件对象
		}
		this.getRequest().setAttribute("admin", getLoginAdmin()); //获取登录账户);
		return  "viewmywork";
	}
	
	
	/*
	 * 编辑我的工作
	 */
	public String editMyWork(){
		if(StringUtils.isNotEmpty(id)){
			work = workService.get(id);
		}else{
			work = new Work();
		}
		this.getRequest().setAttribute("isEdit", true);
		WorkFlowPoint tempPoint = null;
		if(StringUtils.isNotEmpty(work.getCurrentPointId())){
			tempPoint = workFlowPointService.get(work.getCurrentPointId(),work.getVersion());
		}
		 String flowId =  workFlowService.getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
		 
		 List<WorkFlowPoint> flowPoint = null;
		 if(tempPoint == null){
			 flowPoint = workFlowPointService. getFlowPointList(flowId);//获取首节点
		 }else{
			 flowPoint =	workFlowPointService.getPointListByNextPoint(tempPoint.getNextPonit(),flowId); //查找下一节点
		 }
	     if(flowPoint != null && flowPoint.size() > 0){
	    	 this.getRequest().setAttribute("flowPoint", flowPoint.get(0));
	     }
	     

		if(work.getFileName() != null && work.getFilePath() != null){
			String[] fileName = work.getFileName().split(",");//文件名称
			String[] filePath = work.getFilePath().split(",");//文件路径
			List<FileVo> fileList = new ArrayList<FileVo>();
			 for(int i = 0; i < fileName.length; i++){
				 FileVo vo = new FileVo();
				 vo.setFileName(fileName[i]);
				 vo.setFilePath(filePath[i]);
				 fileList.add(vo);
			 }
			 this.getRequest().setAttribute("fileList",fileList); //文件对象
		}
		return "editmywork";
	}
    
    /**
     * 获取工作类型json数据
     * @return
     */
    public String getWorkFlowTypeList(){
    	return ajax(workService.getWorkFlowTypeList());	
    }
    
    /**
     * 获取工作流程json数据
     * @return
     */
    public String getWorkFlowList(){	
		return ajax(workService.getWorkFlowList());	
    }
	
   
	

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public String getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public ApprovalRecord getRecord() {
		return record;
	}

	public void setRecord(ApprovalRecord record) {
		this.record = record;
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

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}


	
	
	
	
	
	
	
	
	
	

}