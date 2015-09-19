package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.ProductNetValueDao;
import com.rzrk.entity.ProductNetValue;

/**
 * Dao实现类 - 产品净值
 */

@Repository("productNetValueDaoImpl")
public class ProductNetValueDaoImpl extends BaseDaoImpl<ProductNetValue, String> implements ProductNetValueDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductNetValue> findByConditions(ListOrderedMap params) {
        StringBuffer hql = new StringBuffer("from ProductNetValue where 1 = 1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else if ("<".equalsIgnoreCase(key) || ">".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<ProductNetValue> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductNetValue getByProductId(String productId) {
        Query query = null;
        String hql = "from ProductNetValue as pnv where pnv.productId='" + productId + "' order by pnv.date desc";
        query = getSession().createQuery(hql);
        List<ProductNetValue> list = query.list();
        if (list != null && list.size() > 0) {
            return ((ProductNetValue) list.get(0));
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductNetValue> executeQuery(String hql) {
        Query query = null;
        query = getSession().createQuery(hql);
        List<ProductNetValue> list = query.list();
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateBatch(List<ProductNetValue> pnvList) {

        try {
            for (int i = 0; i < pnvList.size(); i++) {
                ProductNetValue pnv = pnvList.get(i);
                update(pnv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}