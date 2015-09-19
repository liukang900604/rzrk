package com.rzrk.service;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rzrk.entity.Admin;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductRecord;

/**
 * 产品处理记录
 * 请勿直接使用，请在ProductRecordEngine中loop线程使用
 * @version $Id$
 * @author nerve
 * @since 2013-9-25 下午1:33:09
 */
public interface ProductRecordService extends BaseService<ProductRecord, String> {
	
	public void disposeAll();
	public void dispose(String productId,List<String> emailContentList);
	
	
}