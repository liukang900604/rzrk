package com.rzrk.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rzrk.action.admin.BaseAdminAction;
import com.rzrk.bean.LogConfig;
import com.rzrk.entity.Log;
import com.rzrk.service.AdminService;
import com.rzrk.service.LogService;
import com.rzrk.util.LogConfigUtil;

/**
 * 拦截器 - 管理日志
 */

public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 276741467699160227L;

	@Resource(name = "logServiceImpl")
	private LogService logService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = invocation.invoke();
		
		Object action = invocation.getAction();
		String actionClass = action.getClass().getName();
		String actionMethod = invocation.getProxy().getMethod();
		
		if (action instanceof BaseAdminAction) {
			if (!StringUtils.equals(result, BaseAdminAction.ERROR)) {
				List<LogConfig> allLogConfigList = LogConfigUtil.getAllLogConfigList();
				if (allLogConfigList != null) {
					for (LogConfig logConfig : allLogConfigList) {
						if (StringUtils.equals(logConfig.getActionClass(), actionClass) && StringUtils.equals(logConfig.getActionMethod(), actionMethod)) {
							BaseAdminAction baseAdminAction = (BaseAdminAction) action;
							HttpServletRequest request= ServletActionContext.getRequest();
							
							String logInfo = baseAdminAction.getLogInfo();
							String operator = adminService.getLoginAdmin().getUsername();
							if(operator == null) {
								operator = "未知";
							}
							String ip = request.getRemoteAddr();
							String operation = logConfig.getOperation();
							
							Log log = new Log();
							log.setOperation(operation);
							log.setOperator(operator);
							log.setActionClass(actionClass);
							log.setActionMethod(actionMethod);
							log.setIp(ip);
							log.setInfo(logInfo);
							logService.save(log);
							break;
						}
					}
				}
			}
		}
		return null;
	}

}