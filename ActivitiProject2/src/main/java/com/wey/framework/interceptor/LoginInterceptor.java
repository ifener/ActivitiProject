package com.wey.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.wey.framework.model.auth.User;
import com.wey.framework.util.Context;
import com.wey.framework.util.ContextUtil;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		Object object = session.getAttribute("user");
		if(object==null) {
			// 未登录，重定向到登录页面
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		Context context = new Context();
		context.setUser((User)object);
		ContextUtil.setContext(context);
		
		return true;
	}
}
