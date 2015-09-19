/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import com.rzrk.util.RzrkLogger;

/**
 * Purpose: log日志配置信息加载类
 * @version 1.0
 * @author songkez
 * @since 2012-5-17
 */
public class InitLog4jListener implements ServletContextListener {

    /*  @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)  */
    @Override
    public void contextDestroyed(ServletContextEvent context) {
    }

    /*  @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)  */
    @Override
    public void contextInitialized(ServletContextEvent context) {

        String file = System.getProperty("log4j");
        RzrkLogger.info("log4j file path: " + file);

        if (file != null) {
            Properties ps = new Properties();
            try {
                ps.load(new FileInputStream(file));
                // UnicornLogger.info("log4j file load successfully.");
            } catch (IOException ioe) {
                RzrkLogger.error("Load log4j.properties failed", ioe);
            }
            PropertyConfigurator.configure(ps);
        }
    }

}
