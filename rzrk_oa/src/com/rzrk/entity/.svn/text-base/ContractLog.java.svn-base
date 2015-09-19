package com.rzrk.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.asm.Type;
import com.rzrk.util.JsonUtil;
import com.rzrk.vo.ContractLogItemVo;
@Entity
public class ContractLog  extends BaseEntity{
	
	private Admin operator;
	
	private String content;	//变化描述
	private String fields;	//涉及的字段
	
	private ContractCategory contractCategory;
	private String rowId;
	
	
	@ManyToOne
	@JoinColumn
	@OrderBy("createDate asc")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(length=10240)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length=2048)
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@ManyToOne()
	@JoinColumn
	@OrderBy("createDate asc")
	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(ContractCategory contractCategory) {
		this.contractCategory = contractCategory;
	}
	
	@Transient
	public void setContractLogItemList(List<ContractLogItemVo> logList){
		String content = JsonUtil.toJson(logList);
		setContent(content);
		String fields = StringUtils.join(CollectionUtils.collect(logList, new Transformer() {
			@Override
			public Object transform(Object arg0) {
				ContractLogItemVo log = (ContractLogItemVo)arg0;
				return log.getFieldName();
			}
		}),",");
		setFields(fields);
	}
	@Transient
	public List<ContractLogItemVo> getContractLogItemList(){
		return JSON.parseObject(StringUtils.defaultString(content,"[]"), new TypeReference<List<ContractLogItemVo> >(){});
	}
	
	
	

}
