/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

/**                                                                                                                                    
 * Purpose: 产品预订                                                                    
 * @version 1.0                                                         
 * @author rzrk
 * @since 2013-9-23 
 */
@Entity
public class Reservations  extends BaseEntity{
	
	/**  */
	private static final long serialVersionUID = -2097013144641972338L;
	private Product product;
	private double reservAmount; //预约金额
	private String contactPerson; //联系人
	private String contactPhone;//联系电话
	private String contactMobile;//联系手机 
	
	private String fax; //传真
	private String email; //邮件
	private String companyName; //公司名称
	private String address; //联系地址
	
	private String zipCode;
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_reservations_product")
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Column(nullable = false)
	public double getReservAmount() {
		return reservAmount;
	}
	public void setReservAmount(double reservAmount) {
		this.reservAmount = reservAmount;
	}
	@Column(nullable = false)
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	@Column(nullable = false)
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@Column(nullable = false)
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getFax() {
		return fax == null ? "" : fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyName() {
		return companyName == null ? "" :  companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address == null ? "" : address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode == null ? "" : zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
