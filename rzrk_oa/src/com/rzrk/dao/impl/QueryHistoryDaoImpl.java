package com.rzrk.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.QueryHistoryDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.QueryHistory;
import com.rzrk.vo.queryhistory.DataItem;

@Repository("queryHistoryDaoImpl")
public class QueryHistoryDaoImpl extends BaseDaoImpl<QueryHistory, String> implements QueryHistoryDao {
	
	
	public List<DataItem> getQueryHistoryList(Admin admin){
		String sql = 
				"	SELECT"+
						"		rc.id as rcId,rc.`name` as rcName,"+
						"		cc.id as ccId,cc.`name` as ccName,"+
						"		qh.id as qhId,qh.`name` as qhName, qh.content as qhContent"+
						"	FROM"+
						"		rzrk_contract_category AS cc,"+
						"		rzrk_root_contract_category AS rc,"+
						"		rzrk_query_history AS qh"+
						"	WHERE"+
						"		qh.contract_category_id = cc.id"+
						"	AND"+
						"		rc.id = cc.root_contract_category_id"+
						"	AND qh.admin_id = :admin_id"+
						"	ORDER BY rc.create_date,cc.create_date,qh.create_date desc";
		List<DataItem> list = getSession().createSQLQuery(sql)
		.addScalar("rcId",Hibernate.STRING).addScalar("rcName",Hibernate.STRING)
		.addScalar("ccId",Hibernate.STRING).addScalar("ccName",Hibernate.STRING)
		.addScalar("qhId",Hibernate.STRING).addScalar("qhName",Hibernate.STRING).addScalar("qhContent",Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(DataItem.class))
		.setString("admin_id", admin.getId()).list();
		return list;
	}
	
	public List<QueryHistory> getQueryHistoryTree4Viewlayer(Admin admin){
			Query query = getSession().createQuery("from QueryHistory as qh where qh.admin.id = :adminId and qh.type = :type order by qh.createDate desc ");
			query.setString("adminId", admin.getId()).setInteger("type", QueryHistory.TYPE_VIEWLAYER);
			return query.list();
	}
	
/*	public List<RootContractCategory> getHasQueryHistoryContractCategoryList(Admin admin){
		String sql = 
				"	SELECT"+
				"		{rc.*},{cc.*}"+
				"	FROM"+
				"		rzrk_contract_category AS cc,"+
				"		rzrk_root_contract_category AS rc,"+
				"		rzrk_query_history AS q"+
				"	WHERE"+
				"		q.contract_category_id = cc.id"+
				"	AND"+
				"		rc.id = cc.root_contract_category_id"+
				"	AND q.admin_id = :admin_id";
		List<RootContractCategory> = getSession().createSQLQuery(sql)
		.addEntity("rc", RootContractCategory.class).addJoin("cc", "rc.contractCategorySet")
		.setString("admin_id", admin.getId()).list();
	}
*/
	
	 public QueryHistory findAdminQuery(String queryName,String adminId){
		 String hql = "from QueryHistory as qh where qh.name=:queryName and qh.admin.id=:adminId";
	        try {
	        	Query query = getSession().createQuery(hql).setString("queryName", queryName).setString("adminId", adminId);
				return (QueryHistory)query.uniqueResult();
			} catch (HibernateException e) {
				return null;
			}
	 };

}
