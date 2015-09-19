/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;


import com.rzrk.bean.TemplateConfig;
import com.rzrk.dao.MailSmsLogDao;
import com.rzrk.entity.MailSmsLog;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.ServletContextAware;

import com.rzrk.bean.MailTemplateConfig;
import com.rzrk.bean.Setting;
import com.rzrk.entity.Member;
import com.rzrk.service.MailService;
import com.rzrk.util.SettingUtil;
import com.rzrk.util.TemplateConfigUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Service实现类 - 邮件服务
 */

@Service("mailServiceImpl")
public class MailServiceImpl implements MailService, ServletContextAware {

	private ServletContext servletContext;
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;
	@Resource(name = "javaMailSender")
	private JavaMailSender javaMailSender;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
    @Resource(name = "mailSmsLogDaoImpl")
    private MailSmsLogDao mailSmsLogDao;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	// 增加邮件发送任务
	public void addSendMailTask(final MimeMessage mimeMessage,final MailSmsLog messageLog) {
		try {
			taskExecutor.execute(
				new Runnable() {
					public void run() {
                        try {
                            javaMailSender.send(mimeMessage);
                            System.out.println("邮件反送成功");

                        } catch (MailException e) {
                            System.out.println("邮件反送失败");
                        }
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail(String subject, String templatePath, Map<String, Object> data, String toMail,MailSmsLog messageLog) {
		try {
			Setting setting = SettingUtil.getSetting();
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(setting.getSmtpHost());
			javaMailSenderImpl.setPort(setting.getSmtpPort());
			javaMailSenderImpl.setUsername(setting.getSmtpUsername());
			javaMailSenderImpl.setPassword(setting.getSmtpPassword());
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getShopName()) + " <" + setting.getSmtpFromMail() + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
            messageLog.setSubject(subject);
			addSendMailTask(mimeMessage, messageLog);
		} catch (Exception e) {
            messageLog.setDesc("sendMail error");
            messageLog.setStatus(MailSmsLog.Status.error);
            mailSmsLogDao.save(messageLog);
			e.printStackTrace();
		}
	}
	
	public void sendMail(String subject, String text, String toMail) {
		try {
			Setting setting = SettingUtil.getSetting();
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(setting.getSmtpHost());
			javaMailSenderImpl.setPort(setting.getSmtpPort());
			javaMailSenderImpl.setUsername(setting.getSmtpUsername());
			javaMailSenderImpl.setPassword(setting.getSmtpPassword());
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getShopName()) + " <" + setting.getSmtpFromMail() + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			addSendMailTask(mimeMessage, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", getContextPath());
		commonData.put("setting", SettingUtil.getSetting());
		return commonData;
	}
	
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		Setting setting = SettingUtil.getSetting();
		Map<String, Object> data = getCommonData();
		TemplateConfig mailTemplateConfig = TemplateConfigUtil.getTemplateConfig(MailTemplateConfig.SMTP_TEST);
        if(!mailTemplateConfig.getEnabled()){
            return;
        }
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getMailTemplatePath();
		try {
			JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
			javaMailSenderImpl.setHost(smtpHost);
			javaMailSenderImpl.setPort(smtpPort);
			javaMailSenderImpl.setUsername(smtpUsername);
			javaMailSenderImpl.setPassword(smtpPassword);
			MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getShopName()) + " <" + smtpFromMail + ">");
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendPasswordRecoverMail(Member member) {
		Map<String, Object> data = getCommonData();
		data.put("member", member);
		TemplateConfig mailTemplateConfig = TemplateConfigUtil.getTemplateConfig(MailTemplateConfig.PASSWORD_RECOVER);
        if(!mailTemplateConfig.getEnabled()){
            return;
        }
		String subject = mailTemplateConfig.getSubject();
		String templatePath = mailTemplateConfig.getMailTemplatePath();
        MailSmsLog messageLog = new MailSmsLog();
        messageLog.setMember(member);
        messageLog.setReceiver(member.getEmail());
        messageLog.setType(MailSmsLog.Type.email);
        messageLog.setSubject(subject);
        sendMail(subject, templatePath, data, member.getEmail(), messageLog);
	}
	
	
	/**
	 * 获取虚拟路径
	 * 
	 * @return 虚拟路径
	 */
	private String getContextPath() {
		if (servletContext.getMajorVersion() < 2 || (servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() < 5)) {
			return SettingUtil.getSetting().getContextPath();
		} else {
			return servletContext.getContextPath();
		}
	}

}