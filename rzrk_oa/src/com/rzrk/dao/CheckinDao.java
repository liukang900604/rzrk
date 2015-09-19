package com.rzrk.dao;

import java.util.Date;
import java.util.List;

import com.rzrk.entity.Admin;
import com.rzrk.entity.CheckinLog;

public interface CheckinDao extends BaseDao<CheckinLog, String> {

	public CheckinLog getLatestCheckinTime(String id);
	
	public List<CheckinLog> getCheckinLogNotCheckedList(String date);
	public List<Admin>  getAdminNotCheckin(String date);

}
