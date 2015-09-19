package com.rzrk.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 实体类 - 字段临时表
 * 
 * @author kang.liu
 */

@Entity
@Table(name = "rzrk_field_temp")
public class FieldTemp extends BaseEntity {

	private static final long serialVersionUID = -7519486823153844426L;

	private String id;// ID
	private String fieldId;//字段id
	private String value;//字段值

	/**
	 * 无参构造方法
	 */
	public FieldTemp() {
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    @Column(name="fieldId",length=200)
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	@Column(name="keyValue",length=50)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}