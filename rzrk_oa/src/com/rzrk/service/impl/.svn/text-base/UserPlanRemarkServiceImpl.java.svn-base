package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rzrk.dao.UserPlanRemarkDao;
import com.rzrk.entity.UserPlanRemark;
import com.rzrk.service.UserPlanRemarkService;

@Service("userPlanRemarkServiceImpl")
public class UserPlanRemarkServiceImpl  extends BaseServiceImpl<UserPlanRemark, String> implements UserPlanRemarkService{
	@Resource(name = "userPlanRemarkDaoImpl")
	private UserPlanRemarkDao userPlanRemarkDao;
	@Resource(name = "userPlanRemarkDaoImpl")
	public void setBaseDao(UserPlanRemarkDao userPlanRemarkDao) {
		super.setBaseDao(userPlanRemarkDao);
	}
	@Override
	public List<UserPlanRemark> getUserPlanRemark(String id) {
		// TODO Auto-generated method stub
		return userPlanRemarkDao.getUserPlanRemark(id);
	}
	@Override
	public List<UserPlanRemark> getUserPlanRemarkByProject(String id) {
		// TODO Auto-generated method stub
		return userPlanRemarkDao.getUserPlanRemarkByProject(id);
	}


}
