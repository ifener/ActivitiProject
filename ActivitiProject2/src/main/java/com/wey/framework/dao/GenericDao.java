package com.wey.framework.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable>
{
  public abstract List<T> getAll();

  public abstract List<T> getAllByIDs(PK[] paramArrayOfPK);

  public abstract T get(PK paramPK);

  public abstract Object get(Class paramClass, Serializable paramSerializable);

  public abstract void initialize(Object[] paramArrayOfObject);

  public abstract boolean exists(PK paramPK);

  public abstract T save(T paramT);

  public abstract void remove(PK[] paramArrayOfPK);

  public abstract void removeByBulk(PK[] paramArrayOfPK);
}
