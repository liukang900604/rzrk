package com.rzrk.entity;

import javax.persistence.Entity;

@Entity
public class WorkingDayRecord extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private String content;//工作日数组
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
