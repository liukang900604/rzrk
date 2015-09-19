package com.rzrk.common.contract;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("contractParseManager")
public class ContractParseManager {
	
	@Resource(name="contractParserUser")
	ContractParserUser contractParserUser;
	
	@Resource(name="contractParserDepartment")
	ContractParserDepartment contractParserDepartment;
	
	@Resource(name="contractParserJob")
	ContractParserJob contractParserJob;
	
	@Resource(name="contractParserProduct")
	ContractParserProduct contractParserProduct;
	
	Map<String,ContractParser> map;
	
	public Map<String,ContractParser> getContractParserList(){
		LinkedHashMap<String, ContractParser> map = new LinkedHashMap<String, ContractParser>();
		map.put(contractParserUser.getName(), contractParserUser);
		map.put(contractParserDepartment.getName(), contractParserDepartment);
		map.put(contractParserJob.getName(), contractParserJob);
		map.put(contractParserProduct.getName(), contractParserProduct);
		return map;
	}
	
	public ContractParser getContractParser(String str){
		if(map==null){
			map = getContractParserList();
		}
		return map.get(str);
	}
	
}
