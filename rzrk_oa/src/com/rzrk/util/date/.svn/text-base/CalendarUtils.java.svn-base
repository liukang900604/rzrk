/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Purpose: Calendar Util
 * @version 1.0
 * @author songkez
 * @since 2012-05-21
 */
public class CalendarUtils {

    /** 日期实例 */
    private static Calendar calendar = Calendar.getInstance();

    private static final int MILLI_SECOND = 1000;

    private static final int MINUTE = 60;

    private static final int SECOND = 60;

    private static final int DAY = 24;

    /** 一天的毫秒数 */
    private static final int MILLI_SECOND_FOR_A_DAY = MILLI_SECOND * MINUTE
            * SECOND * DAY;

    private static final int DAYS_FOR_A_MONTH = 30;

    /** flag before */
    public static final transient int BEFORE = 1;

    /** flag after */
    public static final transient int AFTER = 2;

    /** flag equal */
    public static final transient int EQUAL = 3;

    /**
     * 获取当前年份
     * @return int 当前年份
     * @author songkez
     * @since 2012-05-21
     */
    public static int getCurYear() {
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     * @return int 当前月份
     * @author songkez
     * @since 2012-05-21
     */
    public static int getCurMonth() {
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期（day of month）
     * @return int 当前日期
     * @author songkez
     * @since 2012-05-21
     */
    public static int getCurDay() {
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间（24Hours）
     * @return int 时间，24小时制
     * @author songkez
     * @since 2012-05-21
     */
    public static int getCurHour() {
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间（分钟）
     * @return int 当前分钟数
     * @author songkez
     * @since 2012-05-21
     */
    public static int getCurMinute() {
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间（秒）
     * @return int 当前秒数
     * @author songkez
     * @since 2012-05-21
     */
    public static int getCurSecond() {
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 比较设定日期和当前日期
     * @param setedDate 设定日期
     * @param curDate 当前日期
     * @return true:当前日期距设定日期不足或等于一个月, false 当前日期距设定日期大于一个月
     * @author songkez
     * @since 2012-05-21
     */
    public static boolean isLessThanOneMonth(Calendar setedDate,
            Calendar curDate) {

        if (((setedDate.getTimeInMillis() - curDate.getTimeInMillis()) / MILLI_SECOND_FOR_A_DAY) <= DAYS_FOR_A_MONTH) {
            return true;
        }
        return false;
    }

    /**
     * 根据指定格式返回格式化后的日期
     * @param date 要被格式化的日期
     * @param formatedString 格式化字符串
     * @return 格式化后的日期
     * @author songkez
     * @since 2012-05-21
     */
    public static String formatedDate(Date date, String formatedString) {
        String _formatedString = "yyyy/MM/dd";
        if (null == date) {
            return null;
        }
        if (!((null == formatedString) || "".equals(formatedString))) {
            _formatedString = formatedString;
        }
        SimpleDateFormat format = new SimpleDateFormat(_formatedString);
        return format.format(date);
    }

    /**
     * 根据指定格式和地域返回格式化后的日期
     * @param date 要被格式化的日期
     * @param formatedString 格式化字符串
     * @param locale 指定的地域，如果不指定，则使用默认地域
     * @return 格式化后的日期
     * @author songkez
     * @since 2012-05-21
     */
    public static String formatedDate(Date date, String formatedString,
            Locale locale) {
        String _formatedString = "yyyy/MM/dd";
        if (null == date) {
            return null;
        }
        if (!((null == formatedString) || "".equals(formatedString))) {
            _formatedString = formatedString;
        }
        if (null == locale) {
            return formatedDate(date, _formatedString);
        }
        SimpleDateFormat format = new SimpleDateFormat(_formatedString, locale);
        return format.format(date);
    }

    /**
     * 比较两个日期的先后顺序
     * @param first date1
     * @param second date2
     * @return EQUAL - if equal BEFORE - if before than date2 AFTER - if over
     *         than date2
     * @author songkez
     * @since 2012-05-21
     */
    public static int compareTwoDate(Date first, Date second) {
        if ((first == null) && (second == null)) {
            return EQUAL;
        } else if (first == null) {
            return BEFORE;
        } else if (second == null) {
            return AFTER;
        } else if (first.before(second)) {
            return BEFORE;
        } else if (first.after(second)) {
            return AFTER;
        } else {
            return EQUAL;
        }
    }

    /**
     * 比较日期是否介于两者之间
     * @param date the specified date
     * @param begin date1
     * @param end date2
     * @return true - between date1 and date2 false - not between date1 and
     *         date2
     * @author songkez
     * @since 2012-05-21
     */
    public static boolean isDateBetween(Date date, Date begin, Date end) {
        int c1 = compareTwoDate(begin, date);
        int c2 = compareTwoDate(date, end);

        return (((c1 == BEFORE) && (c2 == BEFORE)) || (c1 == EQUAL) || (c2 == EQUAL));
    }

    /**
     * get year of specified calendar
     * @param calendar
     * @return int year
     * @author songkez
     * @since 2012-05-21
     */
    public static int getYearBySpecifiedCalendar(Date Date) {
        calendar.setTime(Date);
        return calendar.get(Calendar.YEAR);
    }

}
