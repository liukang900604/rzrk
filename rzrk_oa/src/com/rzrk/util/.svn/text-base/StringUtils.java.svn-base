/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.rzrk.vo.contract.Field;

/**
 * Purpose: String hand util
 * @version 1.0
 * @author songkez
 * @since 2011-8-3
 */
public class StringUtils {
    /** empty string constant */
    public static final String EMPTY_STRING = "";

    /**
     * trim string
     * @param strings to be trim
     * @return trimed string
     * @author songkez
     * @since 2011-9-30
     */
    public static String[] trim(String[] strings) {
        if (strings == null) {
            return null;
        }
        for (int i = 0, len = strings.length; i < len; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;
    }

    /**
     * get sub of string
     * @param str source string
     * @param begin begin position
     * @param end end position
     * @return sub string
     * @author songkez
     * @since 2011-9-30
     */
    public static String substring(String str, int begin, int end) {
        if (begin == end) {
            return EMPTY_STRING;
        }
        return str.substring(begin, end);
    }

    /**
     * split string
     * @param str target string
     * @param separator separator char
     * @return splited string
     * @author songkez
     * @since 2011-9-30
     */
    public static String[] splitShortString(String str, char separator) {
        int len = str.length();
        if ((str == null) || (len == 0)) {
            return new String[0];
        }
        int lastTokenIndex = 0;

        for (int pos = str.indexOf(separator); pos >= 0; pos = str.indexOf(
                separator, pos + 1)) {
            lastTokenIndex++;
        }

        String[] list = new String[lastTokenIndex + 1];

        int oldPos = 0;
        int i = 0;
        for (int pos = str.indexOf(separator); pos >= 0; pos = str.indexOf(
                separator, oldPos = pos + 1)) {
            list[i++] = substring(str, oldPos, pos);
        }

        list[lastTokenIndex] = substring(str, oldPos, len);

        return list;
    }

    /**
     * 判断字符串是否为空 is empty
     * @param str source string
     * @return 是否为空 if empty
     * @author songkez
     * @since 2012-05-21
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * 生成指定长度的随机字符串 random string
     * @param length 长度 string length
     * @return 随机字符串 random string
     * @author songkez
     * @since 2012-05-21
     */
    public static String makeRandomString(int length) {
        // a-zA-Z0-9
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = rand.nextInt(62);// 0-61之间的随机数字,不包含62,左闭右开
            sbd.append(str.charAt(number));// 取number下标那个字符
        }
        return sbd.toString();
    }
    
    
    /**
     * 生成指定长度的随机字符串 random string
     * @param length 长度 string length
     * @return 随机字符串 random string
     * @author songkez
     * @since 2012-05-21
     */
    public static String makeRandomNumber(int length) {
        // a-zA-Z0-9
        String str = "0123456789";
        Random rand = new Random();
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = rand.nextInt(9);// 0-9之间的随机数字,不包含9,左闭右开
            sbd.append(str.charAt(number));// 取number下标那个字符
        }
        return sbd.toString();
    }

    /**
     * get sub-string
     * @param str to be sub
     * @param begin begin position
     * @param len sub length
     * @return sub string
     * @author songkez
     * @since 2011-10-12
     */
    public static String substr(String str, int begin, int len) {
        if (0 == len) {
            return EMPTY_STRING;
        }
        return str.substring(begin, begin + len);
    }

    /**
     * random hex array
     * @return hex array
     * @author songkez
     * @since 2011-10-12
     */
    public static String randomHex() {

        Set<String> set = new HashSet<String>();
        Random ran = new Random();

        int test;
        String hex = EMPTY_STRING;
        StringBuilder sbd = new StringBuilder();

        while (set.size() < 16) {
            test = ran.nextInt(16);
            hex = Integer.toHexString(test);
            if (!set.contains(hex)) {
                set.add(hex);
            } else {
                continue;
            }
            sbd.append(hex);

        }

        return sbd.toString();
    }

    /**
     * get random number
     * @param length
     * @return random number
     * @author songkez
     * @since 2011-12-15
     */
    public static String randomNumnber(int length) {
        // 0-9
        String str = "0123456789";
        Random rand = new Random();
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = rand.nextInt(9);// 0-61之间的随机数字,不包含62,左闭右开
            sbd.append(str.charAt(number));// 取number下标那个字符
        }
        return sbd.toString();
    }

    /**
     * join the strings
     * @param str string array
     * @return joined string
     * @author songkez
     * @since 2011-12-27
     */
    public static String join(Collection<String> strList,String split,String quote) {
    	boolean first = true;
        StringBuilder sbu = new StringBuilder();
        for (String tmp : strList) {
        	if(first){
        		first=false;
        	}else{
        		sbu.append(split);
        	}
        	sbu.append(quote).append(tmp).append(quote);
        }
        return sbu.toString();
    }
    /**
     * join the strings
     * @param str string array
     * @return joined string
     * @author songkez
     * @since 2011-12-27
     */
    public static String join(String... str) {
        StringBuilder sbu = new StringBuilder();
        for (String tmp : str) {
            sbu.append(tmp);
        }
        return sbu.toString();
    }

    /**
     * reverse the given string
     * @param str to be reverse
     * @return the reversed string
     * @author songkez
     * @since 2012-2-5
     */
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * main method
     * @param args method arguments
     * @author songkez
     * @since 2011-10-12
     */
    public static void main(String[] args) {
        // String test = "This is a test.";
        // StringBuffer sbf = new StringBuffer();
        // char[] crs = test.toCharArray();
        // for (int i = test.length() - 1; i > -1; i--) {
        // String.valueOf(crs[i]);
        // System.out.println(String.valueOf(crs[i]));
        // sbf.append(crs[i]);
        //
        // }
        // System.out.println(sbf.toString());
        // System.out.println(reverse(test));
    }
    
    /**
     * generate alpha random words
     * @param length
     * @return words
     * @author songkez
     * @since  2012-5-21 
     */
    public static String generateAlpaWords(int length){
        // a-zA-Z0-9
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = rand.nextInt(52);// 0-61之间的随机数字,不包含62,左闭右开
            sbd.append(str.charAt(number));// 取number下标那个字符
        }
        return sbd.toString();
    }
    /**
     * get excel cell string
     * @param cell
     * @return
     */
    private static DecimalFormat decimalFormat = new DecimalFormat("#.######");
    public static String parseExcel(Cell cell) {
		String result = "";
    	if(cell==null)return result;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
						.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil
						.getJavaDate(value);
				result = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				if(value%1 == value){	 
					result = String.valueOf(value);
				}else{
					result = String.valueOf(decimalFormat.format(value));
				}
/*				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
*/			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// String类型
			result = cell.getRichStringCellValue().toString().trim();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
		default:
			result = "";
			break;
		}
		return result;
	}
   
	   public static String camelToUnderline(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (Character.isUpperCase(c)){  
	               sb.append('_');  
	               sb.append(Character.toLowerCase(c));  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString();  
	   }  
	   public static String underlineToCamel(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (c=='_'){  
	              if (++i<len){  
	                  sb.append(Character.toUpperCase(param.charAt(i)));  
	              }  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString();  
	   }
	   
	   public static String[] fromCollection(final Collection<?> collection){
		   String[] arr = new String[collection.size()];
		   int i=0;
			for(Object elem : collection){
				if(elem==null){
					arr[i]=null;
				}else{
					arr[i] = elem.toString();
				}
				i++;
			}
			return arr;
	   }
	   

}
