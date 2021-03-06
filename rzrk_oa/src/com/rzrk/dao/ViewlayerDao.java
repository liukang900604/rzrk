package com.rzrk.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.vo.queryhistory.DataItem;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.viewlayer.Definition;
import com.rzrk.vo.viewlayer.Field;

public interface ViewlayerDao extends BaseDao<Viewlayer, String> {

	public List<Viewlayer> getList(Admin admin);

	public Map<String, Object> findShowPager(Definition definition, Pager pager, Node node);

	public Viewlayer find(String key, Object value);

	public List<Field> getPermissionField(Definition definition, Admin admin);
}
