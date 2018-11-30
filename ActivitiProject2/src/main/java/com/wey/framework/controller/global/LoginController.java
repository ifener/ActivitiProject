package com.wey.framework.controller.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.wey.framework.controller.BaseController;
import com.wey.framework.model.auth.User;
import com.wey.framework.service.auth.UserManager;
import com.wey.framework.util.Context;
import com.wey.framework.util.ContextUtil;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserManager userManager;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/doLogin")
	public String doLogin(User user) {
		User usr = userManager.findUserByLoginId(user.getLoginId());
		if (usr != null && usr.getLoginPassword().equals(user.getLoginPassword())) {
			session.setAttribute("user", usr);
			Context context = new Context();
			context.setUser(user);
			ContextUtil.setContext(context);
			return "redirect:index";
		}
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "login";
	}
}
