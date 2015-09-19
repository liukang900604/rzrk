package com.rzrk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 产品收益汇总信息 */
@Entity
@Table(name = "rzrk_product_earn_collect")
public class ProductEarnCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 产品ID */
    private String productId;

    /** 最近一周收益 */
    private Double earnWeek;

    /** 最近一月收益 */
    private Double earnMonth;

    /** 至今收益 */
    private Double earnTotal;

    /** annualized rate of return[年化收益] */
    private Double aror;

    @Column(name = "product_id", length = 50, nullable = false)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "earn_week", length = 10, nullable = true)
    public Double getEarnWeek() {
        return earnWeek;
    }

    public void setEarnWeek(Double earnWeek) {
        this.earnWeek = earnWeek;
    }

    @Column(name = "earn_month", length = 10, nullable = true)
    public Double getEarnMonth() {
        return earnMonth;
    }

    public void setEarnMonth(Double earnMonth) {
        this.earnMonth = earnMonth;
    }

    @Column(name = "earn_total", length = 10, nullable = true)
    public Double getEarnTotal() {
        return earnTotal;
    }

    public void setEarnTotal(Double earnTotal) {
        this.earnTotal = earnTotal;
    }

    @Column(name = "aror", length = 10, nullable = true)
    public Double getAror() {
        return aror;
    }

    public void setAror(Double aror) {
        this.aror = aror;
    }

    @Override
    @Transient
    public void onSave() {
        createDate = new Date();
        modifyDate = new Date();
    }

    @Override
    @Transient
    public void onUpdate() {
        modifyDate = new Date();
    }

    public static String[] getUnUpdateField() {
        return new String[] { "createDate", "id", "productId" };
    }
}
