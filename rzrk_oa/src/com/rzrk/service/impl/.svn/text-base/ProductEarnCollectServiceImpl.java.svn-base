package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Service;

import com.rzrk.dao.ProductEarnCollectDao;
import com.rzrk.entity.ProductEarnCollect;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.ProductEarnCollectService;

/**
 * Service实现类 - 产品收益相关
 * @version $Id$
 * @author nerve
 * @since 2014-2-10 下午2:21:15
 */
@Service("productEarnCollectServiceImpl")
public class ProductEarnCollectServiceImpl extends BaseServiceImpl<ProductEarnCollect, String> implements ProductEarnCollectService {

    @Resource(name = "productEarnCollectDaoImpl")
    private ProductEarnCollectDao productEarnCollectDao;

    @Override
    public ProductEarnCollect getProductEarnCollect(String productId) {
        ListOrderedMap params = new ListOrderedMap();
        params.put("productId", "'" + productId + "'");
        List<ProductEarnCollect> result = productEarnCollectDao.findByConditions(params);
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
    
    @Override
    public List<ProductEarnCollect> getEarnByProductId(String productId) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("productId", "'" + productId + "'");
        list.put("order by", "aror asc");
        List<ProductEarnCollect> resultList = productEarnCollectDao.findByConditions(list);
        return resultList == null ? null : resultList;
    }

    @Resource(name = "productEarnCollectDaoImpl")
    public void setBaseDao(ProductEarnCollectDao dao) {
        super.setBaseDao(dao);
    }

}