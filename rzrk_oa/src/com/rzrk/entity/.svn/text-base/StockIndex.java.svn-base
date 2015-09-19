package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 股指对象
 * @version $Id$
 * @author nerve
 * @since 2013-10-12 下午4:44:02
 */
@Entity
@Table(name = "rzrk_stock")
public class StockIndex extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String stockId;// 股指ID

    private String date;// 结算日

    private Double start;// 开盘

    private Double end;// 收盘

    private Double max;// 最高

    private Double min;// 最低

    /** 非字段属性start */

    /** 增长率 */
    private Double growthRate;

    @Transient
    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    /** 非字段属性end */

    @Column(name = "stock_id", nullable = false)
    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @Column(nullable = false)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(nullable = false)
    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    @Column(nullable = false)
    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    @Column(nullable = false)
    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    @Column(nullable = false)
    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public static String[] getUnUpdateField() {
        return new String[] { "createDate", "updateDate", "id", "date", "stockId" };
    }

}
