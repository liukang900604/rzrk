package com.rzrk.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rzrk.bean.Pager;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.BaseDao;
import com.rzrk.entity.BaseEntity;
import com.rzrk.util.ReflectionUtil;
import com.rzrk.util.RzrkLogger;

/**
 * Dao实现类 - 基类
 */

public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

    protected static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
    protected static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
    protected static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";// "修改日期"属性名称

    protected Class<T> entityClass;
    protected SessionFactory sessionFactory;

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Assert.notNull(id, "id is required");
        return (T) getSession().get(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T load(PK id) {
        Assert.notNull(id, "id is required");
        return (T) getSession().load(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAllList() {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityClass);
        String hql;
        if (ArrayUtils.contains(classMetadata.getPropertyNames(), ORDER_LIST_PROPERTY_NAME)) {
            hql = "from " + entityClass.getName() + " as entity order by entity." + ORDER_LIST_PROPERTY_NAME + " desc";
        } else {
            hql = "from " + entityClass.getName();
        }
        return getSession().createQuery(hql).list();
    }

	@Override
	public List<T> find(DetachedCriteria detachedCriteria) {
    	Criteria criteria =  detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

	@Override
	public T unique(DetachedCriteria detachedCriteria) {
    	Criteria criteria =  detachedCriteria.getExecutableCriteria(getSession());
    	try {
			return (T) criteria.uniqueResult();
		} catch (HibernateException e) {
			try {
				return (T) criteria.list().get(0);
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
		}
    	return null;
	}


    @Override
    public Long getTotalCount() {
        String hql = "select count(*) from " + entityClass.getName();
        return (Long) getSession().createQuery(hql).uniqueResult();
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public PK save(T entity) {
        Assert.notNull(entity, "entity is required");
        if (entity instanceof BaseEntity) {
            try {
                Method method = entity.getClass().getMethod(BaseEntity.ON_SAVE_METHOD_NAME);
                method.invoke(entity);
                return (PK) getSession().save(entity);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return (PK) getSession().save(entity);
        }
    }

    @Override
    public void update(T entity) {
        Assert.notNull(entity, "entity is required");
        if (entity instanceof BaseEntity) {
            try {
                Method method = entity.getClass().getMethod(BaseEntity.ON_UPDATE_METHOD_NAME);
                method.invoke(entity);
                getSession().update(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getSession().update(entity);
        }
    }
    
    @Override
    public void saveOrUpdate(T entity) {
        Assert.notNull(entity, "entity is required");
        if (entity instanceof BaseEntity) {
            try {
                Method method = entity.getClass().getMethod(BaseEntity.ON_UPDATE_METHOD_NAME);
                method.invoke(entity);
                getSession().saveOrUpdate(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getSession().saveOrUpdate(entity);
        }
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "entity is required");
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(PK id) {
        Assert.notNull(id, "id is required");
        T entity = (T) getSession().load(entityClass, id);
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(PK[] ids) {
        Assert.notEmpty(ids, "ids must not be empty");
        for (PK id : ids) {
            T entity = (T) getSession().load(entityClass, id);
            getSession().delete(entity);
        }
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void evict(Object object) {
        Assert.notNull(object, "object is required");
        getSession().evict(object);
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    @Override
    public Pager findPager(Pager pager) {
        Criteria criteria = getSession().createCriteria(entityClass);
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(Pager pager, Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion criterion : criterions) {
            if (criterion != null) {
                criteria.add(criterion);
            }
        }
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(Pager pager, Order... orders) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(Pager pager, DetachedCriteria detachedCriteria) {
    	Criteria criteria =  detachedCriteria.getExecutableCriteria(getSession());
        return findPager(pager, criteria);
    }

    @Override
    public Pager findPager(Pager pager, Criteria criteria) {
        Assert.notNull(pager, "pager is required");
        Assert.notNull(criteria, "criteria is required");

        try {
            Integer pageNumber = pager.getPageNumber();
            Integer pageSize = pager.getPageSize();
            String searchBy = pager.getSearchBy();
            String keyword = pager.getKeyword();
            String orderBy = pager.getOrderBy();
            String isCunxu = pager.getIsCunxu();
            Map<String,Object> numberParam = pager.getNumberParam();
            Map<String,Object> param = pager.getParam();
            String beginDate = pager.getBeginDate();//结束日期
            String endDate = pager.getEndDate();//开始日期
            String dateType = pager.getDateType();//日期字段
            String adminId = pager.getAdminId();//当前人
            String currentId = pager.getCurrentId();//处理人
            String [] flowList = pager.getFlowList();//流程集合
            String flowType = pager.getFlowType();//流程类型
            //Vector<String> workArray = pager.getWorkArray();//流程中的工作
            Pager.Order order = pager.getOrder();

            if (StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(keyword)) {
                if (searchBy.contains(".")) {
                    String alias = StringUtils.substringBefore(searchBy, ".");
                    criteria.createAlias(alias, alias);
                }

                if (searchBy.equals(CREATE_DATE_PROPERTY_NAME)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date minDate = formatter.parse(keyword + " 00:00:00");
                    Date maxDate = formatter.parse(keyword + " 23:59:59");
                    RzrkLogger.debug("keyword is " + keyword);
                    criteria.add(Restrictions.gt(searchBy, minDate));
                    criteria.add(Restrictions.lt(searchBy, maxDate));
                }else {
                    criteria.add(Restrictions.like(searchBy, "%" + keyword + "%"));
                   
                }

            }
            
            //日期类型
            if(StringUtils.isNotEmpty(dateType)){
            	//if(dateType.equals(CREATE_DATE_PROPERTY_NAME) || dateType.equals(MODIFY_DATE_PROPERTY_NAME)){//创建日期、修改日期
            		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		 if(StringUtils.isNotEmpty(beginDate)){//开始日期
            			 Date minDate = formatter.parse(beginDate + " 00:00:00");
            			  criteria.add(Restrictions.gt(dateType, minDate));
            		 }
            		 if(StringUtils.isNotEmpty(endDate)){//开始日期
            			 Date maxDate = formatter.parse(endDate + " 23:59:59");
            			 criteria.add(Restrictions.lt(dateType, maxDate));
            		 }
            	//}
            }
            
          /*  if (StringUtils.isNotEmpty(adminId)){
            	//criteria.add(Restrictions.or(Restrictions.eq("admin.id",adminId), Restrictions.like("currentId","%" +currentId+ "%")));
            	 if(workArray != null && workArray.size() > 0){
            	  criteria.add(Restrictions.or(Restrictions.eq("admin.id",adminId), Restrictions.in("id",workArray )));
            	 }
            }else{
            	//当前流程中的用户
                if(workArray != null && workArray.size() > 0){
                	criteria.add(Restrictions.in("id",workArray ));
                }
            }*/
            
            if(StringUtils.isNotEmpty(flowType)){
            	if(WorkFlowContants.NORMAL_FLOW.equals(flowType)){//正常流程
            		criteria.add(Restrictions.not(Restrictions.in("flowId", flowList)));
            	}else if(WorkFlowContants.INSIDE_FLOW.equals(flowType)){//内置流程
            		criteria.add(Restrictions.in("flowId", flowList));
            	}
            }
            
            if (StringUtils.isNotEmpty(adminId) && StringUtils.isNotEmpty(currentId)){
            	criteria.add(Restrictions.or(Restrictions.eq("admin.id",adminId), Restrictions.like("currentId","%" +currentId+ "%")));
            }
            
            
            if (StringUtils.isNotEmpty(pager.getUserPlanAdminId())){
            	//满足处理人 Or 测试人
            	criteria.add(Restrictions.or(Restrictions.eq("admin.id", pager.getUserPlanAdminId()), Restrictions.eq("testPerson.id", pager.getUserPlanAdminId())));
//        	    criteria.add(Restrictions.eq("admin.id",pager.getUserPlanAdminId()));
            }
            
            String hasNoRoot = pager.getHasNoRoot();
    	    if (StringUtils.isNotEmpty(hasNoRoot) && hasNoRoot.equals("0")){
    	    	criteria.add(Restrictions.isNotNull("rootContractCategory.id"));
    	    }else if(StringUtils.isNotEmpty(hasNoRoot) && hasNoRoot.equals("1")){
    	    	criteria.add(Restrictions.isNull("rootContractCategory.id"));
    	    }
            
            
            if (StringUtils.isNotEmpty(isCunxu)) {
                    criteria.add(Restrictions.like(isCunxu, "%" + keyword + "%"));
            }
            
            
            //查询组  非模糊查询
            for(String obj : numberParam.keySet()){
            	 criteria.add(Restrictions.eq(obj,numberParam.get(obj)));
            }
            //查询组  模糊查询
            for(String obj : param.keySet()){
            	 criteria.add(Restrictions.like(obj,"%" +param.get(obj)+"%"));
            }
            

            pager.setTotalCount(criteriaResultTotalCount(criteria));

            if (StringUtils.isNotEmpty(orderBy) && order != null) {
            	 if(StringUtils.equals(orderBy, "planuser")){
                 	criteria.createAlias("admin", "a");
                 	if(order == Pager.Order.asc){
                 		criteria.addOrder(Order.asc("a.name"));
                 	}else{
                 		criteria.addOrder(Order.desc("a.name"));
                 	}
                 }else if (order == Pager.Order.asc) {
                     criteria.addOrder(Order.asc(orderBy));
                 }else {
                     criteria.addOrder(Order.desc(orderBy));
                 }
            }

            ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityClass);
            if (!StringUtils.equals(orderBy, ORDER_LIST_PROPERTY_NAME) && ArrayUtils.contains(classMetadata.getPropertyNames(), ORDER_LIST_PROPERTY_NAME)) {
                criteria.addOrder(Order.asc(ORDER_LIST_PROPERTY_NAME));
                criteria.addOrder(Order.desc(CREATE_DATE_PROPERTY_NAME));
                if (StringUtils.isEmpty(orderBy) || order == null) {
                    pager.setOrderBy(ORDER_LIST_PROPERTY_NAME);
                    pager.setOrder(Pager.Order.asc);
                }
            } else if (!StringUtils.equals(orderBy, CREATE_DATE_PROPERTY_NAME) && ArrayUtils.contains(classMetadata.getPropertyNames(), CREATE_DATE_PROPERTY_NAME)) {
                criteria.addOrder(Order.desc(CREATE_DATE_PROPERTY_NAME));
                if (StringUtils.isEmpty(orderBy) || order == null) {
                    pager.setOrderBy(CREATE_DATE_PROPERTY_NAME);
                    pager.setOrder(Pager.Order.desc);
                }
            }

            if(StringUtils.isEmpty(pager.getShowAll())){
            	criteria.setFirstResult((pageNumber - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            

            RzrkLogger.debug("criteria.list() " + criteria.list());
            pager.setResult(criteria.list());
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 获取Criteria查询数量
    @SuppressWarnings("unchecked")
    protected int criteriaResultTotalCount(Criteria criteria) {
        Assert.notNull(criteria, "criteria is required");

        int criteriaResultTotalCount = 0;
        try {
            CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

            Projection projection = criteriaImpl.getProjection();
            ResultTransformer resultTransformer = criteriaImpl.getResultTransformer();
            List<OrderEntry> orderEntries = (List<OrderEntry>) ReflectionUtil.getFieldValue(criteriaImpl, "orderEntries");
            ReflectionUtil.setFieldValue(criteriaImpl, "orderEntries", new ArrayList());

            Integer totalCount = ((Long) criteriaImpl.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            if (totalCount != null) {
                criteriaResultTotalCount = totalCount;
            }

            criteriaImpl.setProjection(projection);
            if (projection == null) {
                criteriaImpl.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
            }
            if (resultTransformer != null) {
                criteriaImpl.setResultTransformer(resultTransformer);
            }
            ReflectionUtil.setFieldValue(criteriaImpl, "orderEntries", orderEntries);
        } catch (Exception e) {

        }
        return criteriaResultTotalCount;
    }

	@Override
	public void merge(T entity) {
		 Assert.notNull(entity, "entity is required");
		this.getSession().merge(entity);
	}

	/*  @see com.rzrk.dao.BaseDao#deleteAll()  */
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}