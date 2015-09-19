package com.rzrk.vo;

/**
 * model客户端在线用户PO类
 * @author Dell9020MT
 *
 */

public class UserLoginfPO {
	private String id;
	private String proxyip;
	private String user;
	private String clientip;
	private String clientport;
	private String datetime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProxyip() {
		return proxyip;
	}
	public void setProxyip(String proxyip) {
		this.proxyip = proxyip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	
	public String getClientport() {
		return clientport;
	}
	public void setClientport(String clientport) {
		this.clientport = clientport;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
}
