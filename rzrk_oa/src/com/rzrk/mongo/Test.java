/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.mongo;

import java.util.List;

import com.mongodb.BasicDBObject;

/**                                                                                                                                    
 * Purpose:  test mongo                                                                   
 * @version 1.0                                                  
 * @author songkez
 * @since 2015-6-12 
 */
public class Test {

	public static void main(String args[]){
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        BasicDBObject beanOne = new BasicDBObject();
        beanOne.put("name", "kakakaka");
        beanOne.put("sex", "男");
        beanOne.put("age", 20);
        beanOne.put("成绩", 100);
//        baseDAOImpl.insert("test", beanOne);
        List<BasicDBObject> list = baseDAOImpl.find("test", beanOne);
        System.out.println(list);
        System.out.println(list.get(0).keySet());
	}
}
