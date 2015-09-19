package com.rzrk.action.rzrk;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import weibo4j.org.json.JSONArray;

import com.rzrk.Constants.Stock;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.entity.StockIndex;
import com.rzrk.service.ProductNetValueService;
import com.rzrk.service.ProductService;
import com.rzrk.service.StockIndexService;
import com.rzrk.util.date.DateUtils;

/**
 * 图表
 * @version $Id$
 * @author nerve
 * @since 2013-10-15 下午4:02:18
 */
@ParentPackage("shop")
public class ChartAction extends BaseShopAction {

    private static final long serialVersionUID = -1374763490833950843L;

    @Resource(name = "stockIndexServiceImpl")
    private StockIndexService stockIndexService;

    @Resource(name = "productNetValueServiceImpl")
    private ProductNetValueService productNetValueService;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    /**
     * 产品详情页走势图
     * @return
     * @author nerve
     * @since 2013-10-15下午3:46:22
     */
    public String trendChart() {
        StringBuffer log = new StringBuffer();
        log.append("目的 : 获取产品走势图 ; ");
        try {
            String chartType = getRequest().getParameter("chartType");
            log.append(String.format("图表类型 : %s ; ", chartType));
            String productId = getRequest().getParameter("productId");
            log.append(String.format("产品ID : %s ; ", productId));

            // 沪深300走势图
            if (Stock.HS300.getCode().equals(chartType)) {

                // 根据产品ID查询产品的初始时间
                ProductNetValue netValue = productNetValueService.getInceptionNetValue(productId);
                if (netValue == null) {
                    log.append(" --> 此产品无净值数据!直接返回成功!");
                    logger.info(log.toString());
                    return NONE;
                }
                Date startDate = netValue.getDate();
                log.append(String.format("图表起始时间 : %s ; ", DateUtils.formatDateTime(startDate)));

                // 查询此时间之后的股指
                List<StockIndex> resultList = stockIndexService.getStockIndexListByDate(startDate);
                if (resultList == null) {
                    log.append(" --> 此阶段无股指数据!");
                    logger.info(log.toString());
                    return NONE;
                }

                log.append(String.format("实际起始时间 : %s ; ", resultList.get(0).getDate()));
                // 将时间统一,你妹的.
                resultList.get(0).setDate(DateUtils.formatStockDate(startDate));
                logger.info(DateUtils.formatStockDate(startDate));

                // 生成股指数据
                JSONArray arr = new JSONArray();
                StockIndex first = resultList.get(0);
                for (int i = 0; i < resultList.size(); i++) {
                    StockIndex item = resultList.get(i);
                    JSONArray itemArr = new JSONArray();
                    // 特殊处理by nerve 加上8小时
                    itemArr.put(DateUtils.parseDate(item.getDate(), "yyyyMMdd").getTime() + 28800000);
                    if (i == 0) {
                        // 特殊处理by nerve 设置初始值为0.001
                        itemArr.put(0.001);
                    } else {
                        itemArr.put(Math.rint((item.getEnd() / first.getEnd() - 1) * 10000) / 100);
                    }
                    arr.put(itemArr);
                }

                getResponse().getWriter().write(arr.toString());
                logger.info("沪深 : " + arr.toString());
                log.append(" --> 获取沪深300走势图数据成功!");
                logger.info(log.toString());
            }

            // 产品走势图
            else {
                // 获取净值数据
                List<ProductNetValue> resultList = productNetValueService.getNetValueByProductId(productId);

                if (resultList == null || resultList.isEmpty()) {
                    log.append(" --> 获取产品走势图数据为空!");
                    logger.info(log.toString());
                    return NONE;
                }

                log.append(String.format("图表起始时间 : %s ; ", DateUtils.formatDateTime(resultList.get(0).getDate())));

                JSONArray arr = new JSONArray();
                for (int i = 0; i < resultList.size(); i++) {
                    ProductNetValue item = resultList.get(i);
                    JSONArray itemArr = new JSONArray();
                    // 特殊处理by nerve 加上8小时
                    itemArr.put(item.getDate().getTime() + 28800000);
                    if (i == 0) {
                        // 特殊处理by nerve 设置初始值为0.001
                        itemArr.put(0.001);
                    } else {
                        itemArr.put(Math.rint(item.getGrowthRateAdd() * 10000) / 100);
                    }
                    arr.put(itemArr);
                }

                getResponse().getWriter().write(arr.toString());
                logger.info("产品 : " + arr.toString());
                log.append(" --> 获取产品走势图数据成功!");
                logger.info(log.toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            log.append(" --> 生成数据异常!");
            logger.error(log.toString());
        }

        return NONE;
    }
}
