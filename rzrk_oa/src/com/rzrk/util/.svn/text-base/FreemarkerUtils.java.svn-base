/*
 * 
 * 
 * 
 */
package com.rzrk.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;

/**
 * Utils - Freemarker
 * 
 * 
 * 
 */
@SuppressWarnings("unchecked")
public final class FreemarkerUtils {
	/**
	 * 不可实例化
	 */
	private FreemarkerUtils() {
	}

	/**
	 * 解析字符串模板
	 * 
	 * @param template
	 *            字符串模板
	 * @param model
	 *            数据
	 * @return 解析后内容
	 */
	public static String process(String template, Map<String, ?> model) throws IOException, TemplateException {
		Configuration configuration = null;
		ApplicationContext applicationContext = SpringUtil.getApplicationContext();
		if (applicationContext != null) {
			Object _configuration = SpringUtil.getBean("freeMarkerConfigurationFactoryBean");
			configuration = (Configuration)_configuration;
		}
		return process(template, model, configuration);
	}

	/**
	 * 解析字符串模板
	 * 
	 * @param template
	 *            字符串模板
	 * @param model
	 *            数据
	 * @param configuration
	 *            配置
	 * @return 解析后内容
	 */
	public static String process(String template, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
		if (template == null) {
			return null;
		}
		if (configuration == null) {
			configuration = new Configuration();
		}
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), configuration).process(model, out);
		return out.toString();
	}

	/**
	 * 解析字符串模板
	 * 
	 * @param template
	 *            字符串模板
	 * @param model
	 *            数据
	 * @return 解析后内容
	 */
	public static String processFile(String templatepPath, Map<String, ?> model) throws IOException, TemplateException {
		Configuration configuration = null;
		ApplicationContext applicationContext = SpringUtil.getApplicationContext();
		if (applicationContext != null) {
			Object _configuration = SpringUtil.getBean("freeMarkerConfigurationFactoryBean");
			configuration = (Configuration)_configuration;
		}
		return processFile(templatepPath, model, configuration);
	}

	/**
	 * 解析字符串模板
	 * 
	 * @param template
	 *            字符串模板
	 * @param model
	 *            数据
	 * @param configuration
	 *            配置
	 * @return 解析后内容
	 */
	public static String processFile(String templatepPath, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
		if (templatepPath == null) {
			return null;
		}
		if (configuration == null) {
			configuration = new Configuration();
		}
		StringWriter out = new StringWriter();
		configuration.getTemplate(templatepPath).process(model, out);
		return out.toString();
	}



}