package com.rzrk.dao;

import java.util.List;
import java.util.Set;

import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.vo.queryhistory.DataItem;

public interface QueryHistoryDao extends BaseDao<QueryHistory, String> {
	
	public List<DataItem> getQueryHistoryList(Admin admin);
	
	public List<QueryHistory> getQueryHistoryTree4Viewlayer(Admin admin);
	
	 public QueryHistory findAdminQuery(String queryName,String adminId);

}
