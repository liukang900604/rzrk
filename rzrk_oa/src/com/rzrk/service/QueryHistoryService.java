package com.rzrk.service;

import java.util.List;

import com.rzrk.entity.Admin;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.queryhistory.ViewlayerItem;

public interface QueryHistoryService extends BaseService<QueryHistory, String> {
	public List<TreeItem> getQueryHistoryTree(Admin admin);
	public List<ViewlayerItem> getQueryHistoryTree4Viewlayer(Admin admin);
	public boolean checkExist(Admin admin,QueryHistory queryHistory,String id);

}
