package com.wey.framework.service.auth.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wey.framework.dao.auth.UserDao;
import com.wey.framework.model.auth.User;
import com.wey.framework.service.auth.UserManager;
import com.wey.framework.service.impl.GenericManagerImpl;

@Service
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager {
	
	@Autowired
	public void setDao(UserDao userDao) {
		setGenericDao(userDao);
	}
	
	public UserDao getDao(){
		return (UserDao)super.getDao();
	}

	@Override
	public User saveUser(User user) throws Exception {
		 user = this.getDao().save(user);
		 return user;
	}

	@Override
	@Cacheable(cacheNames="myCache",key="#id")
	public User findUserById(Long id) {
		return this.getDao().get(id);
	}

	@Override
	public User findUserByLoginId(String loginId) {
		try {
			return this.getDao().findUserByLoginId(loginId);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}
