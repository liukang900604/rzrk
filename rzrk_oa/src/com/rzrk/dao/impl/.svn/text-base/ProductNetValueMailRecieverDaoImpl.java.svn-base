package com.rzrk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rzrk.dao.ProductNetValueMailRecieverDao;
import com.rzrk.entity.ProductDailyRecord;
import com.rzrk.entity.ProductNetValueMailReciever;


@Repository("productNetValueMailRecieverDaoImpl")
public class ProductNetValueMailRecieverDaoImpl extends BaseDaoImpl<ProductNetValueMailReciever,String> implements ProductNetValueMailRecieverDao{

	@Override
	public List<ProductNetValueMailReciever> getProductNetValueMailRecieverListByProductId(
			String productId) {
		String hql = "from ProductNetValueMailReciever as pdr where pdr.productId = '"+ productId +"'";
        List<ProductNetValueMailReciever> resultList = getSession().createQuery(hql.toString()).list();
        return (resultList == null || resultList.isEmpty()) ? null : resultList;
	}

}
