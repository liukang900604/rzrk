package com.rzrk.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.vo.querytree.Node;

public interface UnionContractDao extends BaseDao<ContractField, String> {
	public Map<String,Object>  findPager(String contractCategoryId,Pager pager,Node node);
	public Map<String,Object>  findPager(String contractCategoryId,Pager pager,Node node,Map<String,Object> rowCondMap);
}