package com.rzrk.dao;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.rzrk.entity.ProductNetValue;

/**
 * Dao接口-产品净值
 * @version $Id$
 * @author nerve
 * @since 2013-10-9 下午1:26:44
 */
public interface ProductNetValueDao extends BaseDao<ProductNetValue, String> {

    public List<ProductNetValue> findByConditions(ListOrderedMap params);

    public ProductNetValue getByProductId(String productId);

    /**
     * 批量更新操作
     * @param pnvList
     * @return
     * @author nerve
     * @since 2014-1-23上午10:42:56
     */
    public boolean updateBatch(List<ProductNetValue> pnvList);

    /**
     * 执行HQL
     * @param hql
     * @return
     * @author nerve
     * @since 2014-2-11下午8:16:33
     */
    List<ProductNetValue> executeQuery(String hql);

}