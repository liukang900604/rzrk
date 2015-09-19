package com.rzrk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 产品净值表 */
@Entity
@Table(name = "rzrk_product_net_value")
public class ProductNetValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 产品ID */
    private String productId;
    /**
     * 日期类型<br/>
     * 100:估值日,101:分配日,102:开放日,103:成立日,104:期初,此处仅保留100,103
     */
    private Integer dateType;

    /** 日期 */
    private Date date;

    /** 信托单位净值(录入的净值) */
    private Double trustValue;

    /** 累计净值起始点 */
    private Double trustValueStart;

    /** 信托单位累计净值 */
    private Double trustValueAdd;

    /** 累计增长率 */
    private Double growthRateAdd;

    /** 当月增长率 */
    @Deprecated
    private Double growthRateMonth;

    /** 增长率 */
    @Deprecated
    private Double growthRate;

    @Column(name = "product_id", length = 50, nullable = false)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "date_type", length = 1, nullable = false)
    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "trust_value", nullable = false)
    public Double getTrustValue() {
        return trustValue;
    }

    public void setTrustValue(Double trustValue) {
        this.trustValue = trustValue;
    }

    @Column(name = "trust_value_start", nullable = true)
    public Double getTrustValueStart() {
        return trustValueStart;
    }

    public void setTrustValueStart(Double trustValueStart) {
        this.trustValueStart = trustValueStart;
    }

    @Column(name = "trust_value_add", nullable = true)
    public Double getTrustValueAdd() {
        return trustValueAdd;
    }

    public void setTrustValueAdd(Double trustValueAdd) {
        this.trustValueAdd = trustValueAdd;
    }

    @Deprecated
    @Column(name = "growth_rate_month", nullable = true)
    public Double getGrowthRateMonth() {
        return growthRateMonth;
    }

    @Deprecated
    public void setGrowthRateMonth(Double growthRateMonth) {
        this.growthRateMonth = growthRateMonth;
    }

    @Column(name = "growth_rate_add", nullable = true)
    public Double getGrowthRateAdd() {
        return growthRateAdd;
    }

    public void setGrowthRateAdd(Double growthRateAdd) {
        this.growthRateAdd = growthRateAdd;
    }

    @Deprecated
    @Column(name = "growth_rate", nullable = true)
    public Double getGrowthRate() {
        return growthRate;
    }

    @Deprecated
    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
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