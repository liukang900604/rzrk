package com.rzrk.service;

import java.util.List;
import java.util.Map;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.UnionContractCategory;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.unioncontract.Definition;
import com.rzrk.vo.unioncontract.Field;

public interface UnionContractCategoryService extends BaseService<UnionContractCategory, String> {
	public List<Field> getPermissionField(UnionContractCategory definition, Admin admin);
}
