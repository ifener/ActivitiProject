package com.wey.framework.service.auth;

import com.wey.framework.model.auth.User;
import com.wey.framework.service.GenericManager;

public interface UserManager extends GenericManager<User, Long> {
	
	public User saveUser(User user) throws Exception;
	
	public User findUserById(Long id);
	
	public User findUserByLoginId(String loginId);
}
