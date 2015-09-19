<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.security.BadCredentialsException"%>
<%@page import="com.rzrk.util.SpringUtil"%>
<%@page import="com.rzrk.service.AdminService"%>
<%@page import="com.rzrk.entity.Admin"%>
<%@page import="com.rzrk.util.SettingUtil"%>
<%@page import="com.rzrk.bean.Setting"%>
<%@page import="org.springframework.security.DisabledException"%>
<%@page import="org.springframework.security.LockedException"%>
<%@page import="org.springframework.security.AccountExpiredException"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);

	final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";

	String base = request.getContextPath();
	ApplicationContext applicationContext = SpringUtil.getApplicationContext();
	if (applicationContext == null) {
		return;
	}

	AdminService adminService = (AdminService) SpringUtil.getBean("adminServiceImpl");
	Setting setting = SettingUtil.getSetting();
	String adminLoginProcessingUrl = setting.getAdminLoginProcessingUrl();
	String message = null;
	String username = null;
	
	String error = StringUtils.trim(request.getParameter("error"));
	if (StringUtils.equalsIgnoreCase(error, "captcha")) {
		message = "验证码错误,请重新输入!";
	} else {
		Exception springSecurityLastException = (Exception) session.getAttribute(SPRING_SECURITY_LAST_EXCEPTION);
		if (springSecurityLastException != null) {
			username = ((String) session.getAttribute("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
			Admin admin = adminService.getAdminByUsername(username);
			if (null != admin && admin.getManType() == 3){
				message = "您已离职,无法登录!";
			}else if (springSecurityLastException instanceof BadCredentialsException) {
	
	
				if (admin != null) {
					int loginFailureLockCount = setting.getLoginFailureLockCount();
					int loginFailureCount = admin.getLoginFailureCount();
					if (setting.getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						message = "若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!";
					} else {
						message = "您的用户名或密码错误!";
					}
				} else {
					message = "您的用户名或密码错误!";
				}
			} else if (springSecurityLastException instanceof DisabledException) {
				message = "您的账号已被禁用,无法登录!";
			} else if (springSecurityLastException instanceof LockedException) {
				message = "您的账号已被锁定,无法登录!";
			} else if (springSecurityLastException instanceof AccountExpiredException) {
				message = "您的账号已过期,无法登录!";
			} else {
				message = "出现未知错误,无法登录!";
			}
			session.removeAttribute(SPRING_SECURITY_LAST_EXCEPTION);
		}
	}
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="x-ua-compatible" content="ie-edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css"/>
    <link rel="stylesheet" href="/rzrk/css/background/login.css"/>
    <script src="/rzrk/js/alljs.js"></script>
    <script src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script>
    	var message = "";
		<%if (message != null) {%>
			message = "<%=message%>";
		<%}%>
    </script>
    <script src="/rzrk/js/background/login.js"></script>
    <title>睿智融科BEST企业管理系统</title>
</head>
<body>
<div class="feature">
    <img src="/rzrk/images/background/welcome.png" alt=""/>
</div>
<div class="login-box">
    <form id="loginForm" action="<%=base%><%=adminLoginProcessingUrl%>" method="post">
        <input type="text" id="username" name="j_username" placeholder="用户名"/>
        <input type="password" id="password" name="j_password" placeholder="密码"/>
        <a href="javascript:" class="btn" id="submitForm">登 录</a>
        <label for="isRememberUsername"><input type="checkbox" id="isRememberUsername" />记住用户名</label>
    </form>
</div>
<footer>
    Copyright&copy;2015 ThinkTrader All Right Reserved.
</footer>
</body>
</html>