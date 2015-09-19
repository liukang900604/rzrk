package com.rzrk.service.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rzrk.bean.Pager;
import com.rzrk.common.contract.ContractParseManager;
import com.rzrk.common.contract.ContractParser;
import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.UserPlanRequireDao;
import com.rzrk.dao.WorkContractRecordDao;
import com.rzrk.dao.WorkDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ApprovalRecord;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Project;
import com.rzrk.entity.SystemMessage;
import com.rzrk.entity.SystemMessage.RedType;
import com.rzrk.entity.SystemMessage.SystemmessageType;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkContractRecord;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.entity.WorkFlowType;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.JobService;
import com.rzrk.service.MailService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.SmsService;
import com.rzrk.service.SystemMessageService;
import com.rzrk.service.WorkFlowPointService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.service.WorkFlowTypeService;
import com.rzrk.service.WorkService;
import com.rzrk.util.CnSpellUtils;
import com.rzrk.util.DateUtil;
import com.rzrk.util.GenerateUtil;
import com.rzrk.util.HttpClientUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.SettingUtil;
import com.rzrk.util.StrUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.contract.ContractEntity;
import com.rzrk.vo.workflow.Project4workflow;
import com.rzrk.vo.workflow.Require4workflow;

@Transactional
@Service
public class WorkServiceImpl extends BaseServiceImpl<Work, String> implements WorkService {
//    private static final AtomicLong ATOMIC_LONG = new AtomicLong();
    
    @Resource
    ProjectService projectService;
    
	@Resource
	private WorkDao workDao;
	@Resource
	private ContractDao contractDao;
	@Resource
	private UserPlanRequireDao userPlanRequireDao;
	
	@Resource(name = "workDaoImpl")
	public void setBaseDao(WorkDao workDao) {
		super.setBaseDao(workDao);
	}
	
	@Resource
	private  ContractService  contractService;
	
	@Resource
	private  WorkFlowPointService  flowPointService;
	
	@Resource
	private JobService  jobService;
	
	/**
	 * 人员service
	 */
	@Resource
	private AdminService adminService;
	
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
	 * 工作流流程类型service
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
	@Resource
	private ContractCategoryService contractCategoryService;
	
	/**
	 * 部门service
	 */
	@Resource
	private DeparmentService deparmentService;
	@Resource
	ContractParseManager contractParseManager;
	@Resource
	WorkContractRecordDao workContractRecordDao;
	/**
	 * 检查我的工作名称是否唯一
	 * @return
	 */
	public List<Work> checkWorkName(String workName) {
		String hql = "from Work where workName =:workName";
		//获取我的工作任务
		return this.getBaseDao().getSession().createQuery(hql).setParameter("workName", workName).list();
	}

	
	/**
	 * 删除字段数据
	 * @param fieldList
	 */
	public void deleteContartField(Set<ContractField> fieldList,String workId){
		//String sql = "delete from rzrk_rzrk_work_contract_field where rzrk_work_id =:workId";
		//this.getBaseDao().getSession().createSQLQuery(sql).setParameter("workId", workId).executeUpdate();
		for(ContractField field :fieldList){
			contractService.delete(field);
		}
	}
	
	
	/**
	 * 删除工作流节点
	 */
	public void deleteWorkFlowId(Set<WorkFlowPoint> pointList){
		for(WorkFlowPoint point :pointList){
			flowPointService.delete(point);
		}
	}
	
	
	/**
	 * 取消我的工作
	 * @param id
	 * @param loginAdmin
	 * @return
	 */
	public String cancleMyWork(String id,Admin loginAdmin){
		
		   Work temp = this.get(id);//获取数据库最新对象
			 try{
				 
					updateSysMessage("尊敬的"+temp.getCurrentPointName()+",您的待处理工作【"+temp.getWorkName()+"】已被取消。",temp.getId(),temp.getCurrentPointId(),loginAdmin);
					temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
					temp.setEndDate(new Date(System.currentTimeMillis()));//结束时间
					temp.setCurrentId("");//清空处理人名Id
					temp.setCurrentAdminName("");//清空处理人
					temp.setIsComplete(WorkFlowContants.COMPLETE);//完成
					temp.setStatus(WorkFlowContants.QUIT_STATU);//取消;
					if(!(temp.getIsDelete() != null && (WorkFlowContants.DELETE.equals(temp.getIsDelete()) ||  WorkFlowContants.UPDATE.equals(temp.getIsDelete())))){//不需要删除、更新
						for(ContractField field : temp.getFieldSet()){//更换状态
							field.setStatu(WorkFlowContants.CANCEL);
						}
					}
				
					removeRelationWorkForDefined(temp);
					this.update(temp);
					List<Admin> adminList = new ArrayList<Admin>();
					adminList.add(temp.getAdmin());//接收人就是自己
					saveSysMessage("工作取消","尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】被取消。",loginAdmin,adminList,null,null,null);
					sendMailAndSms(temp,adminList,"尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】被取消。","工作取消");
					return "success";
				}catch(Exception e){
					return "false";
				}
		
	}
	/**
	 * 驳回我的工作
	 * @param work
	 * @param loginAdmin
	 * @param record
	 * @return
	 */
	public String quitWorkCheck(Work work,Admin loginAdmin, ApprovalRecord record,String type){
		Work temp = this.get(work.getId());//获取数据库最新对象
		if(StringUtils.isNotEmpty(temp.getCurrentId())){//验证审批权限
    		if(temp.getCurrentId().indexOf(loginAdmin.getId()) == -1){
    			throw new PersonalException("工作已处理!");
    		}
    	}else{
    		throw new PersonalException("工作已处理!");
    	}
		temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
		temp.setLastAdmin(loginAdmin);//最后一次操作人
		temp.setLastAdminName(loginAdmin.getName());//最后操作人名称
		temp.setCurrentId("");//清空处理人名Id
		temp.setCurrentAdminName("");//清空处理人
		record.setPointName(temp.getCurrentPointName());//审签节点名称
		if(StringUtils.isNotEmpty(type) && "1".equals(type)){//驳回上一节点
			if(StringUtils.isNotEmpty(temp.getLastPointId())){
				WorkFlowPoint lastPoint =  workFlowPointService.get(temp.getLastPointId(),work.getVersion());
				temp.setCurrentPointName(lastPoint.getPointName());
			    temp.setCurrentPointId(lastPoint.getId());
			}else{
				temp.setCurrentPointName(null);
				temp.setCurrentPointId(null);
				temp.setLastPointId(null);
				temp.setLastPointName(null);
			}
			
		}else{
			temp.setCheckSort(null);
			temp.setCurrentPointName(null);
			temp.setCurrentPointId(null);
			temp.setLastPointId(null);
			temp.setLastPointName(null);
		}
		
		temp.setIsComplete(WorkFlowContants.WAIT);//待办
		temp.setStatus(WorkFlowContants.BACK_STATU);//驳回
		record.setApprovalPerson(loginAdmin.getName());//审批人
		record.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
		record.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
		record.setStatus("驳回");
		record.setWork(temp);
		Set<ApprovalRecord> tempArray = new HashSet<ApprovalRecord>();
		tempArray.add(record);
		temp.setRecord(tempArray);
		temp.getRecord().add(record);
		try{
		
				if(!(temp.getIsDelete() != null && (WorkFlowContants.DELETE.equals(temp.getIsDelete()) ||  WorkFlowContants.UPDATE.equals(temp.getIsDelete())))){//不需要删除、更新
					for(ContractField field : temp.getFieldSet()){//更换状态
						field.setStatu(WorkFlowContants.BACK);
					}
				}
			this.update(temp);
			List<Admin> adminList = new ArrayList<Admin>();
			adminList.add(temp.getAdmin());//接收人就是自己
			saveSysMessage("工作审批结果","尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】被驳回。",loginAdmin,adminList,temp.getId(),null,temp.getIsInternal());
			sendMailAndSms(temp,adminList,"尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】被驳回。","工作审批结果");
			return   "success";
		}catch(Exception e){
			return "false";
		}
	}
	
	
	
	
	/**
	 * 保存我的工作
	 * @param work
	 * @param fileName
	 * @param filePath
	 * @param fieldId
	 * @param loginAdmin
	 * @param adminList
	 * @return
	 * @throws Exception 
	 */
	public String insertWork(Work work,String[]  fileName,String[]  filePath,String fieldId,Admin loginAdmin,List<Admin>  adminList){
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
 				 work.setFileName(fileNameBuffer.substring(0,fileNameBuffer.length()-1).toString());//文件名
 				 work.setFilePath(filePathBuffer.substring(0,filePathBuffer.length()-1).toString());//文件路径
 			 }
 		 }
 		 
 		 WorkFlow  tempWorkFlow = work.getWorkFlow();
			if(tempWorkFlow != null && StringUtils.isNotEmpty(tempWorkFlow.getId())){
				tempWorkFlow = workFlowService.get(tempWorkFlow.getId());
				if(tempWorkFlow == null){
					throw new PersonalException("所选流程未定义!");
				}
				work.setFlowId(tempWorkFlow.getId());//流程id
				work.setFlowName(tempWorkFlow.getFlowName());//流程名称
				work.setVersion(tempWorkFlow.getVersion());//设置最新流程版本
			}
			
			WorkFlowType  tempWorkFlowType = work.getFlowType();
			if(tempWorkFlowType != null && StringUtils.isNotEmpty(tempWorkFlowType.getId())){
				tempWorkFlowType = workFlowTypeService.get(tempWorkFlowType.getId());
				if(tempWorkFlowType == null){
					throw new PersonalException("所选流程类型未定义!");
				}
				work.setFlowTypeId(tempWorkFlowType.getId());//流程类型id
				work.setFlowTypeName(tempWorkFlowType.getTypeName());//流程类型名称
			}
 	      
			
      		if(work.getIsDelete() != null && (WorkFlowContants.DELETE.equals(work.getIsDelete()) ||  WorkFlowContants.UPDATE.equals(work.getIsDelete()))){//是否删除、更新
      			//删除
      			 if(StringUtils.isNotEmpty(fieldId)){
	      			 String [] field = fieldId.trim().split(",");
	      			 for(int i = 0; i < field.length; i++){
	      			 ContractField conField = contractService.get(field[i]);
	      			 if(conField != null){
	      				work.getFieldSet().add(conField);
	      			
	      			  }
	      			 }
	      		 }
      		}else{
      			if(StringUtils.isNotEmpty(fieldId)){
	      			 String [] field = fieldId.trim().split(",");
	      			 for(int i = 0; i < field.length; i++){
	      			 ContractField conField = contractService.get(field[i]);
	      			 if(conField != null){
	      				work.getFieldSet().add(conField);
	      				conField.setStatu(WorkFlowContants.RELATION);//关联工作流
	      			  }
	      			 }
	      		 }
      		}
 		
 	
 		    boolean  status = false; //是否跳过下一节点
 			work.setAdmin(loginAdmin);//流程发起人
 			work.setStatus(WorkFlowContants.CHECK_STATU);//待审批状态
 			work.setIsComplete(WorkFlowContants.WAIT);//代办状态
 			
 			List<Admin> recepList = new ArrayList<Admin>();//接收人
 			WorkFlowPoint tempPoint = null;//当前节点
 			
 			
 				if( StringUtils.isNotEmpty(work.getCurrentPointId())){
 					tempPoint =	workFlowPointService.get(work.getCurrentPointId(),work.getVersion());//获取工作流节点
 				 	if(tempPoint == null){
 				 		throw new PersonalException("流程未定义节点!");
 				 	}
 				  work.setCurrentPointName(tempPoint.getPointName());//流程名称
 					if(tempPoint != null){
 						
 						if(WorkFlowContants.UNBRANCH.equals(tempPoint.getIsBranch())){//如果为普通节点
 							
 							if(tempPoint.getWorkFlowSet() != null && tempPoint.getWorkFlowSet().size() > 0){
 								 adminList = new ArrayList<Admin>(tempPoint.getWorkFlowSet());//节点观看人
 								recepList.addAll(adminList);//通知人
 	 		 				 	if(StringUtils.isNotEmpty(tempPoint.getSearchName())){
 	 		 				 	status =  getAdminByKeyName(tempPoint.getSearchName(), recepList,work.getAdmin(),work.getFormContent(),loginAdmin);
 	 		 				 	}
 	 		 				 	if(!status){
 	 		 				 		setWorkName(work, recepList);//设置审批人名称
 	 		 				 	}
 	 		 				 
 							}else{
 								//关键词
 								if(StringUtils.isEmpty(tempPoint.getSearchName())){
 									throw new PersonalException("下一节点无审批人!");
 								}else{
 									status = getAdminByKeyName(tempPoint.getSearchName(), recepList,work.getAdmin(),work.getFormContent(),loginAdmin);
 									if(!status){
 										 setWorkName(work, recepList);
 									}
 								}
 								
 							}
 							
 		 				}
 				    }else{
 				    	throw new PersonalException("下一节点不存在!");
 				    }
 					
 				}else{
 					throw new PersonalException("流程未定义节点!");
 				}
 				
 			
     			
 		
 			work.setWokrCode(CnSpellUtils.getStringPinYinHead(work.getWorkName()));//获取我的工作字符串首字母
 			work.setCode(CnSpellUtils.getPinYinHeadChar(loginAdmin.getName()));//获取首字母
 			work.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
 			work.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
 				
 				//增加写入二级表	start,如果fieldset为空说明是从流程发起的，如果不为空说明是二级分类录入的
 				if(work.getFieldSet().size()==0){
 	      			String formContent = "<div>"+work.getFormContent()+"</div>";
 	      			Document doc = Jsoup.parse(formContent, "UTF-8");
 	      			Elements cidElem = doc.select(".contractCategory.cid");
 	      			
 	      			if(cidElem.size()>0){
 	      				String cid = cidElem.val();
 	      				String contract_category_id = cidElem.attr("contract_category_id");
 	      				if(StringUtils.isEmpty(cid)){
// 	      					String py = CnSpellUtils.getPinYinHeadChar(work.getWorkName());
// 	      					if(py.length()>4){
// 	      						py = py.substring(0,4);
// 	      					}
// 	      					py = StringUtils.leftPad(py, 4, '0');
// 	      					String primary = String.format("%4s-%s-%04d", py, com.rzrk.util.date.DateUtils.getCurrentDate("yyyyMMddHHmmss"),
// 	      							ATOMIC_LONG.incrementAndGet()%1000);
 	      					String primary = GenerateUtil.createPrimary(contract_category_id);
 	      					cidElem.attr("value",primary);
 	      					String newContent = doc.select("div").eq(0).html();//TODO性能
 	      					work.setFormContent(newContent);
 	      				}
 	      				String contractCategoryId = cidElem.attr("contract_category_id");
 		      			ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
 		      			if(contractCategory == null){
 		      				throw new PersonalException("表单内容对应的二级分类不存在,请重新设计表单!");
 		      			}
 		      			String[] titleArr = contractCategory.getDefinitionObj().getTitles();
 		      			ContractEntity<String, String> entity = getFieldEntity(doc, titleArr);
 		      			saveWithForm(work,contractCategoryId, entity);
 		      			//设置分支用户
 		      			getUserByCondition(work,tempPoint,recepList,loginAdmin);
 	 					
 	      			}else{
 		      			save(work);
 	      			}
 				}else{
		      		save(work);
 				}
 		
 			if(status){//继续找下一节点
 				findLastPoint(work,tempPoint,recepList,loginAdmin);
 				this.update(work);
	 		}
 			
 			String message = null;
 			String subJect = null;
 			if(StringUtils.isNotEmpty(work.getCurrentAdminName())){
 				message = "尊敬的"+work.getCurrentAdminName()+",您有新的工作【"+work.getWorkName()+"】审批,请及时处理。";
 				subJect = "工作审批通知";
 				//新增系统消息
 	 			saveSysMessage("工作审批通知",message,loginAdmin,recepList,work.getId(),work.getCurrentPointId(),null);
 			}else{
 				recepList.clear();
 				recepList.add(work.getAdmin());//接收人就是自己
 				message = "尊敬的"+work.getAdmin().getName()+",您的工作【"+work.getWorkName()+"】已结束。";
 				subJect = "工作审批结果";
 				saveSysMessage("工作审批结果",message,loginAdmin,recepList,null,null,null);
 			}
 			
 			sendMailAndSms(work,recepList,message,subJect);
 			
 			return  "success";
 		
	}
	
	/**
	 * 发送邮件
	 * @param work
	 * @param recepList 邮件接收人
	 * @param message 邮件内容
	 */
	public  void sendMailAndSms(Work work,List<Admin> recepList,String message,String subject){
		//发送短信、邮件
			if(work.getWorkFlow() != null && StringUtils.isNotEmpty(work.getWorkFlow().getId())){
				WorkFlow flow = workFlowService.get(work.getFlowId());
				if(flow != null){
					if(WorkFlowContants.SEND.equals(flow.getIsEmail())){//发送邮件
						try{
							for(Admin admin : recepList){
								if(StringUtils.isNotEmpty(admin.getEmail())){
									mailService.sendMail(subject,message,admin.getEmail());
								}
								
							}
						}catch(Exception e){
							
						}
					}
					/*if(WorkFlowContants.SEND.equals(flow.getIsMsg())){//发送短信
						smsService.sendSmsTest(host, username, password, mobile);
					}*/
				}
				
			}
	}


	private ContractEntity<String, String> getFieldEntity(Document doc, String[] titleArr ) {
		ContractEntity<String, String> entity = new ContractEntity<String, String>();
		for(int i=0;i<titleArr.length;i++){
			String title = titleArr[i];
			Elements titleElem = doc.select(".contractCategory.cfield[field="+title+"]");
			if(titleElem.size()>0){
				String val = titleElem.val();
				if(titleElem.is("input[type=text]")){
					if(titleElem.is(".definition")){//definition新描述
						String builtsign = titleElem.attr("builtsign");
						if(StringUtils.isNotEmpty(builtsign)){
		      				ContractParser parser = contractParseManager.getContractParser(builtsign);
		      				if(parser!=null){
		      					String pval = parser.parse(val);
			   					if(StringUtils.isNotEmpty(val) && StringUtils.isEmpty(pval)){
									throw new PersonalException(title+"解析失败,不存在"+val);
								}else{
									
								}
			      				entity.put("__id__"+title, pval);
		      				}
						}
					}else{//兼容以前的
		  				ContractParser parser = contractParseManager.getContractParser(title);
		  				if(parser!=null){
		  					String pval = parser.parse(val);
		   					if(StringUtils.isNotEmpty(val) && StringUtils.isEmpty(pval)){
								throw new PersonalException(title+"解析失败,不存在"+val);
							}else{
								
							}
		      				entity.put("__id__"+title, pval);
		  				}
					}
				}else if(titleElem.is(".select,.selectTree,.checkbox,.radio")){
					val = titleElem.attr("value");
				}else if(titleElem.is("textarea")){
					val = titleElem.attr("value");
				}
				entity.put(title, val);
			}
		}
		return entity;
	}
	
	
	/**
	 * 设置审批工作者名称、审批工作者id
	 * @param work
	 * @param recepList
	 */
	public void setWorkName(Work work, List<Admin> recepList){
		      StringBuffer currentAdminName = new StringBuffer();
 			 StringBuffer currentAdminId = new StringBuffer();//当前处理人id
 			 for(Admin admin : recepList){
 				Admin tempAdmin = adminService.get(admin.getId());
 				currentAdminName.append(tempAdmin.getName()).append("、");
 				currentAdminId.append(tempAdmin.getId()).append("、");
 			 }
		    work.setCurrentAdminName(currentAdminName.substring(0,currentAdminName.length()-1).toString());//当前处理人
 			work.setCurrentId(currentAdminId.substring(0,currentAdminId.length()-1).toString());//当前处理人id
		
	}
	
	/**
	 * 获取工作名称Set集合
	 * @return
	 */
	public Set<String>  getJonSet(){
		List<Job> jobList = jobService.getKeyJobList();
		Set<String> jobSet = new HashSet<String>();
		if(jobList != null){
			for(Job job : jobList){
				jobSet.add(job.getJobName());
			}
		}
		return jobSet;
	}
	

	
	/**
	 * 获取用户最大的优先级
	 */
	public String getPriorityLevelByUser(Admin admin){
		String level = "";
		if(admin != null){
			Set<Job> jobSet = admin.getMainJobSet();
			if(jobSet != null){
				for(Job job : jobSet){
					if(StringUtils.isNotEmpty(level)){
						if(StringUtils.isNotEmpty(job.getPriorityLevel())){
							if(Integer.parseInt(job.getPriorityLevel()) > Integer.parseInt(level)){
								level = job.getPriorityLevel();
							}
						}
					}else{
						level = job.getPriorityLevel();
					}
				}
			}
			
		}
		return level;
	}
	
	/**
	 * 根据关键字获取执行人
	 * @param work
	 * @param keyName
	 * @param recepList
	 * @param admin //创建人
	 */
	public boolean getAdminByKeyName(String keyName,List<Admin> recepList,Admin admin,String formContent,Admin loginAdmin){
		String [] keyArray =  keyName.trim().split("_");
		for(String temp : keyArray){
			Set<String> jobSet = getJonSet();
			List<String> deparmetNameList = deparmentService.getDeparmentName(); //获取部门名称
			//String[] tempArray = temp.trim().split(":");
			if(jobSet.contains(temp) || deparmetNameList.contains(temp)){//关键字  关键字格式  部门名称或者别名
				String adminLevel = getPriorityLevelByUser(admin); //当前用户优先级
				if(StringUtils.isEmpty(adminLevel)){
					adminLevel = "0";//默认最低级别
				}
				Job tempJob = jobService.getByName(temp);
				
				if(tempJob != null){ //历史数据兼容
					if(Integer.parseInt(adminLevel) > Integer.parseInt(tempJob.getPriorityLevel())){ //关键字小于工作用户级别直接过
						return true;
					}else{
						if(WorkFlowContants.DEPARMENT_MANAGER.equals(temp)){//部门经理
							Set<Deparment> deparmentList  = admin.getDeparmentSet();
							if(deparmentList != null && deparmentList.size() > 0){//判断用户部门是否存在
								for(Deparment deparment : deparmentList){
									Admin leader = deparment.getDeparmentLeader();//领导是否存在
									if( leader != null){
										if(!recepList.contains(leader)){//去重
											recepList.add(leader);
										}
									}
								}
							}
							if(recepList.contains(admin)){//判断处理人是否是自己,如果是自己就跳过
								recepList.clear();
								return true;
							}
						}else{
							Admin tempAdmin = adminService.get(tempJob.getAdminId());
							recepList.add(tempAdmin);
							if(recepList.size() == 1 && recepList.contains(admin)){//判断处理人是否是自己,如果是自己就跳过
								recepList.clear();
								return true;
							}
						}
					}
					
				}else{//新的关键字判断
				//	if(deparmetNameList.contains(temp)){
						List<Admin> tempAdminList = new ArrayList<Admin>();
						Deparment deparment  = deparmentService.getByName(temp);
						addDeparmentManager(deparment,tempAdminList);//获取当前部门以及父部门的直接领导
						if(tempAdminList.contains(admin)){ //当前人在部门领导里面
							return true;
						}else{
							if(WorkFlowContants.DEPARMENT_MANAGER.equals(deparment.getDeparmentAlisa())){//如果部门别名是部门经理
								Set<Deparment> deparmentList  = admin.getDeparmentSet();
								if(deparmentList != null && deparmentList.size() > 0){//判断用户部门是否存在
									for(Deparment tempDeparment : deparmentList){
										Admin leader = tempDeparment.getDeparmentLeader();//领导是否存在
										if( leader != null){
											if(!recepList.contains(leader)){//去重
												recepList.add(leader);
											}
										}
									}
								}
								if( recepList.contains(admin)){//判断处理人是否是自己,如果是自己就跳过
									recepList.clear();
									return true;
								}
								
							}else{
								recepList.add(deparment.getDeparmentLeader());
								if(recepList.size() == 1 && recepList.contains(admin)){//判断处理人是否是自己,如果是自己就跳过
									recepList.clear();
									return true;
								}
								
							}
						}
					}
					
				//}
			
				
			}else{ //非内置关键字
				if(StringUtils.isNotEmpty(formContent)){
					String formContents = "<div>"+formContent+"</div>";
					Document doc = Jsoup.parse(formContents, "UTF-8");
					Elements cidElem = doc.select(".contractCategory.cfield[builtsign=用户名][field="+keyName+"]");//查找关键字
					if(cidElem.size() > 0){
							String userName = cidElem.get(0).val();//获取处理的用户名
							if(StringUtils.isNotEmpty(userName)){
								Admin  tempAdmin = adminService.getAdminByName(userName);
								     if(tempAdmin == null){
								    	 throw new PersonalException("当前用户名存在多个用户,请联系人员!");
								     }else{
								    	 recepList.add(tempAdmin);
								     }
							}else{
								throw new PersonalException(keyName+"不能为空!");
							}
					}else{
						 throw new PersonalException("流程内容不符合规范,未发现【"+keyName+"】关键字");
					}
					
				}else{
					 throw new PersonalException("流程内容不存在,请检查表单!");
				}
				
			}
		}	
		return false;
	}
	
	/**
	 * 根据条件获取当前用户
	 * @param work  我的工作
	 * @param tempPoint 当前节点
	 * @throws Exception
	 */
	public void getUserByCondition(Work work,WorkFlowPoint tempPoint,List<Admin> recepList,Admin loginAdmin){
		boolean  status = false;
		//分支节点  更新节点观看人
			if(tempPoint != null){
				if(WorkFlowContants.BRANCH.equals(tempPoint.getIsBranch())){//如果为分支节点
					
					String condition = null;//条件值
					String value = null;//关键字值
					int keyNum = 0; //计算关键字是否匹配上
					//获取关键字
					 for(ContractField field:work.getFieldSet()){
					     if(field.getFieldName().equals(tempPoint.getKeyName())){//匹配分支节点关键字 获取关键字的value
					    	value = field.getValue(); 
					    	keyNum ++;
					    	break;
					     }
					 }
					 
					 StringBuffer currentAdminName = new StringBuffer();
	     			 StringBuffer currentAdminId = new StringBuffer();//当前处理人id
					 if(keyNum == 0) {//没匹配上关键字
						 throw new PersonalException("当前条件节点分支条件名称无法匹配!");
						 
					 }else{//条件名称匹配上
						String [] userCondition = tempPoint.getUserCondition().trim().split(","); //用户节点数组
						int  defaultCondition = 0;
						if(StringUtils.isNotEmpty(value)){//比较的值不为空
							 for(String tempCondition : userCondition){ //用户条件组成格式   用户id-用户id:条件1-条件2
									String [] user = tempCondition.trim().split(":");
									String [] conditionArray = user[1].trim().split("_");//获取多个条件
									int conditionNum = 0;//条件数
									int containsNum = 0; //包含数目
									if(conditionArray.length == 2){//说明 < 值 < 
										for(int i= 0; i < conditionArray.length; i++){
											
											String tempcondition = conditionArray[i];
											if(StringUtils.isEmpty(tempcondition)){
												conditionNum ++;
												continue;
											}
											if(tempcondition.contains("<")){//如果判断大于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													conditionNum ++;
													continue;
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件<的值只能填写整数!");
												}
												if(i == 0){//条件值左边
													if(Integer.valueOf(value).intValue() > Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}else{
													if(Integer.valueOf(value).intValue() < Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}
												
											}
					
											if(tempcondition.contains("≤")){//如果判断大于等于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													conditionNum ++;
													continue;
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件≤的值只能填写整数!");
												}
												if(i == 0){//条件值左边
													if(Integer.valueOf(value).intValue() >= Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}else{
													if(Integer.valueOf(value).intValue() <= Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}
												
											}
											
											if(tempcondition.contains("=")){//如果判断等于
												if(tempcondition.substring(1).trim().equals(value)){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											if(tempcondition.contains(WorkFlowContants.CONDITION_CONTAINS)){//如果是包含 模糊查询
												if(value.indexOf(tempcondition.substring(2).trim()) != -1 ){
													containsNum ++;
													break;
												}
											}
											if(tempcondition.contains(WorkFlowContants.CONDITION_NO_EQUALS)){//如果判断不等于
												if(!(tempcondition.substring(1).trim().equals(value))){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											
										}
										
										
									}else if(conditionArray.length == 1){ // <值大于
										for(String tempcondition : conditionArray){
											if(StringUtils.isEmpty(tempcondition)){
												throw new PersonalException("分支节点不能无条件!");
											}
											
											if(tempcondition.contains("<")){//如果判断大于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件<的值不能为空!");
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件<的值只能填写整数!");
												}
												if(Integer.valueOf(value).intValue() > Integer.valueOf(tempcondition.substring(1)).intValue() ){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
					
											if(tempcondition.contains("≤")){//如果判断大于等于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件≤的值不能为空!");
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件≤的值只能填写整数!");
												}
												if(Integer.valueOf(value).intValue() >= Integer.valueOf(tempcondition.substring(1)).intValue() ){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											
											if(tempcondition.contains("=")){//如果判断等于
												if(tempcondition.substring(1).trim().equals(value)){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											
											if(tempcondition.contains(WorkFlowContants.CONDITION_CONTAINS)){//如果是包含 模糊查询
												if(value.indexOf(tempcondition.substring(2).trim()) != -1 ){
													conditionNum ++;
													break;
												}
											}
											
											if(tempcondition.contains(WorkFlowContants.CONDITION_NO_EQUALS)){//如果判断不等于
												if(!(tempcondition.substring(1).trim().equals(value))){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
										}
										
									}else{
										throw new PersonalException("分支节点存在无条件分支!");
									}
								
									
									if((conditionNum == conditionArray.length) || (containsNum > 0)){//条件全部满足
										recepList.clear();//清空处理人	
										defaultCondition ++;
										//
										String [] adminArray = user[0].split("_");
										if(user.length == 3){//包含了节点条件值
											if(StringUtils.isNotEmpty(user[0]) || StringUtils.isNotEmpty(user[2])){//有处理人的分支
												for(String temp : adminArray){
													Admin tempAdmin = adminService.get(temp);
													if(tempAdmin != null && StringUtils.isNotEmpty(tempAdmin.getId())){
														recepList.add(tempAdmin);//接收人
													}
												
												}
												 status =	getAdminByKeyName(user[2],recepList,work.getAdmin(),work.getFormContent(),loginAdmin);//获取条件指定的人
												 if(!status){
													 setWorkName(work,recepList);
												 }
												 if(status){//继续找下一节点
								 				    findLastPoint(work,tempPoint,recepList,loginAdmin);
								 				 }
													
											}else{
												findLastPoint(work,tempPoint, recepList, loginAdmin);
											}
											
										}else{
											if(StringUtils.isNotEmpty(user[0])){//有处理人的分支
												for(String temp : adminArray){
													Admin tempAdmin = adminService.get(temp);
													if(tempAdmin != null && StringUtils.isNotEmpty(tempAdmin.getId())){
														recepList.add(tempAdmin);//接收人
														currentAdminName.append(tempAdmin.getName()).append("、");
						 		 	     				currentAdminId.append(tempAdmin.getId()).append("、");
													}
													
												}
												 
								 				work.setCurrentAdminName(currentAdminName.substring(0,currentAdminName.length()-1).toString());//当前处理人
								     			work.setCurrentId(currentAdminId.substring(0,currentAdminId.length()-1).toString());//当前处理人id
											}else{
												findLastPoint(work,tempPoint, recepList, loginAdmin);
											}
											
											
										}
										break;
									
									}
									
								 }
							 
						}
						
						if(defaultCondition == 0){//条件分支没有匹配上  给默认分支 默认处理人
							throw new PersonalException("表单填写内容不合适,未找到符合要求的分支,请正确填写表单内容!");
						 }
					 }
					 
	     		   this.update(work);
				}
		    }else{
		    	throw new PersonalException("下一节点不存在!");
		    }
		
	}
	
	
	public void findLastPoint(Work work,WorkFlowPoint point,List<Admin> recepList,Admin loginAdmin){
		String flowId =  workFlowService.getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
		WorkFlowPoint flowPoint = workFlowPointService.getWorkFlowPointByWorkFlow(WorkFlowContants.BETWEEN, point.getNextPonit(), flowId);//获取下一节点
		if(flowPoint != null){
			if(StringUtils.isEmpty(work.getLastPointId())){
				work.setLastPointId(point.getId());
				work.setLastPointName(point.getPointName());
			}
			work.setCurrentPointId(flowPoint.getId());//更换当前节点
			work.setCurrentPointName(flowPoint.getPointName());
			boolean status = false;
			if(WorkFlowContants.UNBRANCH.equals(flowPoint.getIsBranch())){//普通节点
				List<Admin> adminList = new ArrayList<Admin>();
				if(flowPoint.getWorkFlowSet() != null && flowPoint.getWorkFlowSet().size() > 0){
					 adminList = new ArrayList<Admin>(flowPoint.getWorkFlowSet());//节点观看人
	 				 	 recepList.addAll(adminList);//通知人
	 				 	if(StringUtils.isNotEmpty(flowPoint.getSearchName())){
	 				 		status =  getAdminByKeyName(flowPoint.getSearchName(), recepList,work.getAdmin(),work.getFormContent(),loginAdmin);
	 				 	}
	 				 	if(!status){
	 				 		setWorkName(work, recepList);//设置审批人名称
	 				 	}
	 				    if(status){//继续找下一节点
	 				    	findLastPoint(work,flowPoint,recepList,loginAdmin);
	 				    }
	 				 
				}else{
					//关键词
					if(StringUtils.isEmpty(flowPoint.getSearchName())){
						throw new PersonalException("下一节点无审批人!");
					}else{
						 status = getAdminByKeyName(flowPoint.getSearchName(), recepList,work.getAdmin(),work.getFormContent(),loginAdmin);
						 if(!status){
							 setWorkName(work, recepList);
						 }
						 if(status){//继续找下一节点
		 				    	findLastPoint(work,flowPoint,recepList,loginAdmin);
		 				 }
					}
					
				}
			}else{
				getUserByCondition(work, flowPoint,recepList,loginAdmin);
			}
		}else{
			Set<ContractField> deleteSet = new HashSet<ContractField>();
			//代表最后一节点了
			if(work.getIsDelete() != null && WorkFlowContants.DELETE.equals(work.getIsDelete())){//是否需要删除
				//删除工作流关联的信息
				deleteSet = work.getFieldSet();
				
				
			}else if(work.getIsDelete() != null && WorkFlowContants.UPDATE.equals(work.getIsDelete())){//更新操作
				for(ContractField field : work.getFieldSet()){//更换状态
					field.setValue(field.getUpdateValue());
				}
			}else{
				for(ContractField field : work.getFieldSet()){//更换状态
					field.setStatu(WorkFlowContants.PASS);
				}
				
			}
			work.setModifyDate(new Date(System.currentTimeMillis()));
			work.setEndDate(new Date(System.currentTimeMillis()));//结束日期
			work.setFieldSet(new HashSet<ContractField>());//清除关系
			work.setCurrentAdminName("");//当前处理人
			work.setCurrentId("");//当前处理人id
			work.setIsComplete(WorkFlowContants.COMPLETE);//已完成
			work.setStatus(WorkFlowContants.END_STATU);//正常结束
			
			if(StringUtils.equals(work.getWorkFlow().getId(),CategoryContants.PROJECT_SETUP_ID)){
				String expand = work.getExpand();
				if(StringUtils.isNotBlank(expand)){
					Project4workflow project4workflow = JsonUtil.toObject(expand, Project4workflow.class);
					Project project = new Project();
					project.setName(project4workflow.getName());
					try {
						project.setProjectType(Project.ProjectType.valueOf(project4workflow.getProjectType()));
					} catch (Exception e) {
						project.setProjectType(Project.ProjectType.开发);
					}
					project.setBeginTime(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", new Date()));
					project.setEndTime(DateUtil.addDay(project.getBeginTime(),NumberUtils.toInt(project4workflow.getPredictDay(),7 )));
					try {
						project.setStatus(Project.Status.valueOf(project4workflow.getStatus()));
					} catch (Exception e) {
						project.setStatus(Project.Status.未完成);
					}
					project.setContent(project4workflow.getProjectDesc());
					Admin responsibor = adminService.get(project4workflow.getResponsiborId());
					project.setResponsibor(responsibor);
					Deparment  deparment = work.getAdmin().getDeparmentSet().iterator().next();
					project.setDeparment(deparment);
					Admin creator = work.getAdmin();
					project.setCreator(creator);
//					project.setRequestRowidList(project4workflow.getRequestRowids());
//					contractService.updateProjectRelation(StrUtil.toString(project4workflow.getRequestRowids()));
					projectService.save(project);
					for(String requestRowid : project4workflow.getRequestRowids()){
						ContractField  contractField  = contractDao.get(requestRowid);
						Assert.notNull(contractField, "不存在需求："+requestRowid);
						userPlanRequireDao.removeByRequire(requestRowid);
						contractField.setProjectId(project.getId());
					}
				}
			}else if(StringUtils.equals(work.getWorkFlow().getId(),CategoryContants.MODEL_USER_ADD_ID)){
				LinkedHashMap<String, String> entity = null;
				String contractCategoryId=null;
				String formContent = "<div>" + work.getFormContent() + "</div>";
				Document doc = Jsoup.parse(formContent, "UTF-8");
				Elements cidElem = doc.select(".contractCategory.cid");
				if (cidElem.size() > 0) {
					contractCategoryId = cidElem.attr("contract_category_id");
					ContractCategory contractCategory = contractCategoryService.get(contractCategoryId);
					if (contractCategory == null) {
						throw new PersonalException("表单内容对应的二级分类不存在,请重新设计表单!");
					}
					String[] titleArr = contractCategory.getDefinitionObj().getTitles();
					entity = getFieldEntity(doc, titleArr);
				}				
				Assert.notEmpty(entity, "内容不能为空");
				JSONObject jsonObject = new JSONObject();
				for(Entry<String, String> entry : entity.entrySet()){
					if(StringUtils.equals(entry.getKey(),"权限")){
						JSONArray array = new JSONArray();
						String values = entry.getValue();
						if(StringUtils.isNotBlank(values)){
							array.addAll(Arrays.asList(values.split(",")));
						}
						jsonObject.put("权限", array);
					}else{
						jsonObject.put(entry.getKey(), entry.getValue());
					}
				}
				String entityStr = JsonUtil.toJson(jsonObject);
				System.out.println("################# 发送模型用接口");
				System.out.println(entityStr);
				String paramStr;
				try {
					paramStr = "externalInfo="+URLEncoder.encode(entityStr,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new PersonalException("参数url编码异常："+e.getMessage());
				}
				String MODEL_USER_ADD_URL = SettingUtil.getSetting().getModelAddUserUrl();
				String result = HttpClientUtil.sendPost(MODEL_USER_ADD_URL	, paramStr);
				result = URLDecoder.decode(result);
				if(result.contains("创建失败")){
					throw new PersonalException("model增加用户返回异常："+result);
				}
			}else if(work.getWorkType() != null && (WorkFlowContants.INSIDE_REQUIRE_FLOW.equals(work.getWorkType()) || WorkFlowContants.EXTERNAL_REQUIRE_FLOW.equals(work.getWorkType()) )){
				LinkedHashMap<String, String> entity = null;
				String contractCategoryId=null;
				String formContent = "<div>" + work.getFormContent() + "</div>";
				Document doc = Jsoup.parse(formContent, "UTF-8");
				Elements cidElem = doc.select(".contractCategory.cid");
				if (cidElem.size() > 0) {
					contractCategoryId = cidElem.attr("contract_category_id");
					ContractCategory contractCategory = contractCategoryService.get(contractCategoryId);
					if (contractCategory == null) {
						throw new PersonalException("表单内容对应的二级分类不存在,请重新设计表单!");
					}
					String[] titleArr = contractCategory.getDefinitionObj().getTitles();
					entity = getFieldEntity(doc, titleArr);
				}				
				String expand = work.getExpand();
				if(StringUtils.isNotBlank(expand)){
					Require4workflow require4workflow = JsonUtil.toObject(expand, Require4workflow.class);
					Assert.notEmpty(entity, "内容不能为空");
					String primaryId = entity.values().iterator().next();
					Assert.notNull(primaryId, "主键不能为空");
					ContractField primaryContractField = contractService.unique(DetachedCriteria.forClass(ContractField.class)
							.add(Restrictions.eq("contractCategoryId", contractCategoryId))
							.add(Restrictions.eq("indication", true))
							.add(Restrictions.eq("value", primaryId)));
					
					Assert.notNull(primaryContractField, "主键不存在");
					primaryContractField.setProjectId(require4workflow.getProjectId());
					contractService.update(primaryContractField);
				}						
			}
			removeRelationWorkForDefined(work);//去除工作流与表单的关系
			
			this.update(work);
			if(!WorkFlowContants.UN_DELETE.equals(work.getIsInternal())){//非特殊工作流
				if(!(StringUtils.isNotEmpty(work.getCurrentPointId()))){//如果没有下一节点
					if(work.getIsDelete() != null && WorkFlowContants.DELETE.equals(work.getIsDelete())){//是否需要删除
						this.deleteContartField(deleteSet,work.getId());
					}
				}
			}
		}
	}
	
    /**
     * 增加部门管理
     * @param deparment
     * @param adminList
     */
    public void addDeparmentManager(Deparment deparment,List<Admin> adminList){
  	  if(deparment != null){
  		  if(deparment.getDeparmentLeader() != null){
  			  adminList.add(deparment.getDeparmentLeader());//部门领导
  			  addDeparmentManager(deparment.getParent(),adminList);
  		  }
  		  
  	  }
    }

   
	

	  /**
     * 保存系统消息
     * @param title  标题
     * @param context 内容
     * @param admin  人员
     */
    public void  saveSysMessage(String title,String context,Admin admin,List<Admin> adminList,String workId,String currentPointId,String isInternal){
  	   SystemMessage message = new SystemMessage();
			message.setCreateTime(new Date(System.currentTimeMillis()));//创建时间
			message.setRedType(RedType.未读);//是否已读
			message.setTitle(title);
			if(StringUtils.isNotEmpty(workId)){
				message.setType(SystemmessageType.审批消息);
			}else{
				message.setType(SystemmessageType.个人消息);
			}
			message.setContext(context);
			message.setLoginAdmin(admin);//发布人
			message.setToMessagAdmin(adminList);//接收人
			message.setWorkId(workId);
			message.setCurrentPointId(currentPointId);
			message.setIsInternal(isInternal);//是否为内置工作流
			systemService.save(message);
    }
    
    
    /**
     * 更新系统消息
     */
    
    public void updateSysMessage(String context,String workId,String currentPointId,Admin loginAdmin){
    	String hql = "from SystemMessage where currentPointId =:currentPointId and  workId =:workId";
    	SystemMessage systemMessage = (SystemMessage) this.getBaseDao().getSession().createQuery(hql).setParameter("currentPointId", currentPointId).setParameter("workId", workId).uniqueResult();
    	if(systemMessage != null){
    		systemMessage.setType(SystemmessageType.个人消息);
    		systemMessage.setContext(context);
    		systemMessage.setWorkId(null);
    		systemMessage.setCurrentPointId(null);
    		systemMessage.setLoginAdmin(loginAdmin);
    		systemService.update(systemMessage);
    	}
    }
    
    /**
     * 审批通过
     * @param work
     * @param loginAdmin
     * @param record
     * @param adminList
     * @return
     */
    public String updateWorkCheck(Work work,Admin loginAdmin,ApprovalRecord record,List<Admin>  adminList){
    	Work temp = this.get(work.getId());//获取数据库最新对象
    	if(StringUtils.isNotEmpty(temp.getCurrentId())){//验证审批权限
    		if(temp.getCurrentId().indexOf(loginAdmin.getId()) == -1){
    			throw new PersonalException("工作已处理!");
    		}
    	}else{
    		throw new PersonalException("工作已处理!");
    	}
    	if(StringUtils.isNotEmpty(temp.getCheckPerson())){
    		if(!temp.getCheckPerson().contains(loginAdmin.getId())){
    			String tempCheckPerson = temp.getCheckPerson()+","+loginAdmin.getId();
    			temp.setCheckPerson(tempCheckPerson);
    		}
    	}else{
    		temp.setCheckPerson(loginAdmin.getId());
    	}
    	ContractEntity<String, String> entity = null ; 
    	String contractCategoryId = null;
    	  boolean statu = false;
    		String formContent = "<div>"+work.getFormContent()+"</div>";
			Document doc = Jsoup.parse(formContent, "UTF-8");
			Elements cidElem = doc.select(".contractCategory.cid");
			if(cidElem.size()>0){
				contractCategoryId = cidElem.attr("contract_category_id");
  			ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
  			if(contractCategory == null){
      				throw new PersonalException("表单内容对应的二级分类不存在,请重新设计表单!");
      			}
  			String[] titleArr = contractCategory.getDefinitionObj().getTitles();
  			entity = getFieldEntity(doc, titleArr);
  			contractDao.update4work(contractCategoryId, entity);
			}
    	
    	
		Set<ContractField> deleteSet = new HashSet<ContractField>();
	
		List<Admin> recepList = new ArrayList<Admin>();//接收人
		
			temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
			temp.setLastAdmin(loginAdmin);//最后一次操作人
			temp.setLastAdminName(loginAdmin.getName());//最后操作人名称
			record.setPointName(temp.getCurrentPointName());//审签节点名称
			WorkFlowPoint currentTempPoint = null;
			if(StringUtils.isNotEmpty(temp.getCurrentPointId())){
				currentTempPoint = workFlowPointService.get(temp.getCurrentPointId(),temp.getVersion());
				if(currentTempPoint == null){
					throw new PersonalException("当前审批节点不存在!");
				}
			}else{
				throw new PersonalException("当前审批节点不存在!");
			}
			
			if(StringUtils.isNotEmpty(temp.getCheckSort())){//判断审批记录
				Map<String,Object> checkMap = JsonUtil.toObject(temp.getCheckSort(), HashMap.class);
				checkMap.put(currentTempPoint.getPointSort(), loginAdmin.getName());
				temp.setCheckSort(JsonUtil.toJson(checkMap));
			}else{
				Map<String,Object> checkMap = new HashMap<String,Object>();
				checkMap.put(currentTempPoint.getPointSort(), loginAdmin.getName());
				temp.setCheckSort(JsonUtil.toJson(checkMap));
			}
			//更换上一节点
			temp.setLastPointId(currentTempPoint.getId());
			temp.setLastPointName(currentTempPoint.getPointName());
			temp.setFormContent(work.getFormContent());
	
				if( StringUtils.isNotEmpty(work.getCurrentPointId())){//如果有下一节点
					recepList.add(temp.getAdmin());
	      			saveSysMessage("工作审批结果","尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】进入下一级审核。",loginAdmin,recepList,null,null,null);
	      			
	      			sendMailAndSms(temp,recepList,"尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】进入下一级审核。","工作审批结果");
	      			recepList = new ArrayList<Admin>();
	      			currentTempPoint = workFlowPointService.get(work.getCurrentPointId(),temp.getVersion()); 
	      			if(currentTempPoint == null){
	      				throw new PersonalException("未找到下一节点!");
	      			}
	      			//替换当前节点
	      			temp.setCurrentPointId(currentTempPoint.getId());
	      			temp.setCurrentPointName(currentTempPoint.getPointName());
					
		 					
		 				
		 				if(currentTempPoint != null){
		 						if(WorkFlowContants.UNBRANCH.equals(currentTempPoint.getIsBranch())){//如果为普通节点
		 							if(currentTempPoint.getWorkFlowSet() != null && currentTempPoint.getWorkFlowSet().size() > 0){//判断节点是否存在审批人
		 								  adminList = new ArrayList<Admin>(currentTempPoint.getWorkFlowSet());//节点观看人
		 								 recepList.addAll(adminList);//通知人;
					 				 	if(StringUtils.isNotEmpty(currentTempPoint.getSearchName())){
					 				 		statu = getAdminByKeyName(currentTempPoint.getSearchName(), recepList,temp.getAdmin(),temp.getFormContent(),loginAdmin);
					 				 	}
					 				 	if(!statu){
					 				 		setWorkName(temp, recepList);//设置审批人
					 				 	}
					 				 	 
		 							}else{
		 								
		 								if(StringUtils.isNotEmpty(currentTempPoint.getSearchName())){
		 									statu = getAdminByKeyName(currentTempPoint.getSearchName(), recepList,temp.getAdmin(),temp.getFormContent(),loginAdmin);
		 									if(!statu){
		 										setWorkName(temp, recepList);
		 									}
		 								}else{
		 									throw new PersonalException("下一节点无审批人!");
										
		 								}
		 							}
		 							
		 						}else{//分支节点
		 							this.getUserByCondition(temp, currentTempPoint, recepList,temp.getAdmin());
		 						}
		 				 	  
		 				    }else{
		 				    	throw new PersonalException("下一节点不存在!");
		 				    }
		 				
		 				if(statu){//继续找下一节点
			 				findLastPoint(temp,currentTempPoint,recepList,loginAdmin);
			 				this.update(temp);
				 		}
			 				
		 				
		 			
	      			if(StringUtils.isNotEmpty(temp.getCurrentAdminName())){
	      				
	      				saveSysMessage("工作审批通知","尊敬的"+temp.getCurrentAdminName()+" ,您有新的工作【"+temp.getWorkName()+"】审批,请及时处理。",loginAdmin,recepList,temp.getId(),temp.getCurrentPointId(),null);
	      				sendMailAndSms(temp,recepList,"尊敬的"+temp.getCurrentAdminName()+" ,您有新的工作【"+temp.getWorkName()+"】审批,请及时处理。","工作审批通知");
	      			}else{
	      				adminList = new ArrayList<Admin>();
		  	 			adminList.add(temp.getAdmin());//接收人就是自己
		  	 			saveSysMessage("工作审批结果","尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】已结束。",loginAdmin,adminList,null,null,null);
		  	 			sendMailAndSms(temp,adminList,"尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】已结束。","工作审批结果");
		      			
	      			}
					
	      			
				}else{
					if(temp.getIsDelete() != null && WorkFlowContants.DELETE.equals(temp.getIsDelete())){//是否需要删除
						
					
						//删除工作流关联的信息
						deleteSet = temp.getFieldSet();
					
						
					}else if(temp.getIsDelete() != null && WorkFlowContants.UPDATE.equals(temp.getIsDelete())){//更新操作
						for(ContractField field : temp.getFieldSet()){//更换状态
							field.setValue(field.getUpdateValue());
						}
					}else{
						for(ContractField field : temp.getFieldSet()){//更换状态
							field.setStatu(WorkFlowContants.PASS);
						}
						
					}
					temp.setModifyDate(new Date(System.currentTimeMillis()));
					temp.setEndDate(new Date(System.currentTimeMillis()));//结束日期
					temp.setFieldSet(new HashSet<ContractField>());//清除关系
					temp.setCurrentAdminName("");//当前处理人
	      			temp.setCurrentId("");//当前处理人id
					temp.setIsComplete(WorkFlowContants.COMPLETE);//已完成
					temp.setStatus(WorkFlowContants.END_STATU);//正常结束
	      			recepList.add(temp.getAdmin());
	      			
					saveSysMessage("工作审批结果","尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】已结束。",loginAdmin,recepList,null,null,null);
					sendMailAndSms(temp,recepList,"尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】已结束。","工作审批结果");
					if(StringUtils.equals(temp.getWorkFlow().getId(),CategoryContants.PROJECT_SETUP_ID)){  //TODO 以后要改成workType
						String expand = temp.getExpand();
						if(StringUtils.isNotBlank(expand)){
							Project4workflow project4workflow = JsonUtil.toObject(expand, Project4workflow.class);
							Project project = new Project();
							project.setName(project4workflow.getName());
							try {
								project.setProjectType(Project.ProjectType.valueOf(project4workflow.getProjectType()));
							} catch (Exception e) {
								project.setProjectType(Project.ProjectType.开发);
							}
							project.setBeginTime(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", new Date()));
							project.setEndTime(DateUtil.addDay(project.getBeginTime(),NumberUtils.toInt(project4workflow.getPredictDay(),7 )));
							try {
								project.setStatus(Project.Status.valueOf(project4workflow.getStatus()));
							} catch (Exception e) {
								project.setStatus(Project.Status.未完成);
							}
							project.setContent(project4workflow.getProjectDesc());
							Admin responsibor = adminService.get(project4workflow.getResponsiborId());
							project.setResponsibor(responsibor);
							Deparment deparment = temp.getAdmin().getDeparmentSet().iterator().next();
							project.setDeparment(deparment);
							Admin creator = temp.getAdmin();
							project.setCreator(creator);
//							project.setRequestRowidList(project4workflow.getRequestRowids());
//							contractService.updateProjectRelation(StrUtil.toString(project4workflow.getRequestRowids()));
							projectService.save(project);
							for(String requestRowid : project4workflow.getRequestRowids()){
								ContractField  contractField  = contractDao.get(requestRowid);
								Assert.notNull(contractField, "不存在需求："+requestRowid);
								userPlanRequireDao.removeByRequire(requestRowid);
								contractField.setProjectId(project.getId());
							}
						}
					}else if(StringUtils.equals(temp.getWorkFlow().getId(),CategoryContants.MODEL_USER_ADD_ID)){  //TODO 以后要改成workType
						Assert.notEmpty(entity, "内容不能为空");
						JSONObject jsonObject = new JSONObject();
						for(Entry<String, String> entry : entity.entrySet()){
							if(StringUtils.equals(entry.getKey(),"权限")){
								JSONArray array = new JSONArray();
								String values = entry.getValue();
								if(StringUtils.isNotBlank(values)){
									array.addAll(Arrays.asList(values.split(",")));
								}
								jsonObject.put("权限", array);
							}else{
								jsonObject.put(entry.getKey(), entry.getValue());
							}
						}
						String entityStr = JsonUtil.toJson(jsonObject);
						System.out.println("################# 发送模型用接口");
						System.out.println(entityStr);
						String paramStr;
						try {
							paramStr = "externalInfo="+URLEncoder.encode(entityStr,"UTF-8");
						} catch (UnsupportedEncodingException e) {
    						throw new PersonalException("参数url编码异常："+e.getMessage());
						}
						String MODEL_USER_ADD_URL = SettingUtil.getSetting().getModelAddUserUrl();
						String result = HttpClientUtil.sendPost(MODEL_USER_ADD_URL	, paramStr);
						result = URLDecoder.decode(result);
						if(result.contains("创建失败")){
    						throw new PersonalException("model增加用户返回异常："+result);
						}
					}else if(temp.getWorkType() != null && (WorkFlowContants.INSIDE_REQUIRE_FLOW.equals(temp.getWorkType()) || WorkFlowContants.EXTERNAL_REQUIRE_FLOW.equals(temp.getWorkType()) )){
						String expand = temp.getExpand();
						if(StringUtils.isNotBlank(expand)){
							Require4workflow require4workflow = JsonUtil.toObject(expand, Require4workflow.class);
							Assert.notEmpty(entity, "内容不能为空");
							String primaryId = entity.values().iterator().next();
							Assert.notNull(primaryId, "主键不能为空");
							ContractField primaryContractField = contractService.unique(DetachedCriteria.forClass(ContractField.class)
									.add(Restrictions.eq("contractCategoryId", contractCategoryId))
									.add(Restrictions.eq("indication", true))
									.add(Restrictions.eq("value", primaryId)));
							
							Assert.notNull(primaryContractField, "主键不存在");
							primaryContractField.setProjectId(require4workflow.getProjectId());
							contractService.update(primaryContractField);
						}						
					}
					//去除工作与工作流关系
	      			removeRelationWorkForDefined(temp);
				}
				
				record.setApprovalPerson(loginAdmin.getName());//审批人
				record.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
				record.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
				record.setStatus("通过");
				record.setWork(temp);
				Set<ApprovalRecord> tempArray = temp.getRecord();
				tempArray.add(record);
				temp.setRecord(tempArray);		
			this.update(temp);
  			
			if(!WorkFlowContants.UN_DELETE.equals(temp.getIsInternal())){//非特殊工作流
				if(!( StringUtils.isNotEmpty(work.getCurrentPointId()))){//如果没有下一节点
					if(temp.getIsDelete() != null && WorkFlowContants.DELETE.equals(temp.getIsDelete())){//是否需要删除
						this.deleteContartField(deleteSet,temp.getId());
					}
				}
			}
			return "success";
		
    }
    
   
    
    /**
     * 去除于工作流定义的关系
     * @param work
     */
    public void removeRelationWorkForDefined(Work work){
    	work.setWorkFlow(null);
    	work.setFlowType(null);
    	work.setCurrentPointId(null);
    	work.setCurrentPointName(null);
    	work.setLastPointId(null);
    	work.setLastPointName(null);
    }
    
    
    
    public void  updateSystemMessage(String workId){
    	String sql = "update rzrk_system_message set workId = null,is_internal = null where  workId =:workId and (is_internal = 1 or is_internal = 2)";
    	this.getBaseDao().getSession().createSQLQuery(sql).setParameter("workId", workId).executeUpdate();
    }
     /**
      * 更新我的工作
      * @param work
      * @param temp
      * @param adminList
      * @return
     * @throws Exception 
      */
    public String updateWork(Work work,Work temp,List<Admin>  adminList,Admin loginAdmin,String[]  fileName,String[]  filePath){
    	
    	WorkFlowPoint currentTempPoint = null;
		if(StringUtils.isNotEmpty(temp.getCurrentPointId())){
			currentTempPoint = workFlowPointService.get(temp.getCurrentPointId(),temp.getVersion());
			if(currentTempPoint != null){
				temp.setLastPointId(currentTempPoint.getId());
				temp.setLastPointName(currentTempPoint.getPointName());//更新上一节点
			}
			
		}
		
		updateSystemMessage(temp.getId());
		
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
				 temp.setFileName(fileNameBuffer.substring(0,fileNameBuffer.length()-1).toString());//文件名
				 temp.setFilePath(filePathBuffer.substring(0,filePathBuffer.length()-1).toString());//文件路径
			 }
		 }
			
		
		temp.setStatus(WorkFlowContants.CHECK_STATU);//待审批状态
		temp.setIsComplete(WorkFlowContants.WAIT);//代办状态
		temp.setFormContent(work.getFormContent());
		List<Admin> recepList = new ArrayList<Admin>();//接收人
		boolean statu = false; 
		WorkFlowPoint tempPoint = null; //当前节点
				if(StringUtils.isEmpty(work.getCurrentPointId())){
					throw new PersonalException("请选择节点!");
				}
 			    tempPoint =	workFlowPointService.get(work.getCurrentPointId(),temp.getVersion());//获取工作流节点
 			    ApprovalRecord record = new ApprovalRecord();
 					if(tempPoint != null){
 		 			     if(StringUtils.isNotEmpty(temp.getCurrentPointName())){
 		 			    	record.setPointName(temp.getCurrentPointName());
 		 			     }else{
 		 			    	record.setPointName(tempPoint.getPointName());
 		 			     }
 						temp.setCurrentPointId(tempPoint.getId());
 		 				temp.setCurrentPointName(tempPoint.getPointName());//更新当前节点
 						if(WorkFlowContants.UNBRANCH.equals(tempPoint.getIsBranch())){//如果为普通节点
 							if(tempPoint.getWorkFlowSet() != null && tempPoint.getWorkFlowSet().size() > 0){
 								adminList = new ArrayList<Admin>(tempPoint.getWorkFlowSet());//节点观看人
 	 							 recepList.addAll(adminList);
 	 							if(StringUtils.isNotEmpty(tempPoint.getSearchName())){//查看节点关键字是否存在
 	 								statu = getAdminByKeyName(tempPoint.getSearchName(), recepList,temp.getAdmin(),temp.getFormContent(),loginAdmin);
 	 							}	 
 	 							if(!statu){
 	 								setWorkName(temp, recepList);//设置审批人
 	 							}
 							}else{
 								
 								if(StringUtils.isNotEmpty(tempPoint.getSearchName())){
 									statu = getAdminByKeyName(tempPoint.getSearchName(), recepList,temp.getAdmin(),temp.getFormContent(),loginAdmin);
 									if(!statu){
 										setWorkName(temp, recepList);
 									}
 								}else{
 									throw new PersonalException("下一节点无审批人!");
								
 								}
 							}
 						
 						}
 				 	   
 				 	  
 				    }else{
 				    	throw  new PersonalException("下一节点不存在!");
 				    }
 					
 			
		
		temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
		temp.setLastAdmin(loginAdmin);//最后一次操作人
		
		
		record.setApprovalPerson(loginAdmin.getName());//审批人
		record.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
		record.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
		record.setStatus("重新提交");
		record.setWork(temp);
		record.setAdvice(loginAdmin.getName()+"修改后重新提交");
		Set<ApprovalRecord> tempArray = temp.getRecord();
		tempArray.add(record);
		temp.setRecord(tempArray);	
		
			//增加写入二级表	start      			
  			String formContent = "<div>"+work.getFormContent()+"</div>";
  			Document doc = Jsoup.parse(formContent, "UTF-8");
  			Elements cidElem = doc.select(".contractCategory.cid");
  			
  			if(cidElem.size()>0){
//  				String cid = cidElem.val();
  				String contractCategoryId = cidElem.attr("contract_category_id");
      			ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
      			if(contractCategory == null){
	      				throw new PersonalException("表单内容对应的二级分类不存在,请重新设计表单!");
	      			}
      			String[] titleArr = contractCategory.getDefinitionObj().getTitles();
      			ContractEntity<String, String> entity = getFieldEntity(doc, titleArr);
      			updateWithForm(temp,contractCategoryId, entity);
      			//设置分支节点用户
      			getUserByCondition(temp,tempPoint,recepList,temp.getAdmin());
  			}else{
      			update(temp);
  			}

  			if(statu){//继续找下一节点
 				findLastPoint(temp,tempPoint,recepList,loginAdmin);
 				this.update(temp);
	 		}
 			
  			String message = null;
  			String subJect = null;
  			if(StringUtils.isNotEmpty(temp.getCurrentAdminName())){
  				message = "尊敬的"+temp.getCurrentAdminName()+" ,您有新的工作【"+temp.getWorkName()+"】审批,请及时处理。";
  				subJect = "工作审批通知";
  				saveSysMessage("工作审批通知",message,loginAdmin,recepList,temp.getId(),temp.getCurrentPointId(),null);
  			}else{
  				recepList.clear();
  				recepList.add(temp.getAdmin());//接收人就是自己
  				subJect = "工作审批结果";
  				message = "尊敬的"+temp.getAdmin().getName()+",您的工作【"+temp.getWorkName()+"】已结束。";
  	 			saveSysMessage("工作审批结果",message,loginAdmin,recepList,null,null,null);
  	 			
  			}
  			
  			sendMailAndSms(temp,recepList,message,subJect);
			
			return "success";
		
		
    }


	@Override
	public void saveWithForm(Work work, String contractCategoryId, ContractEntity<String, String> entity){
		Set<ContractField> cfList = contractDao.save4work(contractCategoryId, entity);
		work.setFieldSet(cfList);
		save(work);
		WorkContractRecord workContractRecord = new WorkContractRecord();
		workContractRecord.setContractCategoryId(contractCategoryId);
		workContractRecord.setWorkId(work.getId());
		workContractRecord.setWorkFlowId(work.getWorkFlow().getId());
		workContractRecord.setRowId(cfList.iterator().next().getId());
		workContractRecordDao.save(workContractRecord);
	}

	@Override
	public void updateWithForm(Work work,String contractCategoryId, ContractEntity<String, String> entity){
		update(work);
		contractDao.update4work(contractCategoryId, entity);
	}

	public List<Work> getByContractFieldPrimary(ContractField primaryField){
		return workDao.getByContractFieldPrimary(primaryField);
		
	}
	
	 /**
     * 获取工作类型json数据
     * @return
     */
    public JSONArray getWorkFlowTypeList(){
    	List<WorkFlowType> flowList = workFlowTypeService.getAllList();
    	JSONArray jsonObj = new JSONArray();
    	 Map<String,Object> initMap = new HashMap<String,Object>(); 
    	    initMap.put("id", "0"); 
    	    initMap.put("text", "");
   	        jsonObj.add(initMap);
		for(int i = 0; i < flowList.size(); i++ ){
			WorkFlowType temp = flowList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("text", temp.getTypeName());
	        jsonObj.add(map);
		}
		return jsonObj;	
    }
    
    /**
     * 获取工作流程json数据
     * @return
     */
    public JSONArray getWorkFlowList(){
    	List<WorkFlow> flowList = workFlowService.getWorkFlowList();
    	JSONArray jsonObj = new JSONArray();
    	  Map<String,Object> initMap = new HashMap<String,Object>(); 
    	  initMap.put("id", "0"); 
    	  initMap.put("text", "");
    	  jsonObj.add(initMap);
		for(int i = 0; i < flowList.size(); i++ ){
			WorkFlow temp = flowList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("text", temp.getFlowName());
	        jsonObj.add(map);
		}
		return jsonObj;	
    }
    
    
	/**
	 * 获取工作流表单的联动数据
	 * @return
	 */
	public  Map<String,Object> getAjaxWorkFlowDataByWorkFlowId(String flowTypeId,Long version){
		 String workFlowId = workFlowService.getMatchFlowId(flowTypeId, version);//新流程id
		 return getAjaxWorkFlowDataByWorkFlowId(workFlowId);
	}
	
	
	
	
	/**
	 * 获取工作流表单的联动数据
	 * @return
	 */
	public  Map<String,Object> getAjaxWorkFlowDataByWorkFlowId(String flowTypeId){
		 Map<String,Object> map = new HashMap<String,Object>(); //json数据集合
		 if(StringUtils.isNotEmpty(flowTypeId)){//工作流程Id
			 WorkFlow   workFlow = workFlowService.get(flowTypeId);
			 if(workFlow != null){
				 
				 Set<WorkFlowPoint> pointShowList = workFlow.getWorkFlowPointSet();//获取流程下所有节点
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
				  	        
				  	      if(StringUtils.isNotEmpty(temp.getFormKeyName())){
				  	          map1.put("formKeyName", temp.getFormKeyName());
				  	        }else{
				  	          map1.put("formKeyName", "");
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
				  	        if(StringUtils.isNotEmpty(temp.getWorkFlowPointId())){
				  	        	map1.put("lastPointId", temp.getWorkFlowPointId().split("-")[0]);
				  	        }else{
				  	        	map1.put("lastPointId", "");
				  	        }
				  	        map1.put("id", temp.getId());
				  	        if(StringUtils.isNotEmpty(temp.getName())){
				  	          map1.put("name", temp.getName());
				  	        }else{
				  	          map1.put("name", "");
				  	        }
				  	        map1.put("pointSort", temp.getPointSort());
				  	     
				  	        jsonObj2.add(map1);
		    		  }
		    		  map.put("pointShowList", jsonObj2);
		    	  }else{
		    		  map.put("pointShowList", "");//流程图节点
		    	  }
				 
				  if(StringUtils.isNotEmpty(workFlow.getIsDelete())){
		  	        	 map.put("isDelete", workFlow.getIsDelete());//是否删除
		  	        }else{
		  	        	 map.put("isDelete", "");//是否删除
		  	        }
				//获取第一个流程的表单的内容 表单如果不存在默认空
		    	   if(workFlow.getFlowForm() != null){
		    		   map.put("content", workFlow.getFlowForm().getFormContent());
		    	   }else{
		    		   map.put("content", "");
		    	   }
		    	   
		    	   //获取开始节点指定的节点集合
			    	List<WorkFlowPoint> pointList = workFlowPointService.getFlowPointList(workFlow.getId());
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
				  map.put("isDelete", "");
				  map.put("pointList", ""); //节点集合
	    		  map.put("adminList", ""); //列表集合
	    		  map.put("content", "");
	    		  map.put("pointShowList", "");//流程图节点
			 }
			   	  
		    	  
		 }else{
			 map.put("isDelete", "");
			  map.put("pointList", ""); //节点集合
    		  map.put("adminList", ""); //列表集合
    		  map.put("content", "");
    		  map.put("pointShowList", "");//流程图节点
		 }
	    	   
		return map;	
	}
	
	
	
	
	/**
	 * 特殊流程审批
	 * @param pager
	 * @param flowType 1：我的审批   2：确认收款基金审批
	 * @param loginAdmin
	 * @return
	 */
	public Map<String,Object> getRequestAjaxWorkList(Pager pager,String flowType,Admin loginAdmin){
		pager.setAdminId(loginAdmin.getId());
		pager.setCurrentId(loginAdmin.getId());
		if(StringUtils.isNotEmpty(flowType) && WorkFlowContants.CONFIRM_RECEIPT_FLOW.equals(flowType)){
			String [] flowList = {CategoryContants.CONFIRM_RECEIPT_WORKFLOW_ID};//收款确认流程
			pager.setFlowList(flowList);//设置流程集合
		}else if(StringUtils.isNotEmpty(flowType) && WorkFlowContants.DEPLOYMENT_FLOW.equals(flowType)){
			pager.setFlowList(CategoryContants.REQUIREMENT_APPROVE_ARRAY);//设置流程集合
		}else{
			pager.setFlowList(CategoryContants.REQUIREMENT_APPROVE_ARRAY);//设置流程集合
		}
		pager.setFlowType(WorkFlowContants.INSIDE_FLOW);//内置流程类型
	
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("rows", getWorkData(pager)); 
        map.put("total", pager.getTotalCount()); 
      
		return  map;
	}
	
	
	/**
	 * 工作流流程
	 * @param pager
	 * @param status  4：工作审批  3：我的查询  2：我的已完成工作 1：我的待审批工作  null:进展中的工作
	 * @param loginAdmin
	 * @return
	 */
	public Map<String,Object> getAjaxWorkList(Pager pager,String status,Admin loginAdmin){
		//查询条件
		Map<String,Object> param = new HashMap<String,Object>();//精确查询map
		Map<String,Object> params = new HashMap<String,Object>();//模糊查询map
		if(StringUtils.isNotEmpty(status)){
			if(WorkFlowContants.WORK_CHECK.equals(status)){//查询我的审核
				param.put("isComplete", WorkFlowContants.WAIT);//待办状态
				params.put("currentId", loginAdmin.getId());//模糊查询审批人
			}else if(WorkFlowContants.WAIT.equals(status) || WorkFlowContants.COMPLETE.equals(status) ){//查询我的工作
				param.put("isComplete", status);
				param.put("admin.id",loginAdmin.getId());
			}else if(WorkFlowContants.WORK_QUERY.equals(status)){
				pager.setAdminId(loginAdmin.getId());
				pager.setCurrentId(loginAdmin.getId());
			}
		//	pager.setFlowList(CategoryContants.REQUIREMENT_APPROVE_ARRAY);//设置流程集合
		//	pager.setFlowType(WorkFlowContants.NORMAL_FLOW);//正常流程类型
		}else{//进展中的工作
			param.put("isComplete", WorkFlowContants.WAIT);//待办状态  
		}
		
		pager.setNumberParam(param);
		pager.setParam(params);
	
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("rows", getWorkData(pager)); 
        map.put("total", pager.getTotalCount()); 
        

        return  map;
	}
	
	
	public JSONArray getWorkData(Pager pager){
		pager = findPager(pager);
		List<Work> alist = (List<Work>) pager.getResult();
			
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			Work temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId());
	        map.put("modifyDate", temp.getModifyDate());
	        if(temp.getCurrentPointName() != null){
	        	map.put("pointName", temp.getCurrentPointName());
	        }else{
	        	map.put("pointName", "");
	        }
	        if(StringUtils.isEmpty(temp.getStatus())){
	        	map.put("status", "");
	        	map.put("jsStatus", "0");
	        }else{
	        	map.put("jsStatus", temp.getStatus());
	        	if(temp.getStatus().equals("1")){
	        		map.put("status", "取消");
	        	}else if(temp.getStatus().equals("2")){
	        		map.put("status", "正常结束");
	     	        map.put("pointName", "-");
	        	}else if(temp.getStatus().equals("3")){
	        		map.put("status", "待"+temp.getCurrentAdminName()+"审批");
	        	}else if(temp.getStatus().equals("4")){
	        		map.put("status", "被撤回");
	        	}else{
	        		map.put("status", "");
	        	}
	        }
	        
	        map.put("workStatu", temp.getStatus());//工作状态 
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
	

    
}
