package com.rzrk.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;

import com.rzrk.Constants;
import com.rzrk.entity.CheckinLog;
import com.rzrk.service.CheckinService;
import com.rzrk.util.DateUtil;
import com.rzrk.util.GeoUtil;

public class CheckinAction extends BaseAdminAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2358090654798032204L;
	
	@Resource(name="checkinServiceImpl")
	private CheckinService checkinService;
	
	
	
	

	/**
	 * @return
	 */
	public String checkin(){
		String x = getRequest().getParameter("x");
		String y = getRequest().getParameter("y");
		String mac = getRequest().getParameter("mac");
		
		
		if(getLoginAdmin().getMacAddress().isEmpty()){
			return ajax(Status.error,"请联系人员绑定mac地址");
		}
		
		if(!getLoginAdmin().getMacAddress().equals(mac)){
			return ajax(Status.error,"请用你绑定好的手机进行打卡");
		}
		
		//判断是否在有效打卡范围内
		Double actualDistance = GeoUtil.getDistanceOfMeter(Double.valueOf(x) ,Double.valueOf(y),Constants.companyLocationX, Constants.companyLocationY);
		
		
		if(actualDistance > Constants.validDistance){
			return ajax(Status.error,"打卡位置无效");
		}else{
			CheckinLog latestcheckinLog = checkinService.getLatestCheckinTime(getLoginAdmin().getId());
			String latestCheckTime = "";
			CheckinLog checkinLog = new CheckinLog();
			String currentDate = DateUtil.getDate();
			if(null == latestcheckinLog || !latestcheckinLog.getCheckinDate().equals(currentDate)){
				
				checkinLog.setAdmin(getLoginAdmin());
				checkinLog.setName(getLoginAdmin().getName());
				
				
				checkinLog.setCheckinDate(currentDate);
				checkinLog.setCheckinTime(DateUtil.getTime());
				checkinLog.setCheckinX(Double.valueOf(x));
				checkinLog.setCheckinY(Double.valueOf(y));
				checkinService.save(checkinLog);
			}else{
				checkinLog.setCheckoutTime(DateUtil.getTime());
				checkinLog.setCheckoutX(Double.valueOf(x));
				checkinLog.setCheckoutY(Double.valueOf(y));
				CheckinLog persistent = checkinService.load(latestcheckinLog.getId());
				BeanUtils.copyProperties(checkinLog, persistent, new String[] {"id", "createDate", "modifyDate", "checkinTime", "admin","checkinDate","checkinX", "checkinY","name"});

				checkinService.update(latestcheckinLog);
				if(latestcheckinLog.getCheckoutTime() == null || latestcheckinLog.getCheckoutTime().trim().length() == 0){
					latestCheckTime = latestcheckinLog.getCheckinDate() + " "+ latestcheckinLog.getCheckinTime();
				}else{
					latestCheckTime = latestcheckinLog.getCheckinDate() + " "+ latestcheckinLog.getCheckoutTime();
				}
			} 
			
			return ajax(Status.success,latestCheckTime);
		}
	}
	
	
	public String getAjaxList() {
		
		processAjaxPagerRequestParameter();
		pager = checkinService.findPager(pager);
		List<CheckinLog> logList = (List<CheckinLog>) pager.getResult();

		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < logList.size(); i++ ){
			CheckinLog temp = logList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); //id
	        map.put("name", temp.getName()); //姓名
	        map.put("checkinDate",temp.getCheckinDate()); // 日期
	        map.put("checkinTime",temp.getCheckinTime()); //上班时间
	        map.put("checkoutTime",temp.getCheckoutTime()); //下班时间
	        map.put("checkinLocation",temp.getCheckinX()+", "+temp.getCheckinY()); //上班打卡位置
	        map.put("checkoutLocation",temp.getCheckoutX() + "," + temp.getCheckoutY()); //下班打卡位置
	        if(null == temp.getCheckStatus()){
	        	map.put("checkStatus",""); //状态
	        }else{
	        	map.put("checkStatus",temp.getCheckStatus()); //状态
	        }
	        
	        map.put("lateHours",temp.getLateHours()); //迟到（小时）
	        map.put("absenteeism",temp.getAbsenteeism()); //矿工（天）
	        map.put("forgetCheckTime",temp.getForgetCheckTime()); //未刷卡次数
	        map.put("overtimes",temp.getOvertimes()); //加班（小时）
	        
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}
	
	public String checkinList(){
		return "checkinList";
	}
	
	public String workingdayList(){
		return "workingdayList";
	}

}
