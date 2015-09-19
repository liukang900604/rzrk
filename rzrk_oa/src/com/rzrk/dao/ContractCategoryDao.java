package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.vo.contract.Field;

public interface ContractCategoryDao extends BaseDao<ContractCategory, String> {

	public ContractCategory find(String key,Object value);
	public String getMajorField(String id);
	public boolean isExistByCategoryname(String name);
//	public boolean deleteWithCheck(String contractCategoryId);
	public List<Field> getPermissionField(ContractCategory  contractCategory, Admin admin);
	public ContractCategory getContractCategoryByCategoryname(String name);
}