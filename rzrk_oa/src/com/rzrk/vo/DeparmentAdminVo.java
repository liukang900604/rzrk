package com.rzrk.vo;

/**
 * 部门人员vo
 * 
 * @author Dell
 *
 */
public class DeparmentAdminVo {

	private String adminId;// 人员id
	private String adminName;// 人员名称
	private String deparmentId;// 部门id
	private String deparmentName;// 部门名称
	
	

	/**
	 * @param adminId
	 * @param adminName
	 * @param deparmentId
	 * @param deparmentName
	 */
	public DeparmentAdminVo(String adminId, String adminName,
			String deparmentId, String deparmentName) {
		this.adminId = adminId;
		this.adminName = adminName;
		this.deparmentId = deparmentId;
		this.deparmentName = deparmentName;
	}
	
	public DeparmentAdminVo() {

	}


	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getDeparmentId() {
		return deparmentId;
	}

	public void setDeparmentId(String deparmentId) {
		this.deparmentId = deparmentId;
	}

	public String getDeparmentName() {
		return deparmentName;
	}

	public void setDeparmentName(String deparmentName) {
		this.deparmentName = deparmentName;
	}

}
