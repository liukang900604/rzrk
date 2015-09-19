package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Purpose: 用户持有的产品
 * @version $Id$
 * @author rzrk
 * @since 2013-9-23
 */
/**
 * @author rzrk
 *
 */
@Entity
@Table(name = "rzrk_member_product_hold")
public class MemberProductHold extends BaseEntity {

    /**  */
    private static final long serialVersionUID = 466479516548401398L;

    private String memberId;

    private String productId;

//    private Member member; // 用户

//    private Product product; // 产品

    private Double totalAmount; // 持有数量

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    public Member getMember() {
//        return member;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }

    
    
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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
    
    private Product product;

    @Transient
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	private ProductNetValue productNetValue;
	@Transient
	public ProductNetValue getProductNetValue() {
		return productNetValue;
	}

	public void setProductNetValue(ProductNetValue productNetValue) {
		this.productNetValue = productNetValue;
	}

	
    
    
}
