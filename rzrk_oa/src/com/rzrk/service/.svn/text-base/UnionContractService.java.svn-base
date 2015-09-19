/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Product;
import com.rzrk.vo.contract.Field;
import com.rzrk.vo.querytree.Node;


/**
 * Service接口 - 文章分类
 */

public interface UnionContractService extends BaseService<ContractField, String> {
	public Pager findPager(Pager pager, String contractCategoryId,Node node );
	public Pager findPager(Pager pager, String contractCategoryId,Node node ,Map<String,Object> rowCondMap);
}