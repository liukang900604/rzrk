package com.rzrk.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.rzrk.dao.AdminDao;
import com.rzrk.service.CheckinService;

@Component("checkinTask")
public class CheckinTask{
	@Resource(name="checkinServiceImpl")
	CheckinService checkinService;
	
	public void checkLast(){
		checkinService.CheckLastStatus();     
	}
}
