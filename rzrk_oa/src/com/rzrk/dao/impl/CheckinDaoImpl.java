package com.rzrk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rzrk.dao.CheckinDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.CheckinLog;

@Repository("checkinDaoImpl")
public class CheckinDaoImpl extends BaseDaoImpl<CheckinLog, String> implements CheckinDao{

	@Override
	public CheckinLog getLatestCheckinTime(String id) {
		String hql = "from CheckinLog as checkinLog where checkinLog.admin.id = :id order by checkinDate desc";
		return (CheckinLog) getSession().createQuery(hql).setParameter("id", id).setMaxResults(1).uniqueResult();

	}

	
	public List<CheckinLog> getCheckinLogNotCheckedList(String date){
		String hql = "from CheckinLog as cl where cl.checkinDate = :checkinDate and cl.checked = :checked)";
		return getSession().createQuery(hql).setString("checkinDate", date).setBoolean("checked", false).list();
	};
	public List<Admin> getAdminNotCheckin(String date){
		String hql = "from Admin as ad where not exists(select cl.id from CheckinLog cl where cl.admin.id = ad.id and cl.checkinDate = :checkinDate)";
		return getSession().createQuery(hql).setString("checkinDate", date).list();
	};

}
