package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.ProductEarnCollectDao;
import com.rzrk.entity.ProductEarnCollect;

/**
 * Dao实现类 - 产品收益相关
 */

@Repository("productEarnCollectDaoImpl")
public class ProductEarnCollectDaoImpl extends BaseDaoImpl<ProductEarnCollect, String> implements ProductEarnCollectDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductEarnCollect> findByConditions(ListOrderedMap params) {
        StringBuffer hql = new StringBuffer("from ProductEarnCollect where 1 = 1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            if ("order by".equalsIgnoreCase(key)) {
                hql.append(String.format(" order by %s", value));
            } else {
                hql.append(String.format(" and %s = %s", key, value));
            }
        }
        List<ProductEarnCollect> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
    }

}