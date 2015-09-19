package com.rzrk.service;

import java.util.List;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.UserPlan;

public interface UserPlanService extends BaseService<UserPlan, String> {

	/**
	 * TODO:Please add method comments
	 * @param id
	 * @return
	 * @author songkez
	 * @since  2015-6-10 
	 */
	List<UserPlan> getListByProjectId(String id,Pager pager);

	/**
	 * TODO:Please add method comments
	 * @param userPlan
	 * @param persistent
	 * @author songkez
	 * @param admin 
	 * @since  2015-6-23 
	 */
	void update(UserPlan userPlan,List<String> requireIds, UserPlan persistent, Admin admin);

	boolean deleteByUserPlanId(String[] ids);

	public void save(UserPlan userPlan,List<String> requireIds);
	
	/**
	 * 需求相关的计划
	 * @param userPlanId
	 * @return
	 */
	public List<UserPlan> getRequireRelation(String rowId);
	
	public void addPlansForRequire(String requireId,String[] userPlanIds);
	
	
	public List<UserPlan>  getUserPlanByProjectId(String projectId);


}
