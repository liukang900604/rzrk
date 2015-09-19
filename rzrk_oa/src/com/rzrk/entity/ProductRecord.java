/**
 *
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
                     
 */
package com.rzrk.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * 实体类 - 人员
 */

/**
 * @author songkez
 *
 */
@Entity
public class ProductRecord extends BaseEntity  {

	private static final long serialVersionUID = -7519486823153844426L;
	
	
	private String productDetailId;// 产品要素id
	private int extractIndex;// 提取index 0 开始
	private java.sql.Date extractDate;// 提取日期
	
	private String productName; //产品名称 便于观察
	
	private boolean sendEmail;//发送提醒邮件
	private boolean writeBill;// 写入账单
	
	private double nac;//NACTn 为第n次合同业绩报酬支付日未扣除当期业绩报酬单位净值
	
	private double nav; //NAVTn支付日已扣除业绩报酬的资产单位净值（NAVT0等于1.0）, 计算公式：NACTn-（NACTn-NAVTn-1）×业绩报酬费率, 每次运算完成之后需要写入该值
	


	public double getNac() {
		return nac;
	}


	public void setNac(double nac) {
		this.nac = nac;
	}


	public double getNav() {
		return nav;
	}


	public void setNav(double nav) {
		this.nav = nav;
	}


	public String getProductDetailId() {
		return productDetailId;
	}
	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}
	public int getExtractIndex() {
		return extractIndex;
	}
	public void setExtractIndex(int extractIndex) {
		this.extractIndex = extractIndex;
	}
	public java.sql.Date getExtractDate() {
		return extractDate;
	}
	public void setExtractDate(java.sql.Date extractDate) {
		this.extractDate = extractDate;
	}
	public boolean isSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}
	public boolean isWriteBill() {
		return writeBill;
	}
	public void setWriteBill(boolean writeBill) {
		this.writeBill = writeBill;
	}
	
	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	@Override
	public String toString() {
		return "ProductRecord [productDetailId=" + productDetailId
				+ ", extractIndex=" + extractIndex + ", extractDate="
				+ extractDate + ", productName=" + productName + ", sendEmail="
				+ sendEmail + ", writeBill=" + writeBill + ", nac=" + nac
				+ ", nav=" + nav + "]";
	}


	
	

	
	
	
	
	
	
	
}