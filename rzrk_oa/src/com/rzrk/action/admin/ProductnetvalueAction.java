package com.rzrk.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.Constants;
import com.rzrk.bean.Pager;
import com.rzrk.bean.Pager.Order;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductEarnCollect;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.ProductEarnCollectService;
import com.rzrk.service.ProductNetValueService;
import com.rzrk.service.ProductService;
import com.rzrk.util.SynProductNetValueThread;
import com.rzrk.util.date.DateUtils;

@ParentPackage("admin")
public class ProductnetvalueAction extends BaseAdminAction {

    private static final long serialVersionUID = 1L;

    @Resource(name = "productNetValueServiceImpl")
    private ProductNetValueService productNetValueService;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Resource(name = "productEarnCollectServiceImpl")
    private ProductEarnCollectService productEarnCollectService;

    private ProductNetValue pnv;

    // 列表
    public String list() {
        String id = getRequest().getParameter("id");
        if (id != null && !"".equals(id)) {
            pager = (pager == null) ? new Pager() : pager;
            pager.setPageSize(15);
            pager.setOrderBy("date");
            pager.setOrder(Order.desc);
            pager = productNetValueService.findPager(pager, Restrictions.eq("productId", id));
        }
        getRequest().setAttribute("productList", productService.getAllEnabledProduct());
        getRequest().setAttribute("productEarnCollect", productEarnCollectService.getProductEarnCollect(id));
        return LIST;
    }

    // 删除
    public String delete() {
        StringBuffer logInfoStringBuffer = new StringBuffer("删除产品净值: ");
        productNetValueService.delete(ids);
        logInfo = logInfoStringBuffer.toString();
        return ajax(Status.success, "删除成功!");
    }

    // 保存产品收益信息
    public String saveProductEarnCollect() {
        try {
            String productId = getRequest().getParameter("productId");

            String earnWeekStr = getRequest().getParameter("earnWeek");
            Double earnWeek = (earnWeekStr == null || "".equals(earnWeekStr)) ? null : Double.parseDouble(earnWeekStr);

            String earnMonthStr = getRequest().getParameter("earnMonth");
            Double earnMonth = (earnMonthStr == null || "".equals(earnMonthStr)) ? null : Double.parseDouble(earnMonthStr);

            String earnTotalStr = getRequest().getParameter("earnTotal");
            Double earnTotal = (earnTotalStr == null || "".equals(earnTotalStr)) ? null : Double.parseDouble(earnTotalStr);

            String arorStr = getRequest().getParameter("aror");
            Double aror = (arorStr == null || "".equals(arorStr)) ? null : Double.parseDouble(arorStr);

            boolean isExist = true;
            ProductEarnCollect obj = productEarnCollectService.getProductEarnCollect(productId);
            if (obj == null) {
                isExist = false;
                obj = new ProductEarnCollect();
                obj.setProductId(productId);
            }
            obj.setEarnWeek(earnWeek);
            obj.setEarnMonth(earnMonth);
            obj.setEarnTotal(earnTotal);
            obj.setAror(aror);

            if (isExist) {
                productEarnCollectService.update(obj);
            } else {
                productEarnCollectService.save(obj);
            }
            return ajax(Status.success, "保存成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ajax(Status.error, "保存失败!");
        }
    }

    // 编辑
    public String edit() {
        getRequest().setAttribute("command", "编辑");
        String productId = getRequest().getParameter("productId");// 产品ID

        if (isNull(id) || isNull(productId)) {
            return ERROR;
        }

        Product product = productService.get(productId);
        pnv = productNetValueService.get(id);

        if (product == null || pnv == null) {
            return ERROR;
        }

        getRequest().setAttribute("ifExist", productNetValueService.ifExist(productId));
        getRequest().setAttribute("product", product);
        return INPUT;
    }

    // 添加
    public String add() {
        String productId = getRequest().getParameter("productId");
        getRequest().setAttribute("command", "添加");
        getRequest().setAttribute("product", productService.get(productId));
        getRequest().setAttribute("ifExist", productNetValueService.ifExist(productId));
        return INPUT;
    }

    // 保存添加
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        ProductNetValue inceptionNetValue = productNetValueService.getInceptionNetValue(pnv.getProductId());
        if (inceptionNetValue == null) {
            pnv.setDateType(Constants.INCEPTION_DATE);
        } else {
            pnv.setDateType(Constants.VALUATION_DATE);
        }
        productNetValueService.save(pnv);
        redirectUrl = "productnetvalue!list.action?id=" + pnv.getProductId();
        logger.info(String.format("新建产品[id=%s]净值[date=%s]成功!", pnv.getProductId(), DateUtils.formatDateTime(pnv.getDate())));
        // 异步更新净值
        synProductNv(pnv.getProductId());
        return SUCCESS;
    }

    // 保存修改
    @InputConfig(resultName = "error")
    public String update() {
        String productId = pnv.getProductId();
        try {
            ProductNetValue persistent = productNetValueService.get(id);
            BeanUtils.copyProperties(pnv, persistent, ProductNetValue.getUnUpdateField());

            ProductNetValue inceptionNetValue = productNetValueService.getInceptionNetValue(persistent.getProductId());

            if (inceptionNetValue.getId().equals(persistent.getId())) {
                persistent.setDateType(Constants.INCEPTION_DATE);
            } else {
                persistent.setDateType(Constants.VALUATION_DATE);
            }

            productNetValueService.update(persistent);
            redirectUrl = "productnetvalue!list.action?id=" + productId;
            logger.info(String.format("更新产品[id=%s]净值[id=%s]成功!", productId, id));
            // 异步更新净值
            synProductNv(productId);
            return SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error(String.format("更新产品[id=%s]净值[id=%s]异常!", productId, id));
            return ERROR;
        }
    }

    /**
     * 异步执行同步净值操作
     * @param productId
     * @author nerve
     * @since 2014-1-23下午12:58:32
     */
    private void synProductNv(String productId) {
        SynProductNetValueThread runner = new SynProductNetValueThread();
        runner.setProductId(productId);
        runner.setProductNetValueService(productNetValueService);
        runner.setProductService(productService);

        Thread t = new Thread(runner);
        t.start();
    }

    public ProductNetValue getPnv() {
        return pnv;
    }

    public void setPnv(ProductNetValue pnv) {
        this.pnv = pnv;
    }

}