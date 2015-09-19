package com.rzrk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.ContractLogDao;
import com.rzrk.entity.ContractLog;

@Repository("contractLogDaoImpl")
public class ContractLogDaoImpl  extends BaseDaoImpl<ContractLog, String> implements ContractLogDao{

	@Override
	public List<ContractLog> queryContractLog(String contractCategoryId, String rowId) {
		String hql =" from ContractLog as cl where cl.rowId=:rowId and cl.contractCategory.id = :contractCategoryId order by cl.createDate desc ";
		Query query = getSession().createQuery(hql).setString("rowId", rowId).setString("contractCategoryId", contractCategoryId);
		List<ContractLog> list = query.list();
		return list;
	}
	
}
