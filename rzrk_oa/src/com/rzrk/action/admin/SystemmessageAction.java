/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ModelDriven;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.SystemMessage;
import com.rzrk.entity.SystemMessage.RedType;
import com.rzrk.entity.SystemMessage.SystemmessageType;
import com.rzrk.entity.Work;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminService;
import com.rzrk.service.MailService;
import com.rzrk.service.SmsService;
import com.rzrk.service.SystemMessageService;
import com.rzrk.service.WorkService;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class SystemmessageAction extends BaseAdminAction implements
		ModelDriven<SystemMessage> {

	private static final long serialVersionUID = 8784555891643520648L;

	private SystemMessage systemMessage = new SystemMessage();

	@Resource(name = "systemMessageServiceImpl")
	private SystemMessageService systemService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	
	/**
	 * 我的工作service
	 */
	@Resource
	private WorkService  workService;

	private List<Admin> adminList = new ArrayList<Admin>(); 

	/**
	 * 删除方法
	 * @return
	 */
	public String delete() {
		try {
			systemService.delete(ids);
			return ajax(Status.success, "删除成功!");

		} catch (Exception e) {
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}

	
	/**
	 * 标为已读
	 * @return
	 */
	public String markSystemMessage() {
		try {
			systemService.markType(ids);
			return ajax(Status.success, "标记已读成功!");

		} catch (Exception e) {
			return ajax(Status.error, "删除失败，标记已读失败!");
		}
	}

	/**
	 * 
	 */
	public String deleteRead() {
		try {
			for (String id : ids) {
				systemMessage = systemService.get(id);
				systemMessage.setIsDelete("true");
				systemService.update(systemMessage);
			}
			return ajax(Status.success, "删除成功!");

		} catch (Exception e) {
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}
	/**
	 * 
	 */
	public String getCount(){
		 int i = systemService.getUnredCount(getLoginAdmin().getId());
		 return ajax(i);
	}
	
	/**
	 * 增加方法
	 * @return
	 */
	public String save() {
		Admin logAdmin = adminService.getLoginAdmin();
		systemMessage.setCreateTime(new Date());
		systemMessage.setRedType(RedType.未读);
		systemMessage.setLoginAdmin(logAdmin);
		List<Admin> list = new ArrayList<Admin>();
		for (int i = 0; i < getAdminList().size(); i++) {
			Admin admin = adminService.get(getAdminList().get(i).getId());
			list.add(admin);
		}
		if (list.size()==0) {
			return ajax(Status.error, "请选择通知人!");
		}
		systemMessage.setToMessagAdmin(list);
		systemService.save(systemMessage);
		//发送邮件
		if(StringUtils.isNotEmpty(systemMessage.getIsEmail())){
			if(WorkFlowContants.SEND.equals(systemMessage.getIsEmail())){
				try{
					for(Admin admin :list){
						if(StringUtils.isNotEmpty(admin.getEmail())){
							mailService.sendMail("消息发布",systemMessage.getContext(),admin.getEmail());
						}
					}
				}catch(Exception e){
					
				}
				
			}
		}
		return ajax(Status.success, "添加成功!");
	}
	
	/**
	 * 发送短信与邮件
	 */
/*	private void sendMessage(List<Admin> adminList, String status) {
		if (status == null) {
			return;
		} else {
			String[] statu = status.split(",");
			for (String string : statu) {
				if (string.equals("0")) {
					for (Admin admin : adminList) {
						try {
							smsService.sendSmsTest("", admin.getUsername(),
									admin.getPassword(), admin.getTelephone());
						} catch (RzrkException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					for (Admin admin : adminList) {
						mailService.sendSmtpTestMail("", "", 8088,
								admin.getUsername(), admin.getPassword(),
								admin.getEmail());
					}
				}
			}
		}
	}*/
	/**
	 * 消息查看
	 * @return
	 */
	public String edit() {
		String id = systemMessage.getId();
		systemMessage = systemService.get(id);
		systemMessage.setRedType(RedType.已读);
		systemService.update(systemMessage);
		List<SystemMessage> systemMessageList = systemService.getAllList();
		if (null != systemMessage) {
			systemMessageList.remove(systemMessage);
		}
		getRequest().setAttribute("messagetype", systemMessage.getType());
		getRequest().setAttribute("admin", systemMessage.getLoginAdmin().getName());
		getRequest().setAttribute("redtype", systemMessage.getRedType());
		return "show";
	}

	/**
	 * 消息查看
	 * @return
	 */
	public String query() {
		String id = systemMessage.getId();
		systemMessage = systemService.get(id);
		systemMessage.setRedType(RedType.已读);
		systemService.update(systemMessage);
		List<SystemMessage> systemMessageList = systemService.getAllList();
		if (null != systemMessage) {
			systemMessageList.remove(systemMessage);
		}
		getRequest().setAttribute("messagetype", systemMessage.getType());
		getRequest().setAttribute("admin", systemMessage.getLoginAdmin().getName());
		getRequest().setAttribute("redtype", systemMessage.getRedType());
		return "showquery";
	}
	/**
	 * 获取登录人员已发消息
	 * @return
	 */
	public String getMessage() {
		processAjaxPagerRequestParameter();
		Admin logAdmin = adminService.getLoginAdmin();//获取当前登录用户
		List<SystemMessage> list = systemService.getMessage(logAdmin.getId(),pager);

		JSONArray jsonObj = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SystemMessage temp = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", temp.getId());
			map.put("title", temp.getTitle());
			map.put("admin", temp.getLoginAdmin().getName());
			map.put("type", temp.getType().name());
			map.put("context", temp.getContext());
			map.put("redtype", temp.getRedType().name());
			map.put("createDate", DateUtils.formatDate(temp.getCreateTime(), "yyyy-MM-dd"));
			jsonObj.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("total", pager.getTotalCount());
		map.put("rows", jsonObj);

		return ajax(map);
	}
	
	public String checkWorkData(){
		Admin loginAdmin = this.getLoginAdmin();
		String messageId = this.getRequest().getParameter("messageId");
		if(StringUtils.isEmpty(messageId)){
			return ajax(Status.error, "通知信息不存在!");
		}
		
		systemMessage = systemService.get(messageId);
		systemMessage.setRedType(RedType.已读);
		systemService.update(systemMessage);
		
		SystemMessage systemMessage = systemService.get(messageId);
		if(systemMessage == null){
			return ajax(Status.error, "通知信息不存在!");
		}
		if(StringUtils.isEmpty(systemMessage.getWorkId())){
			return ajax(Status.error, "未找到通知中的工作!");
		}
		Work work = workService.get(systemMessage.getWorkId());
		try{
			if(!WorkFlowContants.CHECK_STATU.equals(work.getStatus())){
				return ajax(Status.error, "工作已经被处理!");
			}else{
				if(StringUtils.isNotEmpty(work.getCurrentId())){//验证审批权限
		    		if(work.getCurrentId().indexOf(loginAdmin.getId()) == -1){
		    			return ajax(Status.error, "工作已处理!");
		    		}
		    	}else{
		    		return ajax(Status.error, "工作已经被处理!");
		    	}
				return ajax(Status.success, work.getId());
			}
		}catch(Exception e){
			return ajax(Status.error, "未找到通知中的工作!");
		}
	}
	
	
	public String checkWorkBackData(){
		String messageId = this.getRequest().getParameter("messageId");
		if(StringUtils.isEmpty(messageId)){
			return ajax(Status.error, "通知信息不存在!");
		}
		
		systemMessage = systemService.get(messageId);
		systemMessage.setRedType(RedType.已读);
		systemService.update(systemMessage);
		
		SystemMessage systemMessage = systemService.get(messageId);
		if(systemMessage == null){
			return ajax(Status.error, "通知信息不存在!");
		}
		if(StringUtils.isEmpty(systemMessage.getWorkId())){
			return ajax(Status.error, "未找到通知中的工作!");
		}
		Work work = workService.get(systemMessage.getWorkId());
		try{
			if(!WorkFlowContants.BACK_STATU.equals(work.getStatus())){
				return ajax(Status.error, "工作已经被处理!");
			}
			return ajax(Status.success, work.getId());
		}catch(Exception e){
			return ajax(Status.error, "未找到通知中的工作!");
		}
	}
	
	/**
	 * 获取所有消息数据
	 * @return
	 */
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
		Admin logAdmin = adminService.getLoginAdmin();//获取当前登录用户
		List<SystemMessage> list = systemService.getAdminMessage(logAdmin.getId(),pager);
/*		processAjaxPagerRequestParameter();
		pager = systemService.findPager(pager);
		List<SystemMessage> List = (List<SystemMessage>) pager.getResult();
*/
		JSONArray jsonObj = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SystemMessage temp = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", temp.getId());
			map.put("title", temp.getTitle());
			map.put("admin", temp.getLoginAdmin().getName());
			map.put("type", temp.getType().name());
			map.put("context", temp.getContext());
			map.put("redtype", temp.getRedType().name());
			map.put("createDate", DateUtils.formatDate(temp.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			map.put("internal", temp.getIsInternal());
			if(StringUtils.isNotEmpty(temp.getWorkId())){
				Work work = workService.get(temp.getWorkId());
				if(work != null){
					if(!(WorkFlowContants.CHECK_STATU.equals(work.getStatus()) || WorkFlowContants.BACK_STATU.equals(work.getStatus()))){
						map.put("workId", "");
					}else{
						if(StringUtils.isNotEmpty(temp.getCurrentPointId())){
							if(temp.getCurrentPointId().equals(work.getCurrentPointId())){//当前节点的消息
								map.put("workId",temp.getWorkId());
							}else{
								map.put("workId","");
							}
						}else{//兼容老数据 
							map.put("workId",temp.getWorkId());
			    		
						}
						
					}
				}else{
					map.put("workId","");
				}
			
				
			}else{
				map.put("workId", "");
			}
			jsonObj.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("total", pager.getTotalCount());
		map.put("rows", jsonObj);

		return ajax(map);
	}
	/**
	 * 查看跳转
	 * @return
	 */
	public String select() {
		return "select";
	}
	/**
	 * 消息页面跳转
	 * @return
	 */
	public String list() {
		// pager = logService.findPager(pager);
		return LIST;
	}
	
	/**
	 * 增加页面跳转
	 * @return
	 */
	public String add() {

		SystemmessageType[] sys = systemMessage.getType().values();

		getRequest().setAttribute("t", sys);

		return INPUT;
	}

	/**
	 * 查看页面跳转
	 * @return
	 */
	public String show() {
		return "query";
	}

	/**
	 * 
	 * @return
	 */
	public String checkDeparmentName() {
		String title = systemMessage.getTitle();
		if (systemService.isExistByName(title)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}

	@Override
	public SystemMessage getModel() {
		// TODO Auto-generated method stub
		return systemMessage;
	}
	
	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

}