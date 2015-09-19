/**
 * Project Name: Unicorn Online Shopping Center
 * Confidential and Proprietary                                                                
 * Copyright (C) 2011 By                                                                                     
 * Unicorn Development Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.mongo;

import java.util.List;

import com.mongodb.BasicDBObject;

/**                                                                                                                                    
 * Purpose:  base dao                                                                  
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-6-12 
 */
//数据库CRUD基本操作
public interface BaseDAO {
    public boolean insert(String collectionName, BasicDBObject bean);
     
    public boolean delete(String collectionName, BasicDBObject bean);
     
    public List find(String collectionName, BasicDBObject bean);
     
    public boolean update(String collectionName, BasicDBObject oldBean, BasicDBObject newBean);
     
     
}
