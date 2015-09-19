package com.rzrk.common.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.entity.Admin;
import com.rzrk.service.AdminService;

@Component("contractParserUser")
public class ContractParserUser implements ContractParser {
	private static final String CACHE_PRE = "contractParser.user";
	
	@Resource(name = "adminServiceImpl")
	AdminService adminService;

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
				Admin admin = adminService.getAdminByName(text);
				if (admin != null) {
					value = admin.getId();
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
		List<Admin> adminList = adminService.getAdminListByName(text);
		for(Admin admin : adminList){
			list.add(admin.getId());
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
				Admin admin = adminService.get(value);
				if (admin != null) {
					text = admin.getName();
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
		return "用户名";
	}


	public void removeCache(Admin admin){
		generalCacheAdministrator.removeEntry(CACHE_PRE+".text." + admin.getName());
		generalCacheAdministrator.removeEntry(CACHE_PRE+".value." + admin.getId());
	}

}
