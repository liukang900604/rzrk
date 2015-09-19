package com.rzrk.service;

import java.util.Date;
import java.util.List;

import com.rzrk.entity.StockIndex;

/**
 * 股指
 * @version $Id$
 * @author nerve
 * @since 2013-10-12 下午7:04:57
 */
public interface StockIndexService extends BaseService<StockIndex, String> {

    /**
     * 根据结算日获取股指信息
     * @param date
     * @return
     * @author nerve
     * @since 2013-10-14下午12:44:52
     */
    public List<StockIndex> getStockIndexByDate(Date date);

    /**
     * 根据指定最早结算日获取股指信息
     * @param date
     * @return
     * @author nerve
     * @since 2013-10-14下午12:44:52
     */
    public List<StockIndex> getStockIndexListByDate(Date date);

}