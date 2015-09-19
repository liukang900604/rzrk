package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rzrk.dao.ProjectDao;
import com.rzrk.dao.QueryHistoryDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.service.QueryHistoryService;
import com.rzrk.vo.queryhistory.DataItem;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.queryhistory.ViewlayerItem;

@Service("queryHistoryServiceImpl")
public class QueryHistoryServiceImpl extends BaseServiceImpl<QueryHistory, String> implements QueryHistoryService{
	
	@Resource(name = "queryHistoryDaoImpl")
	private QueryHistoryDao queryHistoryDao;
	
	@Resource(name = "queryHistoryDaoImpl")
	public void setBaseDao(QueryHistoryDao queryHistoryDao) {
		super.setBaseDao(queryHistoryDao);
	}
	
	
	public List<TreeItem> getQueryHistoryTree(Admin admin){
		List<TreeItem> treeItemList= new ArrayList<TreeItem>();
		
		List<DataItem> itemList = queryHistoryDao.getQueryHistoryList(admin);
		
		TreeItem currRootContractCategory=null;
		TreeItem currContractCategory=null;
		boolean first=true;
		for(DataItem item : itemList){
			if(currRootContractCategory==null || !StringUtils.equals(item.getRcId(),currRootContractCategory.getId())){
				currRootContractCategory = new TreeItem();
				currRootContractCategory.setId(item.getRcId());
				currRootContractCategory.setText(item.getRcName());
				if(first)currRootContractCategory.putState("opened", true);
				treeItemList.add(currRootContractCategory);
			}
			if(currContractCategory==null || !StringUtils.equals(item.getCcId(),currContractCategory.getId())){
				currContractCategory = new TreeItem();
				currContractCategory.setId(item.getCcId());
				currContractCategory.setText(item.getCcName());
				currRootContractCategory.addChild(currContractCategory);
				if(first)currContractCategory.putState("opened", true);
			}
			TreeItem qhItem = new TreeItem();
			qhItem.setId(item.getQhId());
			qhItem.setText(item.getQhName());
			qhItem.putDate("history", true);
			qhItem.putDate("contractCategoryId", currContractCategory.getId());
			qhItem.putDate("content", item.getQhContent());
			qhItem.setIcon("jstree-file");
			currContractCategory.addChild(qhItem);
			first=false;
		}
		return treeItemList;
	}

	public List<ViewlayerItem> getQueryHistoryTree4Viewlayer(Admin admin){
		List<ViewlayerItem> viewlayerItemList= new ArrayList<ViewlayerItem>();
		List<QueryHistory> itemList = queryHistoryDao.getQueryHistoryTree4Viewlayer(admin);
		for(QueryHistory queryHistory : itemList){
			ViewlayerItem item = new ViewlayerItem();
			item.setId(queryHistory.getId());
			item.setViewlayerId(queryHistory.getViewlayer().getId());
			item.setText(queryHistory.getName());
			item.setContent(queryHistory.getContent());
			viewlayerItemList.add(item);
		}
		return viewlayerItemList;
	}

	public boolean checkExist(Admin admin,QueryHistory queryHistory,String id){
		QueryHistory _queryHistory = queryHistoryDao.findAdminQuery(queryHistory.getName(),admin.getId());
		if(_queryHistory!=null && !StringUtils.equals(id, _queryHistory.getId())){
			return true;
		}else{
			return false;
		}
	};

	
	
}
