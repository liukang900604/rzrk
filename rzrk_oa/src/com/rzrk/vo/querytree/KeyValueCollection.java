package com.rzrk.vo.querytree;

import java.util.LinkedHashMap;

public class KeyValueCollection {
    private LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
    private LinkedHashMap<String, String> operateMap = new LinkedHashMap<String, String>();
    private int index = 0;
    

	public LinkedHashMap<String, Object> getParamMap() {
		return paramMap;
	}
	public void setParamMap(LinkedHashMap<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	public LinkedHashMap<String, String> getOperateMap() {
		return operateMap;
	}
	public void setOperateMap(LinkedHashMap<String, String> operateMap) {
		this.operateMap = operateMap;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void addIndex() {
		this.index++;
	}
    
}
