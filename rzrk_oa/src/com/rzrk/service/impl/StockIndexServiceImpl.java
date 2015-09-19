package com.rzrk.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Service;

import com.rzrk.Constants;
import com.rzrk.dao.StockIndexDao;
import com.rzrk.entity.StockIndex;
import com.rzrk.service.StockIndexService;
import com.rzrk.util.date.DateUtils;

/**
 * Service实现类 -股指
 * @version $Id$
 * @author nerve
 * @since 2013-10-12 下午7:07:18
 */
@Service("stockIndexServiceImpl")
public class StockIndexServiceImpl extends BaseServiceImpl<StockIndex, String> implements StockIndexService {

    @Resource(name = "stockIndexDaoImpl")
    private StockIndexDao stockIndexDao;

    public List<StockIndex> getStockIndexByDate(Date date) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("stockId", "'" + Constants.Stock.HS300.getId() + "'");
        list.put("date", "'" + DateUtils.formatStockDate(date) + "'");
        return stockIndexDao.findByConditions(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StockIndex> getStockIndexListByDate(Date date) {
        String hql = "from StockIndex where stockId = '%s' and date >= '%s' order by date asc";
        List<StockIndex> resultList = stockIndexDao.getSession().createQuery(String.format(hql, Constants.Stock.HS300.getId(), DateUtils.formatStockDate(date))).list();
        return resultList == null || resultList.isEmpty() ? null : resultList;
    }

    @Resource(name = "stockIndexDaoImpl")
    public void setBaseDao(StockIndexDao dao) {
        super.setBaseDao(dao);
    }

}