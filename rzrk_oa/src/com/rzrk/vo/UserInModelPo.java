package com.rzrk.vo;


/**
 * @author Dell9020MT
 * 
 * 模型客户端用户PO
 *
 */
/**
 * @author Dell9020MT
 *
 */
public class UserInModelPo 
{	
	private String _id;              //
	private String account_type;     //账户类型
	private String count;            //最大同时登录数量
	private String etp_user;         //用户平台
	private String expiretime;       //
	private String interval;         //间隔
	private String isactive;         //是否激活
	private String isupload;         //是否上传
	private String modeltype;        //模型类型(只有0)
	private String pass;             //密码
	private String reguser;          //
	private String type;             //类型（1,2,3)
	private String user;			 //用户名
	private String modelauth;        //
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getEtp_user() {
		return etp_user;
	}
	public void setEtp_user(String etp_user) {
		this.etp_user = etp_user;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getIsupload() {
		return isupload;
	}
	public void setIsupload(String isupload) {
		this.isupload = isupload;
	}
	public String getModeltype() {
		return modeltype;
	}
	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getReguser() {
		return reguser;
	}
	public void setReguser(String reguser) {
		this.reguser = reguser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getModelauth() {
		return modelauth;
	}
	public void setModelauth(String modelauth) {
		this.modelauth = modelauth;
	}
	
}
