package com.rzrk.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Service;

import com.rzrk.Constants;
import com.rzrk.dao.ProductNetValueDao;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.ProductNetValueService;
import com.rzrk.util.date.DateUtils;

/**
 * Service实现类 -产品净值
 * @version $Id$
 * @author nerve
 * @since 2013-10-9 下午1:34:23
 */
@Service("productNetValueServiceImpl")
public class ProductNetValueServiceImpl extends BaseServiceImpl<ProductNetValue, String> implements ProductNetValueService {

    @Resource(name = "productNetValueDaoImpl")
    private ProductNetValueDao productNetValueDao;

    @Override
    public boolean ifExist(String productId) {
        return getInceptionNetValue(productId) == null ? false : true;
    }

    @Override
    public ProductNetValue getInceptionNetValue(String productId) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("productId", "'" + productId + "'");
        list.put("dateType", String.valueOf(Constants.INCEPTION_DATE));
        List<ProductNetValue> resultList = productNetValueDao.findByConditions(list);
        return resultList == null ? null : resultList.get(0);
    }

    @Override
    public List<ProductNetValue> getNetValueByProductId(String productId) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("productId", "'" + productId + "'");
        list.put("order by", "date asc");
        List<ProductNetValue> resultList = productNetValueDao.findByConditions(list);
        return resultList == null ? null : resultList;
    }

    @Resource(name = "productNetValueDaoImpl")
    public void setBaseDao(ProductNetValueDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public ProductNetValue getByProductId(String productId) {
        return productNetValueDao.getByProductId(productId);
    }

    @Override
    public ProductNetValue getByProductId(String productId, Date startDate, Date endDate) {
        String hql = "from ProductNetValue as t where t.productId='%s' and t.date between '%s' and '%s' order by t.date asc";
        hql = String.format(hql, productId, DateUtils.formatDateTime(startDate), DateUtils.formatDateTime(endDate));
        List<ProductNetValue> resultList = productNetValueDao.executeQuery(hql);
        return resultList.get(0);
    }

    @Override
    public List<ProductNetValue> getListByProductId(String productId) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("productId", "'" + productId + "'");
        list.put("order by", "date asc");
        List<ProductNetValue> resultList = productNetValueDao.findByConditions(list);
        return resultList;
    }

    @Override
    public boolean updateBatch(List<ProductNetValue> pnvList) {
        return productNetValueDao.updateBatch(pnvList);
    }

}