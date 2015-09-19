package com.rzrk.action.admin;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.Admin;
import com.rzrk.service.AdminService;

@ParentPackage("admin")
public class PassChangeAction extends BaseAdminAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	private Admin admin;
	private String password;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	
	public String list() {
		return INPUT;
	}
	
	/**
	 * 验证旧密码
	 * @return
	 */
	public String checkPassword() {
		String passwordMd5 = DigestUtils.md5Hex(getPassword());
		String oldPassword = getLoginAdmin().getPassword();
		if (passwordMd5.equals(oldPassword)) {
			return ajax("true");
		}
		return ajax("false");
		
	}
	/**
	 * 修改密码
	 * @return
	 */
	public String updatePassword() {
		Admin logAdmin = getLoginAdmin();
		String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
		logAdmin.setPassword(passwordMd5);
		adminService.update(logAdmin);
		logInfo = "修改密码";
		return  ajax(Status.success, "修改成功!");		
	}

}
