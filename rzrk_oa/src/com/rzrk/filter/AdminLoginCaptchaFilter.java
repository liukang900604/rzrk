/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.rzrk.util.CaptchaUtil;
import com.rzrk.util.SettingUtil;

/**
 * 过滤器 - 后台登录验证码
 */

@Component("adminLoginCaptchaFilter")
public class AdminLoginCaptchaFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean flag = CaptchaUtil.validateCaptchaByRequest(request);
        // 注释掉验证码
        flag = true;

        if (flag) {
            filterChain.doFilter(request, response);
        } else {
            String adminLoginUrl = SettingUtil.getSetting().getAdminLoginUrl();
            response.sendRedirect(request.getContextPath() + adminLoginUrl + "?error=captcha");
        }
    }

    @Override
    public void destroy() {
    }

}