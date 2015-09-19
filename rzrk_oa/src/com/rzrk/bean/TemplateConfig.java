/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */
package com.rzrk.bean;


/**
 * Bean类 - 邮件模板配置
 */

public class TemplateConfig {
	
	public static final String TEST = "test";// SMTP邮箱配置测试
	public static final String PASSWORD_RECOVER = "passwordRecover";// 密码找回
	public static final String GOODS_NOTIFY = "goodsNotify";// 到货通知
	
	private String name;// 配置名称
	private String description;// 描述
	private String subject;// 主题
	private String mailTemplatePath;// mail Freemarker模板文件路径
    private String smsTemplatePath; // sms Freemarker模板文件路径

    private String mailTemplateContent;  //mail 模板内容
    private String smsTemplateContent;   //短信模板内容

    private Boolean isEnabled;  //是否开启
    private String varDesc;   //变量说明

    public String getVarDesc() {
        return varDesc;
    }

    public void setVarDesc(String varDesc) {
        this.varDesc = varDesc;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getMailTemplateContent() {
        return mailTemplateContent;
    }

    public void setMailTemplateContent(String mailTemplateContent) {
        this.mailTemplateContent = mailTemplateContent;
    }

    public String getSmsTemplateContent() {
        return smsTemplateContent;
    }

    public void setSmsTemplateContent(String smsTemplateContent) {
        this.smsTemplateContent = smsTemplateContent;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getMailTemplatePath() {
        return mailTemplatePath;
    }

    public void setMailTemplatePath(String mailTemplatePath) {
        this.mailTemplatePath = mailTemplatePath;
    }

    public String getSmsTemplatePath() {
        return smsTemplatePath;
    }

    public void setSmsTemplatePath(String smsTemplatePath) {
        this.smsTemplatePath = smsTemplatePath;
    }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}