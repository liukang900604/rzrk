package com.rzrk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class QueryHistory  extends BaseEntity{
	public static final int TYPE_TABLE=0;
	public static final int TYPE_VIEWLAYER=1;
	private String name;
	private String content;
	private ContractCategory contractCategory;
	private Admin admin;
	private Viewlayer viewlayer;
	private int type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=10240)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne
	@JoinColumn
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@ManyToOne
	@JoinColumn
	public ContractCategory getContractCategory() {
		return contractCategory;
	}
	public void setContractCategory(ContractCategory contractCategory) {
		this.contractCategory = contractCategory;
	}
	@ManyToOne
	@JoinColumn
	public Viewlayer getViewlayer() {
		return viewlayer;
	}
	public void setViewlayer(Viewlayer viewlayer) {
		this.viewlayer = viewlayer;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
