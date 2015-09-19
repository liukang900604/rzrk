package com.rzrk.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.rzrk.Constants;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.ProductNetValueService;
import com.rzrk.service.ProductService;
import com.rzrk.util.date.DateUtils;

/**
 * Purpose:
 * @version $Id$
 * @author nerve
 * @since 2014-1-23 下午1:02:22
 */
public class SynProductNetValueThread implements Runnable {

    private ProductNetValueService productNetValueService;

    private ProductService productService;

    protected final Logger logger = Logger.getLogger(this.getClass());

    private String productId;

    @Override
    public void run() {

        Product product = productService.get(productId);

        if (product == null) {
            logger.error(String.format("产品[ID=%s]不存在!", productId));
            return;
        }

        List<ProductNetValue> pnvList = productNetValueService.getListByProductId(productId);

        double lastnvadd = 1d;// 本次起始点累计净值
        // double lastnvstart = 1d;// 本次起始点初始值
        boolean hasOneStart = false;// 是否有一个累计净值起始点
        for (int i = 0; i < pnvList.size(); i++) {
            ProductNetValue pnval = pnvList.get(i);
            // 第一条记录的情况
            if (i == 0) {
                if (pnval.getDateType() != Constants.INCEPTION_DATE) {
                    logger.error(String.format("产品[id=%s]最早一条记录[date=%s]日期类型不为成立日[dateType=%s]!", productId, DateUtils.formatDate(pnval.getDate())));
                }
            }

            // 无起始点
            if (!hasOneStart) {
                // 说明是一个累计净值起始点
                if (pnval.getTrustValueStart() != null) {
                    hasOneStart = true;
                    lastnvadd = pnval.getTrustValueStart();
                    // pnval.setTrustValueAdd(pnval.getTrustValue() + lastnvadd - 1);
                    pnval.setTrustValueAdd(lastnvadd);
                } else {
                    pnval.setTrustValueAdd(pnval.getTrustValue());
                }
            }

            // 含有起始点
            else {
                // 说明是一个累计净值起始点
                if (pnval.getTrustValueStart() != null) {
                    lastnvadd = pnval.getTrustValueStart();
                    pnval.setTrustValueAdd(lastnvadd);
                } else {
                    pnval.setTrustValueAdd(pnval.getTrustValue() + lastnvadd - 1);
                }
            }

            pnval.setGrowthRateAdd(pnval.getTrustValueAdd() - 1);
        }

        // 执行批量更新操作
        boolean flag = productNetValueService.updateBatch(pnvList);
        if (flag) {
            logger.info(String.format("同步产品[id=%s]净值数据成功!", productId));
        } else {
            logger.info(String.format("同步产品[id=%s]净值数据失败!", productId));
        }
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductNetValueService getProductNetValueService() {
        return productNetValueService;
    }

    public void setProductNetValueService(ProductNetValueService productNetValueService) {
        this.productNetValueService = productNetValueService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
