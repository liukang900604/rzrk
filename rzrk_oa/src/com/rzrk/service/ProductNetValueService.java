package com.rzrk.service;

import java.util.Date;
import java.util.List;

import com.rzrk.entity.ProductNetValue;

/**
 * 产品净值
 * @version $Id$
 * @author nerve
 * @since 2013-10-9 下午1:30:23
 */
public interface ProductNetValueService extends BaseService<ProductNetValue, String> {

    /**
     * 是否已经存在净值数据
     * @param productId
     * @return
     * @author nerve
     * @since 2013-10-14下午7:15:40
     */
    public boolean ifExist(String productId);

    /**
     * 获取成立日数据
     * @param productId
     * @return
     * @author nerve
     * @since 2013-10-14下午7:15:40
     */
    public ProductNetValue getInceptionNetValue(String productId);

    /**
     * 获取最新一条净值数据
     * @param productId
     * @return
     * @author nerve
     * @since 2014-2-11下午8:08:17
     */
    public ProductNetValue getByProductId(String productId);

    /**
     * 指定时间段获取最新一条净值数据
     * @param productId
     * @return
     * @author nerve
     * @since 2014-2-11下午8:08:17
     */
    public ProductNetValue getByProductId(String productId, Date startDate, Date endDate);

    /**
     * 获取全部产品净值数据
     * @param productId
     * @return
     * @author nerve
     * @since 2014-2-11下午8:06:38
     */
    public List<ProductNetValue> getListByProductId(String productId);

    /**
     * 获取产品净值数据
     * @param productId
     * @return
     * @author nerve
     * @since 2013-10-15上午11:14:25
     */
    public List<ProductNetValue> getNetValueByProductId(String productId);

    /**
     * 批量更新操作
     * @param pnvList
     * @return
     * @author nerve
     * @since 2014-1-23上午10:42:56
     */
    public boolean updateBatch(List<ProductNetValue> pnvList);

}