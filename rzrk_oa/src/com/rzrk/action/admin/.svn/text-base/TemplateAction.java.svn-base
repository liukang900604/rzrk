/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */
package com.rzrk.action.admin;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.bean.TemplateConfig;
import com.rzrk.util.TemplateConfigUtil;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台Action类 - 邮件模板
 */

@ParentPackage("admin")
public class TemplateAction extends BaseAdminAction {

	private static final long serialVersionUID = -1556710151369333272L;
	
	private TemplateConfig templateConfig;
	
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
        templateConfig = TemplateConfigUtil.getTemplateConfig(templateConfig.getName());
        templateConfig.setMailTemplateContent(TemplateConfigUtil.readTemplateFileContent(templateConfig.getMailTemplatePath()));
        templateConfig.setSmsTemplateContent(TemplateConfigUtil.readTemplateFileContent(templateConfig.getSmsTemplatePath()));
        return INPUT;
    }
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "templateConfig.mailTemplateContent", message = "邮件模板内容不允许为空!"),
			@RequiredStringValidator(fieldName = "templateConfig.smsTemplateContent", message = "短信模板内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		TemplateConfig persistent = TemplateConfigUtil.getTemplateConfig(templateConfig.getName());
        TemplateConfigUtil.writeTemplateFileContent(persistent.getMailTemplatePath(), templateConfig.getMailTemplateContent());
        TemplateConfigUtil.writeTemplateFileContent(persistent.getSmsTemplatePath(), templateConfig.getSmsTemplateContent());
        persistent.setDescription(templateConfig.getDescription());
        persistent.setSubject(templateConfig.getSubject());
        persistent.setEnabled(templateConfig.getEnabled());
        TemplateConfigUtil.updateTemplate(persistent);
		freemarkerManager.getConfiguration(getServletContext()).clearTemplateCache();
		redirectUrl = "template!list.action";
		return SUCCESS;
	}
	
	// 获取邮件模板配置集合
	public List<TemplateConfig> getAllTemplateConfigList() {
		return TemplateConfigUtil.getAllTemplateConfigList();
	}

	public TemplateConfig getTemplateConfig() {
		return templateConfig;
	}

	public void setTemplateConfig(TemplateConfig templateConfig) {
		this.templateConfig = templateConfig;
	}

}