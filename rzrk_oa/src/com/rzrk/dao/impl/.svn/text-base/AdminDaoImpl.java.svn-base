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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.rzrk.bean.Pager;
import com.rzrk.dao.AdminDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Job;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Dao实现类 - 人员
 */

@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	@Override
    public boolean isExistByUsername(String username) {
		String hql = "from Admin as admin where lower(admin.username) = lower(:username)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
    public Admin getAdminByUsername(String username) {
		String hql = "from Admin as admin where lower(admin.username) = lower(:username)";
		try {
			return (Admin) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
		} catch (HibernateException e) {
			return null;
		}
	}

	@Override
    public Admin getAdminByName(String name) {
		String hql = "from Admin as admin where admin.name = :name";
		try {
			return (Admin) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		} catch (HibernateException e) {
			return null;
		}
	}
	@Override
	public List<Admin> getAdminListByName(String name) {
		String hql = "from Admin as admin where admin.name like :name";
		return (List<Admin>) getSession().createQuery(hql).setParameter("name", name).list();
	}

	@Override
	public Admin getMaxSortNo() {
		String hql = "from Admin order by sortNo desc";
		Admin admin = (Admin) getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).uniqueResult();
		if (admin != null) {
			return admin;
		} else {
			return null;
		}
	}
	
	
	public List<DeparmentAdminVo> getAdminByDeparmentAndSub(String deparemtId){
		Set<String> v = new HashSet<String>();
		//获取部门以及部门的节点 
		v = getSonDepermentId(v,deparemtId);//获取set集合
	/*	String str = "";
		str = arrayToString(v);*/
		String sql = "select distinct ya.id as adminId,ya.name as adminName,yd.id as deparmentId,yd.name as deparmentName  from rzrk_admin ya,rzrk_admin_deparment ydp,rzrk_deparment yd where ya.id = admin_id and yd.id = deparment_set_id and yd.id in (:idArray) group by ya.id ";
		List<Map<String,Object>> mapList=  this.getSession().createSQLQuery(sql).setParameterList("idArray", new ArrayList<String>(v)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 List<DeparmentAdminVo>  adminList = new ArrayList<DeparmentAdminVo>();
		for(Map<String,Object> map : mapList){
			DeparmentAdminVo vo = new DeparmentAdminVo();
			vo.setAdminId((String) map.get("adminId"));
			vo.setAdminName((String) map.get("adminName"));
			vo.setDeparmentId((String) map.get("deparmentId"));
			vo.setDeparmentName((String) map.get("deparmentName"));
			adminList.add(vo);
		}
		return adminList;
	}
	
	
	public List<DeparmentAdminVo> getAdminByDeparment(String deparmentId){
		String sql = "select distinct ya.id as adminId,ya.name as adminName,yd.id as deparmentId,yd.name as deparmentName  from rzrk_admin ya,rzrk_admin_deparment ydp,rzrk_deparment yd where ya.id = admin_id and yd.id = deparment_set_id and yd.id = :deparmentId group by ya.id ";
		List<Map<String,Object>> mapList=  this.getSession().createSQLQuery(sql).setString("deparmentId", deparmentId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 List<DeparmentAdminVo>  adminList = new ArrayList<DeparmentAdminVo>();
		for(Map<String,Object> map : mapList){
			DeparmentAdminVo vo = new DeparmentAdminVo();
			vo.setAdminId((String) map.get("adminId"));
			vo.setAdminName((String) map.get("adminName"));
			vo.setDeparmentId((String) map.get("deparmentId"));
			vo.setDeparmentName((String) map.get("deparmentName"));
			adminList.add(vo);
		}
		return adminList;
	}
	
	/**
	 * 通过岗位获取人员
	 * @param jobId
	 * @return
	 */
	public List<String>  getAdminByJob(String jobId){
		String sql = "select yj.admin_id from  rzrk_admin_job yj where (yj.main_job_set_id =:mainJobId or yj.vice_job_set_id =:jobId)";
		return this.getSession().createSQLQuery(sql).setString("mainJobId", jobId).setString("jobId", jobId).list();
	}
	
	

	/**
	 * 数组转字符串
	 * @param v
	 * @return
	 */
	public String arrayToString(Vector<String> v){
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		for(String str : v){
			buffer.append("'").append(str).append("',");
		}
		
		return buffer.substring(0,buffer.length()-1)+")";
	}
	
	/**
	 * 获取子部门
	 * @param v
	 * @param deparemtId
	 * @return
	 */
	public Set<String> getSonDepermentId(Set<String> v,String deparemtId){
		 v.add(deparemtId);//添加当前部门 
		 String sql = "select id from rzrk_deparment where parent_id = ?";
		 List<Object> resultList = this.getSession().createSQLQuery(sql).setString(0, deparemtId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 for(Object obj : resultList){
			 Map<String,Object> map = (Map<String, Object>) obj;
			 String depmentId = (String) map.get("id");//获取子部门
			 getSonDepermentId(v,depmentId);
		 }
		 return v;
		
	}

    public Pager findPager(Pager pager, Collection<String> depamentIds) {
    	{
    	StringBuffer sb = new StringBuffer();
    	sb.append("select distinct a.* from rzrk_admin as a,  rzrk_admin_deparment as ad ");
    	sb.append("where a.id = ad.admin_id ");
    	if(CollectionUtils.isNotEmpty(depamentIds)){
        	sb.append("and ad.deparment_set_id in (:depamentId) ");
    	}
    	if(pager.getSearchBy() != null && pager.getSearchBy().equals("name")){
			sb.append("and a.id in (:adminId) ");
		}else if(StringUtils.isNotBlank(pager.getKeyword())){
    		sb.append("and a."+com.rzrk.util.StringUtils.camelToUnderline(pager.getSearchBy())+" like :keyword ");
    	}
    	
    	String departmentId = ((ArrayList<String>)depamentIds).get(0);//本部门id
    	
    	//部门直属成员置顶，离职人员排到末页
    	sb.append("order by case when ad.deparment_set_id = '").append(departmentId).append("' then 0 else 1 end, ");
    	sb.append("case when a.man_type = 3 then 1 else 0 end, ");
    	
    	
    	if(StringUtils.isNotBlank(pager.getOrderBy())){
        	sb.append(com.rzrk.util.StringUtils.camelToUnderline(pager.getOrderBy())+" "+pager.getOrder()+" ");
    	}else{
    		sb.append("a.create_date desc");
    	}
    	
    	String sql = sb.toString();
        SQLQuery query = getSession().createSQLQuery(sql);
    	if(CollectionUtils.isNotEmpty(depamentIds)){
    		query.setParameterList("depamentId", depamentIds);
    	}
    	if(pager.getSearchBy() != null && pager.getSearchBy().equals("name")){
    		query.setParameterList("adminId", pager.getKeyword().split(","));
    	}else if(StringUtils.isNotBlank(pager.getKeyword())){
    		query.setParameter("keyword", "%"+pager.getKeyword()+"%");
    	}
    	int pageNumber = pager.getPageNumber();
    	if(pageNumber<1){
    		pageNumber=1;
    	}
    	int startRow = (pageNumber-1)*pager.getPageSize();
    	query.setMaxResults(pager.getPageSize()).setFirstResult(startRow);
    	query.addEntity(Admin.class);
    	List data = query.list();
    	pager.setResult(data);
    	}
    	
    	{
    	StringBuffer sb = new StringBuffer();
    	sb.append("select  count(distinct a.id) as num from rzrk_admin as a,  rzrk_admin_deparment as ad ");
    	sb.append("where a.id = ad.admin_id ");
    	if(CollectionUtils.isNotEmpty(depamentIds)){
        	sb.append("and ad.deparment_set_id in (:depamentId) ");
    	}
    	if(StringUtils.isNotBlank(pager.getKeyword())){
        	sb.append("and a."+com.rzrk.util.StringUtils.camelToUnderline(pager.getSearchBy())+" like :keyword ");
    	}
    	String sql = sb.toString();
        SQLQuery query = getSession().createSQLQuery(sql);
    	if(CollectionUtils.isNotEmpty(depamentIds)){
    		query.setParameterList("depamentId", depamentIds);
    	}
    	if(StringUtils.isNotBlank(pager.getKeyword())){
    		query.setParameter("keyword", "%"+pager.getKeyword()+"%");
    	}
    	query.addScalar("num",Hibernate.LONG);
    	Long total = (Long) query.uniqueResult();
    	pager.setTotalCount((int)total.longValue());
    	}
        return pager;
    }

	/*  @see com.rzrk.dao.AdminDao#getAllListExceptOutOfJob()  */
	@Override
	public List<Admin> getAllListExceptOutOfJob() {
		String hql = "from Admin as admin where admin.manType != 3";
		return (List<Admin>) getSession().createQuery(hql).list();
	}



}