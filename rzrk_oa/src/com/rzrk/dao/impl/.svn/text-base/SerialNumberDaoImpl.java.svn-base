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
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.rzrk.bean.Pager;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.SerialNumberDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.SerialNumber;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Dao实现类 - 人员
 */

@Repository("serialNumberDaoImpl")
public class SerialNumberDaoImpl extends BaseDaoImpl<SerialNumber, String> implements SerialNumberDao {

	@Override
	public long getAndSet(String type) {
			
		String insertsql = "insert rzrk_serial_number(type,`value`) SELECT :type, 0 FROM DUAL where NOT EXISTS(select 1 from rzrk_serial_number as n where n.type = :type) ";
		String updatesql = "update rzrk_serial_number set `value` = `value`+1 where type = :type ";
		String selectsql = "select `value` as num from rzrk_serial_number where type = :type ";
		Session session = getSession();
		session.createSQLQuery(insertsql).setString("type", type).executeUpdate();
		session.createSQLQuery(updatesql).setString("type", type).executeUpdate();
		long num = (Long) session.createSQLQuery(selectsql).addScalar("num", Hibernate.LONG).setString("type", type).uniqueResult();
		return num;
	}
}