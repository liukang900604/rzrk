package com.rzrk.dao;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.rzrk.entity.Product;

/**
 * Dao接口-产品
 * @version $Id$
 * @author nerve
 * @since 2013-9-25 下午1:36:24
 */
public interface ProductDao extends BaseDao<Product, String> {

    public List<Product> findByConditions(ListOrderedMap params);
    
    public List<Product> findByProductCategories(ListOrderedMap params);
    
    public List<Product> findByProductCategoriess(ListOrderedMap params);
    
    public List<Product> findByProductAggressive(ListOrderedMap params);
    
    public List<Product> findByProductAggressives(ListOrderedMap params);
    
    public List<Product> findProductName();
    
    //灵活型
    public List<Product> findByProductFlexible(ListOrderedMap params);

	public Object findByProductHistoryName(String value);

	public Product getProductByXuntouName(String trim);

	public int getProductByIsValid(int i);
	public Product getProductByAnyName(String value);
	public Product getProductByFullName(String value);
	public List<Product> getProductListByAnyName(String value);
}