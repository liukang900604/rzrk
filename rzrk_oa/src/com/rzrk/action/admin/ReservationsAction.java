package com.rzrk.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.Article;
import com.rzrk.entity.Reservations;
import com.rzrk.service.ReservationsService;
import com.rzrk.util.date.DateUtils;

public class ReservationsAction extends BaseAdminAction{

	/**
	 * 后台Action类，认购
	 */
	private static final long serialVersionUID = -1432458240338208824L;

	private Reservations reservations;

	@Resource(name = "reservationsServiceImpl")
	private ReservationsService reservationsService;

	public Reservations getReservations() {
		return reservations;
	}

	public void setReservations(Reservations reservations) {
		this.reservations = reservations;
	}

	// 列表
	public String list() {
		//pager = reservationsService.findPager(pager);
		return LIST;
	}
	
	
	public String getAjaxList(){
		processAjaxPagerRequestParameter();
		pager = reservationsService.findPager(pager);
		List<Reservations> rList = (List<Reservations>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < rList.size(); i++ ){
		    Reservations temp = rList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("productName", temp.getProduct().getName());
	        map.put("reservAmount",temp.getReservAmount());
	        map.put("contactPerson", temp.getContactPerson());
	        map.put("contactMobile", temp.getContactMobile());
	        map.put("fax", temp.getFax());
	        map.put("email", temp.getEmail());
	        map.put("companyName", temp.getCompanyName());
	        map.put("address", temp.getAddress());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}

	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除订购: ");
		reservationsService.delete(ids);
		logInfo = logInfoStringBuffer.toString();
		return ajax(Status.success, "删除成功!");
	}




}
