package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.StockIndexDao;
import com.rzrk.entity.StockIndex;

/**
 * Dao实现类 -股指
 * @author nerve
 * @since 2013-10-12 下午7:05:21
 */
@Repository("stockIndexDaoImpl")
public class StockIndexDaoImpl extends BaseDaoImpl<StockIndex, String> implements StockIndexDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<StockIndex> findByConditions(ListOrderedMap params) {
        StringBuffer hql = new StringBuffer("from StockIndex where 1 = 1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            hql.append(String.format(" and %s = %s", key, value));
        }
        List<StockIndex> list = getSession().createQuery(hql.toString()).list();
        return (list == null || list.isEmpty()) ? null : list;
    }

}