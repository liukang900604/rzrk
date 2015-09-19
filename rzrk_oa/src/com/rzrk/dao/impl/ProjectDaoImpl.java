/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
import com.rzrk.bean.Pager;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.dao.ProjectDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Project;
import com.rzrk.vo.DeparmentAdminVo;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;

/**
 * Dao实现类 - 人员
 */

@Repository("projectDaoImpl")
public class ProjectDaoImpl extends BaseDaoImpl<Project, String> implements ProjectDao {
	@Resource
	private AdminDao adminDao;
	@Resource
	private DeparmentDao deparmentDao;
	@Override
	public void deleteByProjectId(String[] ids) {
		for (String id : ids) {
			String deleteRemark = "delete from rzrk_user_plan_remark  where project ='"+id+"'";
			String deleteLog = "delete from rzrk_user_plan_log  where project_id ='"+id+"'";
			String deleteAdmin = "delete from rzrk_project_admin  where project_id ='"+id+"'";
			String deleteUserplan = "delete from rzrk_project where id ='"+id+"'";
			getSession().createSQLQuery(deleteRemark).executeUpdate();
			getSession().createSQLQuery(deleteLog).executeUpdate();
			getSession().createSQLQuery(deleteAdmin).executeUpdate();
			getSession().createSQLQuery(deleteUserplan).executeUpdate();
		}
		
	}
	
	public Collection<String> getPermissionField(Admin admin){
		Collection<String> myDepWithSubList = new ArrayList<String>();
		for(Deparment deparment : admin.getDeparmentSet()){
			if(deparment.getDeparmentLeader()!=null && StringUtils.equals(deparment.getDeparmentLeader().getId(), admin.getId())){
				List<Deparment> depList = new ArrayList<Deparment>();
				deparmentDao.getSubDeparment(depList, deparment);
				myDepWithSubList.addAll(CollectionUtils.collect(depList, new Transformer() {
					@Override
					public Object transform(Object arg0) {
						Deparment dep = (Deparment)arg0;
						return dep.getId();
					}
				}));
			}else{
				myDepWithSubList.add(deparment.getId());
			}
		}
		return myDepWithSubList;
	}

	public void findPager(Pager pager,Map<String,Object> queryMap){
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		Collection<String> deparmentIdList = getPermissionField(loginAdmin);
		String nameQuery = (String) queryMap.get("nameQuery");
		String creatorQuery = (String) queryMap.get("creatorQuery");
		String responsiborQuery = (String) queryMap.get("responsiborQuery");
		String statusQuery = (String) queryMap.get("statusQuery");
		String hasNoRoot = (String) queryMap.get("hasNoRoot");
		
		Map<String,Object> params = new HashMap<String, Object>();
		{
			String sqlCount = " SELECT count(1) as num from rzrk_project as p ";
			if(StringUtils.isNotBlank(creatorQuery)){
				sqlCount += " inner join rzrk_admin as cr on ( p.creator_id = cr.id and cr.name like :creatorQuery ) ";
			}
			if(StringUtils.isNotBlank(responsiborQuery)){
				sqlCount += " inner join rzrk_admin as re on ( p.responsibor_id = re.id and re.name like :responsiborQuery ) ";
			}
			
			sqlCount += " where "
					+" ( "
					+" EXISTS ( "
					+"  SELECT 1 "
					+"		FROM "
					+"			rzrk_user_plan_require AS pr,rzrk_contract_field AS f  "
					+"		WHERE "
					+"			pr.row_id = f.id "
					+"		AND f.create_admin_id = '"+loginAdmin.getId()+"' "
					+"		AND EXISTS ( "
					+"			SELECT 1 "
					+"			FROM "
					+"				rzrk_user_plan AS pr_up "
					+"			WHERE "
					+"				pr_up.id = pr.user_plan_id "
					+"			and	pr_up.project_id = p.id "
					+"		) "
					+"	) "
					+" or "
					+" p.permissioned = 0 "
					+" or "
					+ " p.creator_id = '"+loginAdmin.getId()+"' "
					+" or "
					+ " p.responsibor_id = '"+loginAdmin.getId()+"' "
					+" or "
					+" exists (select 1 from rzrk_user_plan as up where up.project_id = p.id and ( up.admin_id = '"+loginAdmin.getId()+"'  or up.creator_id = '"+loginAdmin.getId()+"' )     )  ";
			if(CollectionUtils.isNotEmpty(deparmentIdList)){
				sqlCount+=" or "
				+" p.deparment_id in ("+com.rzrk.util.StringUtils.join(deparmentIdList, ",", "'")+") ";
			};
			sqlCount+=" ) ";
			
		    if (StringUtils.isNotEmpty(hasNoRoot) && hasNoRoot.equals("0")){
				sqlCount+=" and p.root_contract_category_id is not null ";
		    }else if(StringUtils.isNotEmpty(hasNoRoot) && hasNoRoot.equals("1")){
				sqlCount+=" and p.root_contract_category_id is null ";
		    }
			

			if(StringUtils.isNotBlank(statusQuery)){
				sqlCount += " and p.status = :statusQuery ";
			}
			
			if(StringUtils.isNotBlank(nameQuery)){
				sqlCount += " and p.name like :nameQuery ";
			}

			SQLQuery sqlCountQuery = getSession().createSQLQuery(sqlCount);
			
			if(StringUtils.isNotBlank(nameQuery)){
				sqlCountQuery.setString("nameQuery", "%"+nameQuery+"%");
			}
			if(StringUtils.isNotBlank(creatorQuery)){
				sqlCountQuery.setString("creatorQuery", "%"+creatorQuery+"%");
			}
			if(StringUtils.isNotBlank(responsiborQuery)){
				sqlCountQuery.setString("responsiborQuery", "%"+responsiborQuery+"%");
			}
			if(StringUtils.isNotBlank(statusQuery)){
				sqlCountQuery.setInteger("statusQuery",Project.Status.valueOf(statusQuery).ordinal());
			}
			int totalCount = (Integer) sqlCountQuery.addScalar("num", Hibernate.INTEGER).uniqueResult();
			pager.setTotalCount(totalCount);
		}
//		sql += " inner join rzrk_admin as re on ( p.responsibor_id = re.id and re.name like :responsiborQuery ) ";

		{
			String sql = " SELECT * from rzrk_project as p ";
			if(StringUtils.isNotBlank(creatorQuery) || StringUtils.equalsIgnoreCase("Creator",pager.getOrderBy())){
				sql += " ,rzrk_admin as cr ";
			}
			if(StringUtils.isNotBlank(responsiborQuery) || StringUtils.equalsIgnoreCase("responsibor",pager.getOrderBy())){
				sql += " ,rzrk_admin as re ";
			}
			if(StringUtils.equalsIgnoreCase("deparment",pager.getOrderBy())){
				sql += " ,rzrk_deparment as dep ";
			}
			
			sql += " where "
					+" ( "
					+" EXISTS ( "
					+"  SELECT 1 "
					+"		FROM "
					+"			rzrk_user_plan_require AS pr,rzrk_contract_field AS f  "
					+"		WHERE "
					+"			pr.row_id = f.id "
					+"		AND f.create_admin_id = '"+loginAdmin.getId()+"' "
					+"		AND EXISTS ( "
					+"			SELECT 1 "
					+"			FROM "
					+"				rzrk_user_plan AS pr_up "
					+"			WHERE "
					+"				pr_up.id = pr.user_plan_id "
					+"			and	pr_up.project_id = p.id "
					+"		) "
					+"	) "
					+" or "
					+" p.permissioned = 0 "
					+" or "
					+ " p.creator_id = '"+loginAdmin.getId()+"' "
					+" or "
					+ " p.responsibor_id = '"+loginAdmin.getId()+"' "
					+" or "
					+ "exists (select 1 from rzrk_user_plan as up where up.project_id = p.id and ( up.admin_id = '"+loginAdmin.getId()+"'  or up.creator_id = '"+loginAdmin.getId()+"' )     )  ";
			if(CollectionUtils.isNotEmpty(deparmentIdList)){
				sql+=" or "
				+" p.deparment_id in ("+com.rzrk.util.StringUtils.join(deparmentIdList, ",", "'")+") ";
			};
			sql+=" ) ";
			
		    if (StringUtils.isNotEmpty(hasNoRoot) && hasNoRoot.equals("0")){
				sql +=" and p.root_contract_category_id is not null ";
		    }else if(StringUtils.isNotEmpty(hasNoRoot) && hasNoRoot.equals("1")){
				sql +=" and p.root_contract_category_id is null ";
		    }
			
			
			if(StringUtils.isNotBlank(creatorQuery) || StringUtils.equalsIgnoreCase("Creator",pager.getOrderBy())){
				sql += " and p.creator_id = cr.id ";
			}
			if(StringUtils.isNotBlank(responsiborQuery) || StringUtils.equalsIgnoreCase("responsibor",pager.getOrderBy())){
				sql += " and p.responsibor_id = re.id ";
			}
			if(StringUtils.equalsIgnoreCase("deparment",pager.getOrderBy())){
				sql += " and p.deparment_id = dep.id ";
			}
			
			if(StringUtils.isNotBlank(statusQuery)){
				sql += " and p.status = :statusQuery ";
			}
			if(StringUtils.isNotBlank(nameQuery)){
				sql += " and p.name like :nameQuery ";
			}
			if(StringUtils.isNotBlank(creatorQuery) ){
				sql += " and cr.name like :creatorQuery  ";
			}
			if(StringUtils.isNotBlank(responsiborQuery) ){
				sql += " and re.name like :responsiborQuery ";
			}

			if(StringUtils.equalsIgnoreCase("Creator",pager.getOrderBy())){
				sql += " order by cr.name "+" "+ pager.getOrder().toString()+" ";
			}else if(StringUtils.equalsIgnoreCase("responsibor",pager.getOrderBy())){
				sql += " order by re.name "+" "+ pager.getOrder().toString()+" ";
			}else if(StringUtils.equalsIgnoreCase("deparment",pager.getOrderBy())){
				sql += " order by dep.name "+" "+ pager.getOrder().toString()+" ";
			}else if(StringUtils.isNotBlank(pager.getOrderBy())){
				sql += "order by p."+com.rzrk.util.StringUtils.camelToUnderline(pager.getOrderBy())+" "+ pager.getOrder().toString()+" ";
			}

			int start = (pager.getPageNumber()-1)*pager.getPageSize();
			if(start<0){
				start = 0;
			}
			sql+="	LIMIT "+start+","+pager.getPageSize()+" ";
			
			SQLQuery sqlquery = getSession().createSQLQuery(sql);
			
			if(StringUtils.isNotBlank(nameQuery)){
				sqlquery.setString("nameQuery", "%"+nameQuery+"%");
			}
			if(StringUtils.isNotBlank(creatorQuery)){
				sqlquery.setString("creatorQuery", "%"+creatorQuery+"%");
			}
			if(StringUtils.isNotBlank(responsiborQuery)){
				sqlquery.setString("responsiborQuery", "%"+responsiborQuery+"%");
			}
			if(StringUtils.isNotBlank(statusQuery)){
				sqlquery.setInteger("statusQuery",Project.Status.valueOf(statusQuery).ordinal());
			}
			List<Project> resList = sqlquery.addEntity(Project.class).list();
			pager.setResult(resList);
		}
		
		
		
	}

	/*  @see com.rzrk.dao.ProjectDao#checkIfNameExists(com.rzrk.entity.Admin, java.lang.String)  */
	@Override
	public Boolean checkIfNameExists(Project project, String name) {
		String hql = "from Project as project where lower(project.name) = lower(:name) and project.id != :id";
		Project proj = (Project) getSession().createQuery(hql).setParameter("name", name).setParameter("id", project.getId()).uniqueResult();
		if (proj != null) {
			return true;
		} else {
			return false;
		}
	}



}