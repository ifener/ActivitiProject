package com.wey.framework.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.hql.spi.FilterTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.wey.framework.dao.GenericDao;
import com.wey.framework.model.BaseObject;
import com.wey.framework.util.Context;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

public class GenericDaoImpl<T,PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {

	protected Logger logger = LogManager.getLogger(this.getClass());
	private Class<T> persistentClass;
	
	public GenericDaoImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}
	
	
	@Override
	public List<T> getAll() {
		return super.getHibernateTemplate().loadAll(persistentClass);
	}

	@Override
	public List<T> getAllByIDs(@SuppressWarnings("unchecked") PK... paramArgs) {
		List<T> result = new ArrayList<T>();
		if(null!=paramArgs && paramArgs.length>0){
			for(PK id:paramArgs){
				T entity = this.get(id);
				if(null!=entity){
					result.add(entity);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		return (T)this.get(this.persistentClass, id);
	}
	
	public Object get(Class clazz,Serializable id){
		Object entity = super.getHibernateTemplate().get(clazz, id);
		if(null == entity) {
			logger.warn("'"+clazz+"' object with id "+id+" not found!");
		}
		return entity;
	}

	@Override
	public void initialize(Object... paramArgs) {
	   if(null!=paramArgs && paramArgs.length>0) {
		   for(Object o:paramArgs) {
			   if(null!=o){
				   if(Hibernate.isInitialized(o)) {
					   Hibernate.initialize(o);
				   }
			   }
		   }
	   }
		
	}

	@Override
	public boolean exists(PK id) {
		return this.get(id) != null;
	}

	@Override
	public T save(T paramT) {
		T t = null;
		boolean baseObjectFlag = BaseObject.class.isAssignableFrom(paramT.getClass());
		if(baseObjectFlag && null!=paramT){
			BaseObject baseObject = (BaseObject)paramT;
			saveBaseObject(baseObject);
			t = super.getHibernateTemplate().merge(paramT);
		}
		return t;
	}
	
	private void saveBaseObject(BaseObject baseObject){
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Context context = ContextUtil.getContext();
		Long userId = null;
		if(null!=context && null!=context.getUser()){
			userId = context.getUser().getId();
		}
		
		if(null==baseObject.getId()){
			if(userId!=null && baseObject.getCreatedBy()==null){
				baseObject.setCreatedBy(userId);
			}
			
			if(baseObject.getCreatedDate() == null){
				baseObject.setCreatedDate(currentTime);
			}
		}
		
		if(userId!=null){
			baseObject.setUpdatedBy(userId);
		}
		baseObject.setUpdatedDate(currentTime);
		
	}

	@Override
	public void remove(@SuppressWarnings("unchecked") PK... ids) {
		if(null == ids || ids.length==0){
			return;
		}
		for(int i=0,j=ids.length;i<j;i++){
			if(null!=ids[i]) {
				T t = get(ids[i]);
				super.getHibernateTemplate().delete(t);
				
				if(i%20 ==0){
					super.getHibernateTemplate().flush();
					super.getHibernateTemplate().clear();
				}
			}
		}
	}

	@Override
	public void removeByBulk(@SuppressWarnings("unchecked") PK... paramArgs) {
		if(null == paramArgs || paramArgs.length == 0){
			return;
		}
		
		super.getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				String hql="delete "+GenericDaoImpl.this.persistentClass.getName()+" where id in (:ids)";
				int result = session.createQuery(hql).setParameterList("ids", paramArgs).executeUpdate();
				return Integer.valueOf(result);
			}
			
		});
	}
	
	protected List find(String hql){
		return find(hql,-1,-1,null);
	}
	
	protected List find(String hql,Object...values){
		return find(hql,-1,-1,values);
	}
	
	@SuppressWarnings("rawtypes")
	protected List find(String hql,final int offset,final int rowCount,final Object...values){
		List returnList = null;
		returnList = super.getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				if(null!=values){
					for(int i=0,j=values.length;i<j;i++){
						query.setParameter(i, values[i]);
					}
				}
				
				if(offset>0){
					query.setFirstResult(offset);
				}
				
				if(rowCount>0){
					query.setMaxResults(rowCount);
				}
				return query.list();
			}
			
		});
		return returnList;
	}
	
	protected Pagination findPage(String hql,int pageIndex,int pageSize,Object...values){
		Pagination page = new Pagination(pageIndex, pageSize);
		
		findPage(hql,page,values);
		return page;
	}
	
	protected void findPage(String hql,Pagination page,Object...values){
		if(null==page){
			page = new Pagination();
		}
		
		if(page.getPageIndex()<=0){
			page.setPageIndex(Pagination.DEFAULT_PAGE_INDEX);
			page.setPageSize(Pagination.DEFAULT_PAGE_SIZE);
		}
		
		int offset = -1;
		if(page.isEnabledFlag() && page.getPageIndex()>0 && page.getPageSize()>0){
			String countHql = "select count(*) "+removeSelect(hql);
			page.setRowTotal(getLong(countHql,values).intValue());
			offset = page.calculateOffset();
		}
		
		page.setDatas(find(hql,offset,page.getPageSize(),values));
	}
	
	protected static String removeSelect(String hql){
		int beginIndex = hql.toLowerCase().indexOf(" from ");
		return hql.substring(beginIndex);
	}
	
	protected Long getLong(final String hql,final Object...values){
		Long resultLong = null;
		Object object = getObject(hql,values);
		if(null != object && object instanceof Long){
			resultLong = (Long)object;
		}
		if(resultLong==null){
			resultLong = 0L;
		}
		return resultLong;
	}
	
	protected Object getObject(final String hql,final Object...values){
	   return super.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				@SuppressWarnings("rawtypes")
				Query query = session.createQuery(hql);
				setParameters(query,values);
				return query.uniqueResult();
			}
		});
	}
	
	protected Object findObjectBySql(final String sql,final Object...values){
		return super.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				@SuppressWarnings("rawtypes")
				Query query = session.createNamedQuery(sql);
				setParameters(query, values);
				return query.uniqueResult();
			}
		});
	}
	
	protected Object findObject(final String hql,final Object...values){
		return super.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				@SuppressWarnings("rawtypes")
				Query query = session.createQuery(hql);
				setParameters(query, values);
				return query.uniqueResult();
			}
		});
	}
	
	protected void findPageBySql(Pagination page,String sql,Object...values){
		if(null==page){
			page = new Pagination(Pagination.DEFAULT_PAGE_INDEX, Pagination.DEFAULT_PAGE_SIZE);
		}
		int offset = -1;
		if(page.isEnabledFlag() && page.getPageIndex()>0 && page.getPageSize()>0){
			String totalSql = "select count(*) from ("+sql+")";
			Object object = findObjectBySql(totalSql,values);
			int rowTotal = 0;
			if(null!=object){
				BigDecimal bigDecimal = (BigDecimal)object;
				rowTotal = bigDecimal.intValue();
			}
			page.setRowTotal(rowTotal);
			offset = page.calculateOffset();
		}
		
		page.setDatas(findListBySql(sql,offset,page.getPageSize(),values));
	}
	
	protected void findListBySql(Pagination page,final String sql,Object...values){
		int offset = -1;
		if(page.getPageIndex()>0 && page.getPageSize()>0){
			offset = page.calculateOffset();
		}
		page.setDatas(findListBySql(sql,offset,page.getPageSize(),values));
	}
	
	@SuppressWarnings("rawtypes")
	protected List findListBySql(final String sql,final int offset,final int rowCount,final Object...values){
		 return super.getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query query = session.createNativeQuery(sql);
				if(offset>0){
					query.setFirstResult(offset);
				}
				
				if(rowCount>0){
					query.setMaxResults(rowCount);
				}
				
				setParameters(query, values);
				
				return query.list();
			}
			
		});
	}
	
	protected void setParameters(@SuppressWarnings("rawtypes") final Query query,final Object...values){
		if(null!=values && values.length>0){
			int index = 0;
			for(Object object:values){
				query.setParameter(index++, object);
			}
		}
	}
	
	protected Session getSession(){
		return this.getSessionFactory().getCurrentSession();
	}
	
	protected List<T> findByCriterion(Criterion...criterions){
		Criteria criteria = getSession().createCriteria(this.persistentClass);
		for(Criterion c:criterions){
			criteria.add(c);
		}
		return findByCriteria(criteria);
	}
	
	protected List<T> findByCriteria(Criteria criteria){
		return criteria.list();
	}
	
	protected void findByPage(Criteria criteria,Pagination page){
		if(null==page){
			return;
		}
		
		if(page.isEnabledFlag() && page.getPageIndex()>0 && page.getPageSize()>0){
			Integer totalRecord = (Integer)criteria.setProjection(Projections.rowCount()).uniqueResult();
			page.setRowTotal(totalRecord);
			
			int offset = page.calculateOffset();
			criteria.setFirstResult(offset);
		}
		
		if(page.getPageSize()>0){
			criteria.setMaxResults(page.getPageSize());
		}
		page.setDatas(criteria.list());
	}
	
	protected String getHqlSql(String hql){
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)this.getSessionFactory();
		QueryTranslatorFactory queryTranslatorFactory = sessionFactory.getSettings().getQueryTranslatorFactory();
		FilterTranslator filterTranslator = queryTranslatorFactory.createFilterTranslator(hql, hql, null, sessionFactory);
		filterTranslator.compile(null, false);
		return filterTranslator.getSQLString();
	}

}
