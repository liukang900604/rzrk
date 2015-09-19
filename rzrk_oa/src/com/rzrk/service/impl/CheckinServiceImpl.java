package com.rzrk.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import com.rzrk.Constants;
import com.rzrk.dao.CheckinDao;
import com.rzrk.dao.JobDao;
import com.rzrk.dao.WorkdingDayRecordServiceDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.CheckinLog;
import com.rzrk.entity.CheckinLog.CheckStatus;
import com.rzrk.entity.WorkingDayRecord;
import com.rzrk.service.CheckinService;
import com.rzrk.util.DateUtil;

@Service("checkinServiceImpl")
public class CheckinServiceImpl extends BaseServiceImpl<CheckinLog, String> implements CheckinService {

	@Resource(name = "checkinDaoImpl")
     private CheckinDao checkinDao;
	
	@Resource(name = "checkinDaoImpl")
	public void setBaseDao(CheckinDao checkinDao) {
		super.setBaseDao(checkinDao);
	}
	
	@Resource(name = "workdingDayRecordServiceDaoImpl")
	private WorkdingDayRecordServiceDao workdingDayRecordServiceDao;

	@Override
	public CheckinLog getLatestCheckinTime(String id) {
		return checkinDao.getLatestCheckinTime(id);
	}
	
	private static String []timeFt = new String[]{"HH:mm:ss"};
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//TODO 需要做缓存
	public Date getWorkStartTime(){
		Date time=null;
		try {
			time = DateUtils.parseDate(Constants.workStart, timeFt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public Date getWorkEndTime(){
		Date time=null;
		try {
			time = DateUtils.parseDate(Constants.workend, timeFt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	
	public void CheckLastStatus(){
		
		WorkingDayRecord workingDayRecord =  workdingDayRecordServiceDao.getAllList().get(0);
		
		String content = workingDayRecord.getContent();
		HashSet<String> workdaySet = new HashSet<String>();
		try {
			JSONArray daysetArr = JSONArray.fromObject(content);
			int len = daysetArr.size();
			for(int i=0;i<len;i++){
				JSONObject dayset = daysetArr.getJSONObject(i);
				String id = dayset.getString("id");
				if(!StringUtils.isEmpty(id)){
					workdaySet.add(id);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		Date yesterday0 = DateUtils.addDays(new Date(), -1);
		String yesterday0Str = dateFormat.format(yesterday0);
		
		
		Date startTime = getWorkStartTime();
		Date endTime = getWorkEndTime();
		List<CheckinLog> checkinLogList = checkinDao.getCheckinLogNotCheckedList(yesterday0Str);
			
		if(workdaySet.size()==0 || workdaySet.contains(yesterday0Str)){
			for(CheckinLog checkinLog : checkinLogList){
				double lateHours=0; //迟到时间（小时）
				double earlyHours=0; //早退时间（小时）
				double absenteeism=0; //矿工（天）
				int forgetCheckTime=0; //未刷卡次数
				double overtimes=0; //加班时长（小时）
				CheckStatus checkStatus= CheckStatus.正常;//状态
				
				Date checkInTime =null;
				String checkInTimeStr = checkinLog.getCheckinTime();
				Date checkOutTime =null;
				String checkOutTimeStr = checkinLog.getCheckoutTime();
				if(!StringUtils.isEmpty(checkInTimeStr)){
					try {
						checkInTime = DateUtils.parseDate(checkInTimeStr, timeFt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(!StringUtils.isEmpty(checkOutTimeStr)){
					try {
						checkOutTime = DateUtils.parseDate(checkOutTimeStr, timeFt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(checkInTime==null && checkOutTime==null){
					//旷工
					absenteeism=1;
					forgetCheckTime=2;
					checkStatus = CheckStatus.矿工;
				}else if(checkInTime!=null && checkOutTime!=null){
					//是否迟到
					if(checkInTime.after(startTime)){
						lateHours = (checkInTime.getTime() - startTime.getTime()) / 3600000.00;
						checkStatus = CheckStatus.迟到;
					}
					//是否早退
					if(checkOutTime.before(endTime)){
						earlyHours = (endTime.getTime() - checkOutTime.getTime()) / 3600000.00;
						checkStatus = CheckStatus.早退;
					}else if(endTime.before(checkOutTime)){
						//算加班
						double _overtimes = (checkOutTime.getTime() - endTime.getTime() ) / 3600000.00;
						if(_overtimes>1){
							overtimes = _overtimes;
							checkStatus = CheckStatus.加班;
						}
					}
				}else if(checkInTime!=null && checkOutTime==null){
					forgetCheckTime=1;
					checkStatus = CheckStatus.未刷卡;
					//是否迟到
					if(checkInTime.after(startTime)){
						lateHours = (checkInTime.getTime() - startTime.getTime()) / 3600000.00;
					}
				}else{
					// 不存在的情况
				}
				
				checkinLog.setLateHours(lateHours);
				checkinLog.setEarlyHours(earlyHours);
				checkinLog.setAbsenteeism(absenteeism);
				checkinLog.setForgetCheckTime(forgetCheckTime);
				checkinLog.setOvertimes(overtimes);
				checkinLog.setCheckStatus(checkStatus);
				checkinLog.setChecked(true);
				checkinDao.update(checkinLog);
				checkinDao.flush();
			}
			
			
			List<Admin> adminList  = checkinDao.getAdminNotCheckin(yesterday0Str);
			for(Admin admin : adminList){
				CheckinLog checkinLog = new CheckinLog();
				checkinLog.setCheckinDate(yesterday0Str);
				checkinLog.setAbsenteeism(1);
				checkinLog.setForgetCheckTime(2);
				checkinLog.setCheckStatus(CheckStatus.矿工);
				checkinLog.setChecked(true);
				checkinLog.setName(admin.getUsername());
				checkinLog.setAdmin(admin);
				checkinDao.save(checkinLog);
				checkinDao.flush();
			}
		}else{
			for(CheckinLog checkinLog : checkinLogList){
				Date checkInTime =null;
				String checkInTimeStr = checkinLog.getCheckinTime();
				Date checkOutTime =null;
				String checkOutTimeStr = checkinLog.getCheckoutTime();
				if(!StringUtils.isEmpty(checkInTimeStr)){
					try {
						checkInTime = DateUtils.parseDate(checkInTimeStr, timeFt);
					} catch (ParseException e) {
						checkInTime = startTime;
					}
				}
				if(!StringUtils.isEmpty(checkOutTimeStr)){
					try {
						checkOutTime = DateUtils.parseDate(checkOutTimeStr, timeFt);
					} catch (ParseException e) {
						checkOutTime = endTime;
					}
				}
				double _overtimes = (checkOutTime.getTime() - checkInTime.getTime() ) / 3600000.00;
				checkinLog.setOvertimes(_overtimes);
				checkinLog.setCheckStatus(CheckStatus.加班);
				checkinLog.setChecked(true);
				checkinDao.update(checkinLog);
				checkinDao.flush();
			
			}
			
			
		}
		
		
		
	}
     
     
}
