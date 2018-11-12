package com.wey.framework.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wey.framework.dao.GenericDao;
import com.wey.framework.exception.ServiceException;
import com.wey.framework.service.GenericManager;

public class GenericManagerImpl<T,PK extends Serializable> implements GenericManager<T, PK> {
	
	protected Logger log = LogManager.getLogger(getClass());

	protected GenericDao<T,PK> genericDao;
	
	public GenericManagerImpl(GenericDao<T,PK> genericDao){
		this.genericDao = genericDao;
	}
	
	
	@Override
	public List<T> getAll() {
		try {
			return getDao().getAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "getAll", new Object[0]);
		}
	}

	@Override
	public List<T> getAllByIDs(PK... paramArgs) {
		try {
			return getDao().getAllByIDs(paramArgs);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "getAllByIDs", new Object[0]);
		}
	}

	@Override
	public T get(PK id) {
		try {
			return getDao().get(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "get", new Object[0]);
		}
	}
	@Override
	public T get(PK id,boolean lazy){
		try {
			T result = getDao().get(id);
			if(!lazy){
				getDao().initialize(new Object[]{result});
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "get", new Object[0]);
		}
	}

	public Object get(Class paramClass,Serializable paramSerializable){
		try {
			return this.getDao().get(paramClass, paramSerializable);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "get", new Object[0]);
		}
	}

	@Override
	public boolean exists(PK id) {
		try {
			return this.getDao().exists(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "exists", new Object[0]);
		}
	}

	@Override
	public T save(T paramT) {
		try {
			return this.getDao().save(paramT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "save", new Object[0]);
		}
	}

	@Override
	public void remove(PK... ids) {
		try {
			this.getDao().remove(ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "remove", new Object[0]);
		}
	}

	@Override
	public void removeByBulk(PK... paramArgs) {
		try {
			this.getDao().removeByBulk(paramArgs);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ServiceException(e, "removeByBulk", new Object[0]);
		}
	}
	
	public GenericDao<T, PK> getDao(){
		return this.genericDao;
	}

}
