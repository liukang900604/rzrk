/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * Unicorn Development Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.mongo;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

/**                                                                                                                                    
 * Purpose:  base dao implementation                                                                  
 * @version 1.0                                                         
 * @author songkez
 * @since 2015-6-12 
 */
public class BaseDAOImpl implements BaseDAO{
	@Override
    public boolean insert(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getDB();
        db.getCollection(collectionName).insert(bean);
        return false;
    }
 
    @Override
    public boolean delete(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getDB();
        db.getCollection(collectionName).remove(bean);
        return false;
    }
 
    @Override
    public List find(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getDB();
        List list = db.getCollection(collectionName).find(bean).toArray();
        return list ;
    }
 
    @Override
    public boolean update(String collectionName, BasicDBObject oldBean, BasicDBObject newBean) {
        DB db = MongoDBUtil.getDB();
        db.getCollection(collectionName).update(oldBean, newBean);
        return false;
    }
}
