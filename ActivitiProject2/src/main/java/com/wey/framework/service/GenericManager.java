package com.wey.framework.service;

import java.io.Serializable;
import java.util.List;

public interface GenericManager<T,PK extends Serializable> {
	
    public List<T> getAll();
	
	public List<T> getAllByIDs(@SuppressWarnings("unchecked") PK...paramArgs);
	
	public T get(PK id);
	
	public T get(PK id,boolean lazy);
	
	public Object get(Class paramClass,Serializable paramSerializable);
	
	public boolean exists(PK id);
	
	public T save(T paramT);
	
	public void remove(@SuppressWarnings("unchecked") PK... ids);

	public void removeByBulk(@SuppressWarnings("unchecked") PK...paramArgs);
}
