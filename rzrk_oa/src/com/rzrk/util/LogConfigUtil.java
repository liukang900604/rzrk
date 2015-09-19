/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.bean.LogConfig;

/**
 * 工具类 - 日志配置
 */

public class LogConfigUtil {

	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager
																			// Bean名称
	private static final String UNICORN_XML_FILE_NAME = "rzrk.xml";// rzrk.xml配置文件名称

	private static final String ALL_LOG_CONFIG_LIST_CACHE_KEY = "UNICORN_ALL_LOG_CONFIG_LIST";// 缓存Key

	/**
	 * 获得所有日志配置集合
	 * 
	 * @return 所有日志配置集合
	 */
	@SuppressWarnings("unchecked")
	public static List<LogConfig> getAllLogConfigList() {
		List<LogConfig> allLogConfigList = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			allLogConfigList = (List<LogConfig>) generalCacheAdministrator
					.getFromCache(ALL_LOG_CONFIG_LIST_CACHE_KEY);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				File rzrkXmlFile = null;
				Document document = null;
				try {
					rzrkXmlFile = new ClassPathResource(UNICORN_XML_FILE_NAME)
							.getFile();
					SAXReader saxReader = new SAXReader();
					document = saxReader.read(rzrkXmlFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Node> nodeList = document
						.selectNodes("/rzrk/logConfig/*");
				Iterator<Node> iterator = nodeList.iterator();

				allLogConfigList = new ArrayList<LogConfig>();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();

					LogConfig logConfig = new LogConfig();
					logConfig.setOperation(element.attributeValue("operation"));
					logConfig.setActionClass(element
							.attributeValue("actionClass"));
					logConfig.setActionMethod(element
							.attributeValue("actionMethod"));
					allLogConfigList.add(logConfig);
				}
				generalCacheAdministrator.putInCache(
						ALL_LOG_CONFIG_LIST_CACHE_KEY, allLogConfigList);
				updateSucceeded = true;
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator
							.cancelUpdate(ALL_LOG_CONFIG_LIST_CACHE_KEY);
				}
			}
		}
		return allLogConfigList;
	}

	/**
	 * 刷新日志配置缓存
	 * 
	 */
	public static void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(ALL_LOG_CONFIG_LIST_CACHE_KEY);
	}

}