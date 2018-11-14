package com.wey.framework.activiti.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.wey.framework.model.auth.User;
import com.wey.framework.service.auth.UserManager;
import com.wey.framework.util.Context;
import com.wey.framework.util.ContextUtil;

@Controller
public class LoginController {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private HttpSession session;

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(User user) {
		User usr = userManager.findUserByLoginId(user.getLoginId());
		if(usr!=null && usr.getLoginPassword().equals(user.getLoginPassword())) {
			session.setAttribute("user", usr);
			Context context = new Context();
			context.setUser(user);
			ContextUtil.setContext(context);
			return "redirect:main";
		}
		
		return "login";
	}
}
