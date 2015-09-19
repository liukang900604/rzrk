package com.rzrk.service;

import java.util.List;

import com.rzrk.entity.UserPlanRemark;


public interface UserPlanRemarkService extends BaseService<UserPlanRemark, String>{

	List<UserPlanRemark> getUserPlanRemark(String id);

	List<UserPlanRemark> getUserPlanRemarkByProject(String id);

}
