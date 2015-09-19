package com.rzrk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SerialNumber {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	String type;
	long value;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}

	
	
}
