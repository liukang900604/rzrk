package com.rzrk.vo.queryhistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeItem {
	private String id;
	private String text;
	private String icon;
	private List<TreeItem> children = new ArrayList<TreeItem>();
	private Map<String,Object> state = new HashMap<String, Object>();
	private Map<String,Object> data = new HashMap<String, Object>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		this.data.put("id", id);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeItem> getChildren() {
		return children;
	}
	public void setChildren(List<TreeItem> children) {
		this.children = children;
	}
	public void addChild(TreeItem item){
		this.children.add(item);
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public void putDate(String key,Object value){
		this.data.put(key, value);
	}
	public Map<String, Object> getState() {
		return state;
	}
	public void setState(Map<String, Object> state) {
		this.state = state;
	}
	public void putState(String key,Object value){
		this.state.put(key, value);
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	
}
