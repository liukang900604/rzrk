package com.rzrk.common.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;

@Component("contractParserDepartment")
public class ContractParserDepartment implements ContractParser {
	private static final String CACHE_PRE = "contractParser.department";
	@Resource(name = "deparmentServiceImpl")
	DeparmentService deparmentService;

	@Resource(name = "cacheManager")
	GeneralCacheAdministrator generalCacheAdministrator;

	@Override
	public String parse(String text) {
		if(StringUtils.isEmpty(text)) return null;
		String key = CACHE_PRE+".text." + text;
		String value = null;
		try {
			value = (String) generalCacheAdministrator.getFromCache(key);
		} catch (NeedsRefreshException e) {
			boolean updateSucceeded = false;
			try {
				Deparment deparment = deparmentService.getByName(text);
				if (deparment != null) {
					value = deparment.getId();
					generalCacheAdministrator.putInCache(key, value);
					updateSucceeded = true;
				}
			} catch (Exception ex) {
				e.printStackTrace();
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator.cancelUpdate(key);
				}
			}
		}

		return value;
	}

	@Override
	public List<String> parseList(String text) {
		ArrayList<String> list = new ArrayList<String>();
		if(StringUtils.isEmpty(text)) return list;
		List<Deparment> deparmentList = deparmentService.getListByName(text);
		for(Deparment deparment : deparmentList){
			list.add(deparment.getId());
		}
		return list;
	}


	@Override
	public String format(String value) {
		if(StringUtils.isEmpty(value)) return null;
		String key = CACHE_PRE+".value." + value;
		String text = null;
		try {
			text = (String) generalCacheAdministrator.getFromCache(key);
		} catch (NeedsRefreshException e) {
			boolean updateSucceeded = false;
			try {
				Deparment deparment = deparmentService.get(value);
				if (deparment != null) {
					text = deparment.getName();
					generalCacheAdministrator.putInCache(key, text);
					updateSucceeded = true;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator.cancelUpdate(key);
				}
			}
		}
		return text;
	}

	@Override
	public String getName() {
		return "部门";
	}

	public void removeCache(Deparment deparment){
		generalCacheAdministrator.removeEntry(CACHE_PRE+".text." + deparment.getName());
		generalCacheAdministrator.removeEntry(CACHE_PRE+".value." + deparment.getId());
	}

}
