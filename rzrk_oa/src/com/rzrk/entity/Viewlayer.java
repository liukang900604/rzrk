package com.rzrk.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.rzrk.util.JsonUtil;
import com.rzrk.vo.viewlayer.Definition;

@Entity
public class Viewlayer  extends BaseEntity{
	private String name;
	private String definition;
	private String primaryTable; //主表
	private String primaryField; //主键
	private Admin admin;
	
	Set<QueryHistory> queryHistorySet;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=10240)
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	@Transient
	public Definition getDefinitionObj(){
		if(StringUtils.isEmpty(definition)){
			return new Definition();
		}else{
			return JsonUtil.toObject(definition,Definition.class);
		}
	}
	
	@ManyToOne
	@JoinColumn
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public String getPrimaryTable() {
		return primaryTable;
	}
	public void setPrimaryTable(String primaryTable) {
		this.primaryTable = primaryTable;
	}
	public String getPrimaryField() {
		return primaryField;
	}
	public void setPrimaryField(String primaryField) {
		this.primaryField = primaryField;
	}
	
	@OneToMany(mappedBy="viewlayer",cascade=CascadeType.ALL,orphanRemoval=true)
	public Set<QueryHistory> getQueryHistorySet() {
		return queryHistorySet;
	}
	public void setQueryHistorySet(Set<QueryHistory> queryHistorySet) {
		this.queryHistorySet = queryHistorySet;
	}

	
}
