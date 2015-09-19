package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.ProductDao;
import com.rzrk.dao.ProductRecordDao;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductRecord;

/**
 * Dao实现类 - 产品
 */

@Repository("productRecordDaoImpl")
public class ProductRecordDaoImpl extends BaseDaoImpl<ProductRecord, String> implements ProductRecordDao {
}