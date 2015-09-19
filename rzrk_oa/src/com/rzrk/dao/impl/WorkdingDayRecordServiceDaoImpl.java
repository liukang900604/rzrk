package com.rzrk.dao.impl;

import org.springframework.stereotype.Repository;

import com.rzrk.dao.WorkdingDayRecordServiceDao;
import com.rzrk.entity.WorkingDayRecord;

@Repository("workdingDayRecordServiceDaoImpl")
public class WorkdingDayRecordServiceDaoImpl extends BaseDaoImpl<WorkingDayRecord, String> implements WorkdingDayRecordServiceDao{

}
