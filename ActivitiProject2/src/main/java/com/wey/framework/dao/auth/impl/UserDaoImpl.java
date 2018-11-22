package com.wey.framework.dao.auth.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wey.framework.dao.auth.UserDao;
import com.wey.framework.dao.impl.GenericDaoImpl;
import com.wey.framework.model.auth.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Autowired
	void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public User findUserByLoginId(String loginId) {
		String hql="from User where loginId = ?";
		Object obj = findObject(hql, new Object[] {loginId});
		if(obj!=null) {
			return (User)obj;
		}
		return null;
	}

	@Override
	public List<User> findUserByDeptIdAndPositionId(Long departmentId, Long positionId) {
		String hql="from User where departmentId=? and positionId=?";
		return find(hql, new Object[] {departmentId,positionId});
	}
	
}
