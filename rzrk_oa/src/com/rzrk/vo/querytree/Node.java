package com.rzrk.vo.querytree;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private String relation; //and 和 or
	private List<Node>children = new ArrayList<Node>();
	private Cond cond;
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public Cond getCond() {
		return cond;
	}
	public void setCond(Cond cond) {
		this.cond = cond;
	}
	@Override
	public String toString() {
		return "Node [relation=" + relation + ", children=" + children
				+ ", cond=" + cond + "]";
	}
	
}
