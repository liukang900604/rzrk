package com.rzrk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rzrk.dao.WorkdingDayRecordServiceDao;
import com.rzrk.entity.WorkingDayRecord;
import com.rzrk.service.WorkdingDayRecordService;

@Service("WorkdingDayRecordServiceImpl")
public class WorkdingDayRecordServiceImpl extends BaseServiceImpl<WorkingDayRecord, String> implements WorkdingDayRecordService {
	
	@Resource(name = "workdingDayRecordServiceDaoImpl")
	private WorkdingDayRecordServiceDao workdingDayRecordServiceDao;
	
	@Resource(name = "workdingDayRecordServiceDaoImpl")
	public void setBaseDao(WorkdingDayRecordServiceDao workdingDayRecordServiceDao) {
		super.setBaseDao(workdingDayRecordServiceDao);
	}
}
