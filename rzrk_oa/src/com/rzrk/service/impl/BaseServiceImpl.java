/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.io.Serializable;
import java.util.List;



import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.bean.Pager;
import com.rzrk.dao.BaseDao;
import com.rzrk.service.BaseService;

/**
 * Service实现类 - Service实现类基类
 */

@Transactional
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Transactional(readOnly = true)
	public T get(PK id) {
		return baseDao.get(id);
	}

	@Transactional(readOnly = true)
	public T load(PK id) {
		return baseDao.load(id);
	}

	@Transactional(readOnly = true)
	public List<T> getAllList() {
		return baseDao.getAllList();
	}
	@Transactional(readOnly = true)
    public List<T> find(DetachedCriteria detachedCriteria){
		return baseDao.find(detachedCriteria);
	}
	@Transactional(readOnly = true)
    public T unique(DetachedCriteria detachedCriteria){
		return baseDao.unique(detachedCriteria);
	}
	@Transactional(readOnly = true)
	public Long getTotalCount() {
		return baseDao.getTotalCount();
	}

	@Transactional
	public PK save(T entity) {
		return baseDao.save(entity);
	}

	@Transactional
	public void update(T entity) {
		baseDao.update(entity);
	}
	
	@Transactional
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Transactional
	public void delete(PK id) {
		baseDao.delete(id);
	}

	@Transactional
	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}

	@Transactional(readOnly = true)
	public void flush() {
		baseDao.flush();
	}

	@Transactional(readOnly = true)
	public void evict(Object object) {
		baseDao.evict(object);
	}
	
	@Transactional(readOnly = true)
	public void clear() {
		baseDao.clear();
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager) {
		return baseDao.findPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Criterion... criterions) {
		return baseDao.findPager(pager, criterions);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Order... orders) {
		return baseDao.findPager(pager, orders);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager,DetachedCriteria detachedCriteria) {
		return baseDao.findPager(pager, detachedCriteria);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Criteria criteria) {
		return baseDao.findPager(pager, criteria);
	}

	@Transactional()
	public void merge(T entity) {
		baseDao.merge(entity);
		
	}
	
	@Transactional()
	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
		
	}

	/*  @see com.rzrk.service.BaseService#deleteAll()  */
	@Transactional()
	public void deleteAll() {
		baseDao.deleteAll();
		
	}

}