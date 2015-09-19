package com.rzrk.service;

import java.util.List;
import java.util.Map;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.viewlayer.Definition;
import com.rzrk.vo.viewlayer.Field;

public interface ViewlayerService extends BaseService<Viewlayer, String> {
	public List<Viewlayer> getList(Admin admin);
	public List<Map> getShowList(Admin admin);
	public Pager findShowPager(Pager pager, String contractCategoryId,Node node );
	public boolean checkExist(Viewlayer viewlayer,String id);
	public List<Field> getPermissionField(Definition definition, Admin admin);
}
