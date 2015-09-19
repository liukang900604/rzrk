package com.rzrk.vo.querytree;

public class Cond {
	public static final String eq="eq";
	public static final String ne="ne";
	public static final String has="has";
	public static final String nohas="nohas";
	public static final String in="in";
	public static final String notin="notin";
	
	private String searchBy;
	private String operate;
	private String keyword;
	public String getSearchBy() {
		return searchBy;
	} 
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "Cond [searchBy=" + searchBy + ", operate=" + operate
				+ ", keyword=" + keyword + "]";
	}
	
}
