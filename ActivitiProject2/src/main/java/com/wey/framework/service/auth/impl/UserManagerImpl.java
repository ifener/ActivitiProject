package com.wey.framework.service.auth.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

	public UserDao getDao() {
		return (UserDao) super.getDao();
	}

	@Override
	public User saveUser(User user) throws Exception {
		user = this.getDao().save(user);
		return user;
	}

	@Override
	@Cacheable(cacheNames = "myCache", key = "#id")
	public User findUserById(Long id) {
		return this.getDao().get(id);
	}

	@Override
	public User findUserByLoginId(String loginId) {
		try {
			return this.getDao().findUserByLoginId(loginId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<User> findUserByDeptIdAndPositionId(Long departmentId, Long positionId) {
		
		try {
			return this.getDao().findUserByDeptIdAndPositionId(departmentId, positionId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public String findUserIdByDeptIdAndPositionId(Long departmentId, Long positionId) {

		try {
			StringBuilder builder = new StringBuilder();
			List<User> list = this.getDao().findUserByDeptIdAndPositionId(departmentId, positionId);
			if (list != null && list.size() > 0) {

				for (User u : list) {
					builder.append(u.getId().toString()).append(",");
				}
			}

			if (builder.length() > 0) {
				builder.deleteCharAt(builder.length()-1);
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}
