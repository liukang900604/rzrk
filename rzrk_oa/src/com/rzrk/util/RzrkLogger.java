/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rzrk.util.StringUtils;


/**                                                                                                                                    
 * Purpose: log util                                                                  
 * @version $Id$                                                          
 * @author songkez
 * @since 2012-5-17 
 */
public class RzrkLogger {

    private static Log logger = LogFactory.getLog("OSCLog");

    private static Log transLogger = LogFactory.getLog("Trans");

    /**
     * 得到日志记录类的类名和方法名 caller
     * @return log caller
     * @author songkez
     * @since 2012-5-17
     */
    private static String getCaller() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        try {
            for (int i = 0; i < stack.length; i++) {
                if ("getCaller".equals(stack[i].getMethodName())) {
                    return stack[i + 2].getClassName() + "."
                            + stack[i + 2].getMethodName() + "()";
                }
            }
        } catch (Exception expt) {
            error(expt.getMessage(), expt);
        }
        return StringUtils.EMPTY_STRING;

    }

    /**
     * 记录 information
     * @param message message to be log
     * @author songkez
     * @since 2012-5-17
     */
    public static void info(String message) {
        if (logger.isInfoEnabled()) {
            logger.info(getCaller() + " - " + message);
        }
    }

    /**
     * 记录 information
     * @param message log message
     * @param throwable exception throwed
     * @author songkez
     * @since 2012-5-17
     */
    public static void info(String message, Throwable throwable) {
        if (logger.isInfoEnabled()) {
            logger.info(getCaller() + " - " + message, throwable);
        }
    }

    /**
     * 记录debug message
     * @param message debug message
     * @author songkez
     * @since 2012-5-17
     */
    public static void debug(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(getCaller() + " - " + message);
        }
    }

    /**
     * 记录debug message
     * @param message debug message
     * @param throwable log exception
     * @author songkez
     * @since 2012-5-17
     */
    public static void debug(String message, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.debug(getCaller() + " - " + message, throwable);
        }
    }

    /**
     * 记录trace message
     * @param message trace message
     * @author songkez
     * @since 2012-5-17
     */
    public static void trace(String message) {
        if (logger.isTraceEnabled()) {
            logger.trace(getCaller() + " - " + message);
        }
    }

    /**
     * log trace message
     * @param message trace message
     * @param throwable log exception
     * @author songkez
     * @since 2012-5-17
     */
    public static void trace(String message, Throwable throwable) {
        if (logger.isTraceEnabled()) {
            logger.trace(getCaller() + " - " + message, throwable);
        }
    }

    /**
     * log warning message
     * @param message warining message
     * @author songkez
     * @since 2012-5-17
     */
    public static void warn(String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(getCaller() + " - " + message);
        }
    }

    /**
     * log warning message
     * @param message warning message
     * @param throwable log exception
     * @author songkez
     * @since 2012-5-17
     */
    public static void warn(String message, Throwable throwable) {
        if (logger.isWarnEnabled()) {
            logger.warn(getCaller() + " - " + message, throwable);
        }
    }

    /**
     * log error message
     * @param message error message
     * @author songkez
     * @since 2012-5-17
     */
    public static void error(String message) {
        if (logger.isErrorEnabled()) {
            logger.error(getCaller() + " - " + message);
        }
    }

    /**
     * log error message
     * @param message error message
     * @param throwable log exception
     * @author songkez
     * @since 2012-5-17
     */
    public static void error(String message, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(getCaller() + " - " + message, throwable);
        }
    }

    /**
     * log error message
     * @param throwable exception throwed
     * @author songkezao
     * @since 2011-9-30
     */
    public static void error(Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(getCaller() + " - ", throwable);
        }
    }

    /**
     * log fatal error
     * @param message fatal error
     * @author songkez
     * @since 2012-5-17
     */
    public static void fatal(String message) {
        if (logger.isFatalEnabled()) {
            logger.fatal(getCaller() + " - " + message);
        }
    }

    /**
     * log fatal error
     * @param message fatal error
     * @param throwable log exception
     * @author songkez
     * @since 2012-5-17
     */
    public static void fatal(String message, Throwable throwable) {
        if (logger.isFatalEnabled()) {
            logger.fatal(getCaller() + " - " + message, throwable);
        }
    }

    /**
     * 记录 information
     * @param message log information
     * @author songkez
     * @since 2012-5-17
     */
    public static void logTransaction(String message) {
        if (transLogger.isInfoEnabled()) {
            transLogger.info(getCaller() + " - " + message);
        }
    }

    /**
     * 记录 information
     * @param message log information
     * @param throwable log exception
     * @author songkez
     * @since 2012-5-17
     */
    public static void logTransaction(String message, Throwable throwable) {
        if (transLogger.isInfoEnabled()) {
            transLogger.info(getCaller() + " - " + message, throwable);
        }
    }

}
