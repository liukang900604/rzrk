package com.rzrk.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.rzrk.bean.Pager;

/**
 * Dao接口 - Dao基接口
 */

public interface BaseDao<T, PK extends Serializable> {

    /**
     * 获取Session
     */
    public Session getSession();

    /**
     * 根据ID获取实体对象
     * @param id 记录ID
     * @return 实体对象
     */
    public T get(PK id);

    /**
     * 根据ID获取实体对象
     * @param id 记录ID
     * @return 实体对象
     */
    public T load(PK id);

    /**
     * 获取所有实体对象集合
     * @return 实体对象集合
     */
    public List<T> getAllList();
    /**
     * 条件查找集合
     * @param detachedCriteria
     * @return
     */
    public List<T> find(DetachedCriteria detachedCriteria);
    /**
     * 条件查找唯一
     * @param detachedCriteria
     * @return
     */
    public T unique(DetachedCriteria detachedCriteria);

    /**
     * 获取所有实体对象总数
     * @return 实体对象总数
     */
    public Long getTotalCount();

    /**
     * 保存实体对象
     * @param entity 对象
     * @return ID
     */
    public PK save(T entity);
    
    /**
     * 保存实体对象
     * @param entity 对象
     * @return ID
     */
    public void saveOrUpdate(T entity);

    /**
     * 更新实体对象
     * @param entity 对象
     */
    public void update(T entity);

    /**
     * 删除实体对象
     * @param entity 对象
     * @return
     */
    public void delete(T entity);

    /**
     * 根据ID删除实体对象
     * @param id 记录ID
     */
    public void delete(PK id);

    /**
     * 根据ID数组删除实体对象
     * @param ids ID数组
     */
    public void delete(PK[] ids);

    /**
     * 刷新session
     */
    public void flush();

    /**
     * 清除对象
     * @param object 需要清除的对象
     */
    public void evict(Object object);

    /**
     * 清除Session
     */
    public void clear();

    /**
     * 根据Pager进行查询(提供分页、查找、排序功能)
     * @param pager Pager对象
     * @return Pager对象
     */
    public Pager findPager(Pager pager);

    /**
     * 根据Pager、Criterion进行查询(提供分页、查找、排序功能)
     * @param pager Pager对象
     * @param criterions Criterion数组
     * @return Pager对象
     */
    public Pager findPager(Pager pager, Criterion... criterions);

    /**
     * 根据Pager、Criterion进行查询(提供分页、查找、排序功能)
     * @param pager Pager对象
     * @param orders Order数组
     * @return Pager对象
     */
    public Pager findPager(Pager pager, Order... orders);

    /**
     * 根据Pager、Criteria进行查询(提供分页、查找、排序功能)
     * @param pager Pager对象
     * @param criteria Criteria对象
     * @return Pager对象
     */
    public Pager findPager(Pager pager, Criteria criteria);
    
    /**
     * 根据Pager、DetachedCriteria进行查询(提供分页、查找、排序功能)
     * @param pager Pager对象
     * @param DetachedCriteria DetachedCriteria对象
     * @return Pager对象
     */
    public Pager findPager(Pager pager, DetachedCriteria criteria);
    
    /**
     * 新增或者保存对象
     * @param entity
     */
    public void merge(T entity);

	/**
	 * delete all
	 * @author songkez
	 * @since  2015-7-15 
	 */
	public void deleteAll();
}