package com.rzrk.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rzrk.contants.UserPlanContants;

/**
 * 拦截器 - 管理日志
 */

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 276741467699160227L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String object = (String) invocation.getInvocationContext().getSession().get("SPRING_SECURITY_LAST_USERNAME");
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
	
		 if(StringUtils.isEmpty(object)){
			 if(request.getRequestURL().toString().contains(UserPlanContants.FITER_URL)){
				HttpServletResponse response =  (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);
				String id = (String) request.getParameter("id");
				 Cookie  cookie = new Cookie("fiterUrlName",UserPlanContants.FITER_URL.toString()+"?id="+id);
				 cookie.setMaxAge(3600);//一小时生效
				 cookie.setPath("/");
				 response.addCookie(cookie);
			 }
			 return "loginPage";
			
		 }else{
			 return invocation.invoke();
		 }
		
	}

}