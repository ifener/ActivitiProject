package com.wey.framework.dao.impl;

import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Settings;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SQLCriterion;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.hibernate.persister.entity.Queryable;
import org.hibernate.type.AssociationType;
import org.hibernate.type.DateType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.wey.framework.dao.GenericDao;

public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport
  implements GenericDao<T, PK>
{
  protected final Log log = LogFactory.getLog(getClass());
  private final Class<T> persistentClass;
  private Object returnHisObject;
  private static Pattern pattern = Pattern.compile("\\$(.*?)\\$");

  public GenericDaoImpl(Class<T> persistentClass)
  {
    this.persistentClass = persistentClass;
  }

  public List<T> getAll()
  {
    return super.getHibernateTemplate().loadAll(this.persistentClass);
  }

  public List<T> getAllByIDs(PK[] ids)
  {
    List result = new ArrayList();

    if ((ids != null) && (ids.length > 0))
    {
      for (PK id : ids)
      {
        if (id == null)
        {
          continue;
        }

        Object entity = get(id);
        if (entity == null)
          continue;
        result.add(entity);
      }

    }

    return result;
  }

  public T get(PK id)
  {
    return (T) get(this.persistentClass, id);
  }

  public Object get(Class clazz, Serializable id)
  {
    Object entity = super.getHibernateTemplate().get(clazz, id);

    if (entity == null)
    {
      this.log.warn("Uh oh, '" + clazz + "' object with id '" + id + "' not found...");
    }

    return entity;
  }

  public void initialize(Object[] objs)
  {
    if ((objs == null) || (objs.length < 1))
    {
      return;
    }

    for (Object o : objs)
    {
      if (o == null)
      {
        continue;
      }
      if (Hibernate.isInitialized(o))
        continue;
      Hibernate.initialize(o);
    }
  }

  public boolean exists(PK id)
  {
    return get(id) != null;
  }

  public T save(T object)
  {
    T t = null;
    //boolean baseObjectFlag = BaseObject.class.isAssignableFrom(object.getClass());
    //if ((baseObjectFlag) && (null != object))
    //{
      //BaseObject baseObject = (BaseObject)object;

      //saveBaseObject(baseObject);
      t = super.getHibernateTemplate().merge(object);
    //}

    return t;
  }


  public Object getReturnHisObject()
  {
    return this.returnHisObject;
  }

  public void setReturnHisObject(Object returnHisObject)
  {
    this.returnHisObject = returnHisObject;
  }

  public void remove(PK[] ids)
  {
    if ((ids == null) || (ids.length == 0))
    {
      return;
    }
    int i = 0; for (int len = ids.length; i < len; i++)
    {
      if (ids[i] == null)
      {
        continue;
      }

      super.getHibernateTemplate().delete(get(ids[i]));
      if (i % 20 != 0)
        continue;
      super.getHibernateTemplate().flush();
      super.getHibernateTemplate().clear();
    }
  }

  public void removeByBulk(PK[] ids)
  {
    if ((ids == null) || (ids.length == 0))
    {
      return;
    }
    super.getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException
      {
        String hqlDelete = "delete " + GenericDaoImpl.this.persistentClass.getName() + " where id in (:ids)";
        int deletedEntities = session.createQuery(hqlDelete).setParameterList("ids", ids).executeUpdate();
        return Integer.valueOf(deletedEntities);
      }
    });
  }

  protected List find(String hql)
  {
    return find(hql, new Object[0]);
  }

  protected List find(String hql, Object[] values)
  {
    return find(hql, -1, -1, values);
  }

  protected List find(String hql, int offset, int rowCount, Object[] values)
  {
    List returnList = null;
    try
    {
      String LastHql = hql.toString();
      returnList = (List) super.getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          Query queryObject = session.createQuery(LastHql);
          if (values != null)
          {
            for (int i = 0; i < values.length; i++)
            {
              queryObject.setParameter(i, values[i]);
            }
          }

          if (offset > 0)
          {
            queryObject.setFirstResult(offset);
          }
          if (rowCount > 0)
          {
            queryObject.setMaxResults(rowCount);
          }

          return queryObject.list();
        }
      });
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error: " + e.getMessage());
    }
    return returnList;
  }
}