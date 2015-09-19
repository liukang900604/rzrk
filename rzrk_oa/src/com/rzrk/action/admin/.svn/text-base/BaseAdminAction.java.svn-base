/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rzrk.bean.Pager;
import com.rzrk.bean.Setting;
import com.rzrk.bean.Pager.Order;
import com.rzrk.entity.Admin;
import com.rzrk.service.AdminService;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.SettingUtil;

/**
 * 后台Action类 - 后台基类
 */

public class BaseAdminAction extends ActionSupport {

	private static final long serialVersionUID = 6718838822334455667L;
	
	private static final String HEADER_ENCODING = "UTF-8";
	private static final boolean HEADER_NO_CACHE = true;
	private static final String HEADER_TEXT_CONTENT_TYPE = "text/plain";
	private static final String HEADER_JSON_CONTENT_TYPE = "text/plain";
	
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String REDIRECT = "redirect";
	public static final String PAGEURL = "pageUrl";
	
	public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
	public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称
	public static final String DATA_PARAMETER_NAME = "data";// 操作消息参数名称

    protected final Logger logger = Logger.getLogger(this.getClass());

	// 操作状态（警告、错误、成功）
	public enum Status {
		warn, error, success
	}
	
	protected String id;
	protected String[] ids;
	protected Pager pager = new Pager();
	protected String logInfo;// 日志记录信息
	protected String redirectUrl;// 跳转URL
	protected String pageUrl;//页面url  格式为 /WEB-INF/template/admin/指定的页面

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Override
    public String input() {
		return NONE;
	}

	// 获取系统配置信息
	public Setting getSetting() {
		return SettingUtil.getSetting();
	}
	
	// 获取货币格式字符串
	public String getCurrencyFormat() {
		return SettingUtil.getCurrencyFormat();
	}
	
	// 判断是否为添加
	public Boolean getIsAddAction() {
		if (id == null) {
			return true;
		} else {
			return false;
		}
	}
	
	// 判断是否为编辑
	public Boolean getIsEditAction() {
		if (id != null) {
			return true;
		} else {
			return false;
		}
	}
	
	// 获取Request
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取ServletContext
	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	// 获取Attribute
	protected Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	protected void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	protected String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	// 获取Parameter数组
	protected String[] getParameterValues(String name) {
		return ServletActionContext.getRequest().getParameterValues(name);
	}

	// 获取Session
	protected Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}
	
	// 设置Session
	protected void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 移除Session
	protected void removeSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.remove(name);
	}
	
	// 获取Cookie
	protected String getCookie(String name) {
		Cookie cookies[] = ServletActionContext.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	// 设置Cookie
	protected void setCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
 		ServletActionContext.getResponse().addCookie(cookie);
	}
	
	// 设置Cookie
	protected void setCookie(String name, String value, Integer maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
 		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 移除Cookie
	protected void removeCookie(String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath(ServletActionContext.getRequest().getContextPath() + "/");
		ServletActionContext.getResponse().addCookie(cookie);
	}

	// 获取真实路径
	protected String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}
	
	// 获取ContextPath
	protected String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	// AJAX输出
	protected String ajax(String content, String contentType) {
		try {
			HttpServletResponse response = initResponse(contentType);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	// 根据文本内容输出AJAX
	protected String ajax(String text) {
		return ajax(text, HEADER_TEXT_CONTENT_TYPE);
	}
	
	// 根据操作状态输出AJAX
	protected String ajax(Status status) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		JsonUtil.toJson(response, jsonMap);
		return NONE;
	}
	
	// 根据操作状态、消息内容输出AJAX
	protected String ajax(Status status, String message) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		JsonUtil.toJson(response, jsonMap);
		return NONE;
	}
	
	// 根据操作状态、消息内容输出AJAX
	protected String ajax(Status status, String message, Object object) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		jsonMap.put(DATA_PARAMETER_NAME, object);
		JsonUtil.toJson(response, jsonMap);
		return NONE;
	}
	
	// 根据Object输出AJAX
	protected String ajax(Object object) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
			JsonUtil.toJson(response, object);
		return NONE;
	}
	
	// 根据boolean状态输出AJAX
	protected String ajax(boolean booleanStatus) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, booleanStatus);
		JsonUtil.toJson(response, jsonMap);
		return NONE;
	}
	
	private HttpServletResponse initResponse(String contentType) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(contentType + ";charset=" + HEADER_ENCODING);
		if (HEADER_NO_CACHE) {
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		return response;
	}
	
	/**
	 * 处理分页ajax请求参数
	 */
	protected void processAjaxPagerRequestParameter(){
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(getRequest().getParameter("page"));
		} catch (NumberFormatException e) {
			
		}
		int pageSize = Integer.MAX_VALUE;
		try {
			pageSize = Integer.parseInt(getRequest().getParameter("rows"));
		} catch (NumberFormatException e) {
			
		}
		String searchBy = getRequest().getParameter("searchBy");// 查找字段
		String keyword = getRequest().getParameter("keyword");// 查找关键字
		String orderBy = getRequest().getParameter("sort");// 排序字段
		String orderStr = getRequest().getParameter("order");// 排序方式
		String isCunxu = getRequest().getParameter("isCunxu");
		String hasNoRoot = getRequest().getParameter("hasNoRoot");
		
   
		pager.setPageNumber(pageNo);
		pager.setPageSize(pageSize);
		pager.setSearchBy(searchBy);
		pager.setKeyword(keyword);
		pager.setIsCunxu(isCunxu);
		pager.setHasNoRoot(hasNoRoot);
		
		
	    if(null != orderStr && null != orderBy){
	    	pager.setOrderBy(orderBy);
	    	if (orderStr.equals("asc")){
	    		pager.setOrder(Order.asc);
	    	}else{
	    		pager.setOrder(Order.desc);
	    	}
	    	
	    	
	    }
	    

		
	}
	
	/**
	 * get login admin 
	 * @return logi admin
	 * @author songkez
	 * @since  2012-5-30 
	 */
	protected Admin getLoginAdmin(){
		return adminService.getAdminByUsername((String) getSession("SPRING_SECURITY_LAST_USERNAME"));
	}

    protected boolean isNull(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
	

}