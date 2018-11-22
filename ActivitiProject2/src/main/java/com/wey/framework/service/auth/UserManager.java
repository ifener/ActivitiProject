package com.wey.framework.service.auth;

import java.util.List;

import com.wey.framework.model.auth.User;
import com.wey.framework.service.GenericManager;

public interface UserManager extends GenericManager<User, Long> {
	
	User saveUser(User user) throws Exception;
	
	User findUserById(Long id);
	
	User findUserByLoginId(String loginId);
	
	List<User> findUserByDeptIdAndPositionId(Long departmentId,Long positionId);
	
	String findUserIdByDeptIdAndPositionId(Long departmentId,Long positionId);
}
