package com.rzrk.vo.queryhistory;

public class ViewlayerItem {
	private String id;
	private String viewlayerId;
	private String text;
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getViewlayerId() {
		return viewlayerId;
	}
	public void setViewlayerId(String viewlayerId) {
		this.viewlayerId = viewlayerId;
	}
	@Override
	public String toString() {
		return "ViewlayerItem [id=" + id + ", viewlayerId=" + viewlayerId
				+ ", text=" + text + ", content=" + content + "]";
	}
	
	
	
}
