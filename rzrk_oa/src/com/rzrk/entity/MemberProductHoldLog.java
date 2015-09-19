package com.rzrk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rzrk.Constants.ACTION_TYPE;

/**
 * Purpose: 用户持有的产品操作日志
 * @version 1.0
 * @author rzrk
 * @since 2013-9-23
 */
@Entity
@Table(name = "rzrk_member_product_hold_log")
public class MemberProductHoldLog extends BaseEntity {

    /**  */
    private static final long serialVersionUID = 466479516548401398L;

    private String memberId;

    private String productId;

    private Date actionDate;// 操作时间

    private ACTION_TYPE actionType; // 操作类型

    private double amount; // 操作数量
    
    private double lastAmount; //变更之后

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ACTION_TYPE getActionType() {
        return actionType;
    }

    public void setActionType(ACTION_TYPE actionType) {
        this.actionType = actionType;
    }

    @Column(nullable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(nullable = false, insertable = true, updatable = false)
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Column(nullable = false, insertable = true, updatable = false)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(nullable = false)
    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    private Product product;

    @Transient
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	@Column(nullable = false)
	public double getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(double lastAmount) {
		this.lastAmount = lastAmount;
	}
	

}
