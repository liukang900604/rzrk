package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.ProductDao;
import com.rzrk.entity.Product;

/**
 * Dao实现类 - 产品
 */

@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, String> implements ProductDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findByConditions(ListOrderedMap params) {
        StringBuffer hql = new StringBuffer("from Product where 1 = 1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
    }
    
   
    @Override
    public List<Product> findProductName() {
        Query query = null;
        String hql = "from Product";
        query = getSession().createQuery(hql);
        List<Product> list = query.list();
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

	@Override
	public List<Product> findByProductCategories(ListOrderedMap params) {
		StringBuffer hql = new StringBuffer("from Product where product_categories=1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}
	
	@Override
	public List<Product> findByProductCategoriess(ListOrderedMap params) {
		StringBuffer hql = new StringBuffer("from Product where product_categories=1 and aggressive=1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}
	@Override
	public List<Product> findByProductAggressive(ListOrderedMap params) {
		StringBuffer hql = new StringBuffer("from Product where product_categories=0 and aggressive=1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}


	@Override
	public List<Product> findByProductAggressives(ListOrderedMap params) {
		StringBuffer hql = new StringBuffer("from Product where product_categories=0 and aggressive=0");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}


	@Override
	public List<Product> findByProductFlexible(ListOrderedMap params) {
		StringBuffer hql = new StringBuffer("from Product where product_categories=0 and aggressive=2");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}


	@Override
	public Product findByProductHistoryName(String value) {
		StringBuffer hql = new StringBuffer("from Product where history_name = '"+value+"'");
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);
	}


	@Override
	public Product getProductByXuntouName(String trim) {
		StringBuffer hql = new StringBuffer("from Product where xuntouName = '"+trim+"'");
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);
	}


	@Override
	public int getProductByIsValid(int i) {
		StringBuffer hql = new StringBuffer("from Product where isValid = "+i);
        List<Product> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? 0 : resultList.size();
	}
	
	public Product getProductByAnyName(String value){
		String hql = "from Product as p where p.name=:name or p.nameShort=:nameShort or p.xuntouName=:xuntouName or p.historyName=:historyName ";
		Query query = getSession().createQuery(hql).setString("name", value).setString("nameShort", value).setString("xuntouName", value).setString("historyName", value).setMaxResults(1);
		return (Product) query.uniqueResult();
	}
	
	public Product getProductByFullName(String value){
		String hql = "from Product as p where p.name=:name ";
		Query query = getSession().createQuery(hql).setString("name", value).setMaxResults(1);
		return (Product) query.uniqueResult();
	}
	
	public List<Product> getProductListByAnyName(String value){
		String hql = "from Product as p where p.name=:name or p.nameShort=:nameShort or p.xuntouName=:xuntouName or p.historyName=:historyName ";
		Query query = getSession().createQuery(hql).setString("name", value).setString("nameShort", value).setString("xuntouName", value).setString("historyName", value).setMaxResults(1);
		return query.list();
	}
}