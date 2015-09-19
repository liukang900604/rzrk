package com.rzrk.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.Admin;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.entity.UserPlanRemark;
import com.rzrk.entity.UserPlanRemark.RemarkType;
import com.rzrk.service.AdminService;
import com.rzrk.service.UserPlanLogService;
import com.rzrk.service.UserPlanRemarkService;
import com.rzrk.util.date.DateUtils;

@ParentPackage("admin")
public class UserPlanRemarkAction extends BaseAdminAction{
	private static final long serialVersionUID = 8784555891643520648L;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "userPlanRemarkServiceImpl")
	private UserPlanRemarkService userPlanRemarkService;
	
	@Resource(name = "userPlanLogServiceImpl")
	private UserPlanLogService userPlanLogService;
	
	private UserPlanRemark userPlanRemark;
	
	public UserPlanRemark getUserPlanRemark() {
		return userPlanRemark;
	}

	public void setUserPlanRemark(UserPlanRemark userPlanRemark) {
		this.userPlanRemark = userPlanRemark;
	}
	
	/**
	 * 项目问题描述增加方法
	 * @return
	 */
	public String saveProjectRemark() {
		Admin logAdmin = adminService.getLoginAdmin();
		userPlanRemark.setAdmin(logAdmin);
		userPlanRemark.setType(RemarkType.项目); 
		userPlanRemarkService.save(userPlanRemark);
		
		UserPlanLog upl = new UserPlanLog();
		upl.setOperator(getLoginAdmin());
		upl.setContent("增加问题: "+userPlanRemark.getContent());
		upl.setProject(userPlanRemark.getProject());
		userPlanLogService.save(upl);
		return ajax(Status.success, "添加成功!");
	}
	
	
	/**
	 * 项目问题描述更新方法
	 * @return
	 */
	public String updateProjectRemark() {
		
		String content = this.getRequest().getParameter("content");
		if(StringUtils.isEmpty(id)){
			return ajax(Status.error, "更新失败!");
		}
		UserPlanRemark tempRemark = userPlanRemarkService.get(id);
		if(tempRemark == null){
			return ajax(Status.error, "更新失败!");
		}
		UserPlanLog upl = new UserPlanLog();
		upl.setOperator(getLoginAdmin());
		upl.setContent("更新问题描述: "+tempRemark.getContent()+"--"+content);
		upl.setProject(tempRemark.getProject());
		userPlanLogService.save(upl);
		tempRemark.setContent(content);
		userPlanRemarkService.update(tempRemark);
		return ajax(Status.success, "更新成功!");
	}
	
	
	/**
	 * 项目问题描述删除方法
	 * @return
	 */
	public String deleteProjectRemark() {
		try {
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(getLoginAdmin());
			upl.setContent("删除问题: "+userPlanRemarkService.get(id).getContent());
			upl.setProject(userPlanRemarkService.get(id).getProject());
			userPlanLogService.save(upl);
			userPlanRemarkService.delete(id);
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return ajax(Status.error, "删除失败!"+e.getMessage());
		}
	}
	/**
	 * 获取所有项目问题描述
	 * @return
	 */
	public String getProjectRemarkAjaxList() {
		List<UserPlanRemark> list = userPlanRemarkService.getUserPlanRemarkByProject(id);
		JSONArray jsonObj = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			UserPlanRemark temp = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", temp.getId());
			map.put("adminName", temp.getAdmin().getName());
			map.put("createDate", DateUtils.formatDate(temp.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			map.put("content", temp.getContent());
			jsonObj.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", jsonObj);

		return ajax(map);
	}
	
	/**
	 * 计划问题描述增加方法
	 * @return
	 */
	public String save() {
		Admin logAdmin = adminService.getLoginAdmin();
		userPlanRemark.setAdmin(logAdmin);
		userPlanRemark.setType(RemarkType.计划);
		userPlanRemarkService.save(userPlanRemark);
		
		UserPlanLog upl = new UserPlanLog();
		upl.setOperator(getLoginAdmin());
		upl.setContent("增加问题: "+userPlanRemark.getContent());
		upl.setUserPlan(userPlanRemark.getUserPlan());
		userPlanLogService.save(upl);
		return ajax(Status.success, "添加成功!");
	}
	
	/**
	 * 计划问题描述删除方法
	 * @return
	 */
	public String delete() {
		try {
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(getLoginAdmin());
			upl.setContent("删除问题: "+userPlanRemarkService.get(id).getContent());
			upl.setUserPlan(userPlanRemarkService.get(id).getUserPlan());
			userPlanLogService.save(upl);
			userPlanRemarkService.delete(id);
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return ajax(Status.error, "删除失败!"+e.getMessage());
		}
	}
	
	/**
	 * 获取所有计划问题描述
	 * @return
	 */
	public String getAjaxList() {
		List<UserPlanRemark> list = userPlanRemarkService.getUserPlanRemark(id);
		JSONArray jsonObj = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			UserPlanRemark temp = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", temp.getId());
			map.put("createDate", DateUtils.formatDate(temp.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
			map.put("adminName", temp.getAdmin().getName());
			map.put("content", temp.getContent());
			jsonObj.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", jsonObj);

		return ajax(map);
	}
}
