package com.rzrk.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionContext;
import com.rzrk.bean.Pager;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.dao.UnionContractCategoryDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.UnionContractCategory;
import com.rzrk.vo.DeparmentAdminVo;
import com.rzrk.vo.queryhistory.DataItem;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.KeyValueCollection;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.unioncontract.Definition;
import com.rzrk.vo.unioncontract.Field;

@Repository("unionContractCategoryDaoImpl")
public class UnionContractCategoryDaoImpl extends BaseDaoImpl<UnionContractCategory, String> implements UnionContractCategoryDao {
	
	@Resource
	private AdminDao adminDao;
	@Resource
	DeparmentDao deparmentDao;
	
	public List<Field> getPermissionField(UnionContractCategory unionContractCategory, Admin admin){
		Collection<String> myDepList = new ArrayList<String>();
		Collection<String> myDepWithSubList = new ArrayList<String>();
		for(Deparment deparment : admin.getDeparmentSet()){
			myDepList.add(deparment.getId());
			List<Deparment> depList = new ArrayList<Deparment>();
			deparmentDao.getSubDeparment(depList, deparment);
			myDepWithSubList.addAll(CollectionUtils.collect(depList, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					Deparment dep = (Deparment)arg0;
					return dep.getId();
				}
			}));
		}
		List<Field> fieldList = new ArrayList<Field>();
		Definition definition = unionContractCategory.getDefinitionObj();
		for(int i=0;i<definition.size();i++){
			Field fieldDef = definition.get(i);
			Collection<String> myList = null;
			if(fieldDef.isSuperiorView()){
				myList = myDepWithSubList;
			}else{
				myList = myDepList;
			}
			if(
					(fieldDef.getDepartmentIds().isEmpty() && fieldDef.getAdminIds().isEmpty()) //没有限制部门和人
				||	(CollectionUtils.containsAny(myList, fieldDef.getDepartmentIds())) //部门匹配
				|| (fieldDef.getAdminIds().contains(admin.getId()))	//人员匹配
					
					){
				fieldList.add(fieldDef);
			}
		}
		return fieldList;
	}


}
