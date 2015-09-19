package com.rzrk.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.WorkingDayRecord;
import com.rzrk.service.WorkdingDayRecordService;

@ParentPackage("admin")
public class WorkdingDayRecordAction extends BaseAdminAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2423786282233714472L;

	@Resource(name="WorkdingDayRecordServiceImpl")
	private WorkdingDayRecordService workdingDayRecordService;
	
	private WorkingDayRecord workingDayRecord;
	
	
	public WorkingDayRecord getWorkingDayRecord() {
		return workingDayRecord;
	}

	public void setWorkingDayRecord(WorkingDayRecord workingDayRecord) {
		this.workingDayRecord = workingDayRecord;
	}
	
	public String edit(){
		List<WorkingDayRecord> workingDayRecordingList = workdingDayRecordService.getAllList();
		if(null != workingDayRecordingList && workingDayRecordingList.size() > 0){
			workingDayRecord = workingDayRecordingList.get(0);
		}else{
			workingDayRecord = new WorkingDayRecord();
		}
		return "edit";
	}

	public String save(){
		List<WorkingDayRecord> workingDayRecordingList = workdingDayRecordService.getAllList();
		if(null != workingDayRecordingList && workingDayRecordingList.size() > 0){
			workingDayRecord = workingDayRecordingList.get(0);
		}else{
			workingDayRecord = new WorkingDayRecord();
		}
		workingDayRecord.setContent(getRequest().getParameter("content"));
		workdingDayRecordService.save(workingDayRecord);
		return ajax(Status.success,"保存成功");
	}

}
