/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Purpose: Date tools
 * @version 1.0
 * @author songkez
 * @since 2011-8-3
 */
public class DateUtils {

    private static final String DFLT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE = "yyyy-MM-dd";
    private static final String DATE_STOCK = "yyyyMMdd";

    public static Date parseDate(String date) {
        SimpleDateFormat s = new SimpleDateFormat(DATE);
        Date result = null;
        try {
            result = s.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = s.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date parseDateTime(String datetime) {
        SimpleDateFormat s = new SimpleDateFormat(DFLT_PATTERN);
        Date result = null;
        try {
            result = s.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get current date of locale
     * @param pattern date pattern
     * @param locale locale info
     * @return time string
     * @author songkez
     * @since Aug 19, 2011
     */
    public static String getCurrentDate(String pattern, Locale locale) {
        return new SimpleDateFormat(pattern).format(Calendar.getInstance(locale).getTime());
    }

    /**
     * get current date of locale
     * @param pattern date pattern
     * @return time string
     * @author songkez
     * @since Aug 19, 2011
     */
    public static String getCurrentDate(String pattern) {
        return new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime());
    }

    /**
     * format the timestamp
     * @param pattern date pattern
     * @param time time stamp
     * @return format string
     * @author songkez
     * @since 2011-11-17
     */
    public static String formatTimestamp(String pattern, Date time) {
        return new SimpleDateFormat(pattern).format(time);
    }

    /**
     * format date with default pattern
     * @param time to be format
     * @return formatted time
     * @author songkez
     * @since 2011-11-27
     */
    public static String formatDateTime(Date time) {
        return new SimpleDateFormat(DFLT_PATTERN).format(time);
    }

    public static String formatDate(Date time) {
        return new SimpleDateFormat(DATE).format(time);
    }

    public static String formatDate(Date time, String pattern) {
        return new SimpleDateFormat(pattern).format(time);
    }

    /**
     * 将日期对象转换为字符串
     * @param time
     * @return
     * @author nerve
     * @since 2013-10-12下午7:51:05
     */
    public static String formatStockDate(Date time) {
        return new SimpleDateFormat(DATE_STOCK).format(time);
    }

    /**
     * get designate date by day
     * @param day number, format
     * @return formatted time
     * @author songkez
     * @since 2012-01-16
     */
    public static String getDesignateDateByDay(int dayNum, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, dayNum);//
        Date date = cal.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * get designate date by month
     * @param month number, format
     * @return formatted time
     * @author songkez
     * @since 2012-01-16
     */
    public static String getDesignateDateByMonth(int monthNum, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthNum);//
        Date date = cal.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }
}
