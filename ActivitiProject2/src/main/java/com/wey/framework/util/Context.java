package com.wey.framework.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wey.framework.model.auth.User;
import com.wey.framework.model.common.Menu;

public class Context {

	private Logger log = LogManager.getLogger(this.getClass());

	private User user;
	private String url;
	private String ip;
	private Long userId;
	private Long menuId;
	private Long paramMenuId;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParamMenuId() {
		return paramMenuId;
	}

	public void setParamMenuId(Long paramMenuId) {
		this.paramMenuId = paramMenuId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
