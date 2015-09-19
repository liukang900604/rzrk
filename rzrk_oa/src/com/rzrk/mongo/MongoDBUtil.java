/**
 * Project Name: Unicorn Online Shopping Center
 * Confidential and Proprietary                                                                
 * Copyright (C) 2011 By                                                                                     
 * Unicorn Development Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

/**                                                                                                                                    
 * Purpose: mongo db util                                                                   
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-6-12 
 */
public class MongoDBUtil {
	private static Mongo mongo = null;
    
    private static String DBString = "test";//数据库名
    private static String hostName = "219.232.254.50";//主机名
    private static int port = 27017;//端口号
    private static int poolSize = 10;//连接池大小
     
    private MongoDBUtil(){
         
    }
     
    //获取数据库连接
    public static DB getDB(){
        if(mongo == null){
            init();
        }
         
        return mongo.getDB(DBString);
    }
     
     
    //初始化数据库
    private static void init(){
        try {
            //实例化Mongo
            mongo = new Mongo(hostName, port);
            MongoOptions opt = mongo.getMongoOptions();
            //设置连接池大小
            opt.connectionsPerHost = poolSize;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
