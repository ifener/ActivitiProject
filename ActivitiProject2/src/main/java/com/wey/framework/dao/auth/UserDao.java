package com.wey.framework.dao.auth;

import java.util.List;

import com.wey.framework.dao.GenericDao;
import com.wey.framework.model.auth.User;

public interface UserDao extends GenericDao<User, Long> {
	User findUserByLoginId(String loginId);
	
	List<User> findUserByDeptIdAndPositionId(Long departmentId, Long positionId);
}
