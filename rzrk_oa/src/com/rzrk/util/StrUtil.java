/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;



/**
 * @author kang.liu
 */
public class StrUtil {
    
    /**
     * 验证字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){ 
    	   Pattern pattern = Pattern.compile("^-?(([0-9]+)|([0-9]+\\.[0-9]+))$"); 
    	   Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return false; 
    	   } 
    	   return true; 
    	}
    
    
  /**
   *  把数组或者容器转换成为字符串形式 用逗号隔开 
   * @param object
   * @return
   */
    public static String toString(Object object){  
        if(object == null){  
            return null ;  
        }  
        String returnString = "" ;  
        if(object.getClass().isArray()){  
            int length = Array.getLength( object ) ;  
            for(int x=0;x<length;x++){  
                if(x != 0){  
                    returnString += "," ;  
                }  
                Object value = Array.get(object, x) ;  
                if(value instanceof Number){  
                    returnString += value ;  
                }else{  
                    returnString += "'" + value + "'" ;   
                }  
            }  
        }else{  
            Collection<?> collection = (Collection<?>) object ;  
            Iterator<?> iterator = collection.iterator() ;  
            int x=0;  
            while(iterator.hasNext()){  
                Object value = iterator.next() ;  
                if(x != 0){  
                    returnString += "," ;  
                }  
                x++;  
                if(value instanceof Number){  
                    returnString += value ;  
                }else{  
                    returnString += "'" + value + "'" ;   
                }  
            }  
        }  
        return returnString ;  
    }  
    
    /**
     * 判断同类型对象是否相等
     * @param obj  比较对象
     * @param temp 目标对象 
     * @return
     */
   public static boolean compare(Object obj,Object temp){
	  
	   /**TODO
	   * 暂时不支持对象比较，只支持字符串和基本类型、枚举类型
	   */
	   if(StringUtils.isEmpty(obj.toString()) && StringUtils.isEmpty(temp.toString()) ){
		   return true;
	   }
	  return ObjectUtils.equals(obj, temp);
	
   }

}
