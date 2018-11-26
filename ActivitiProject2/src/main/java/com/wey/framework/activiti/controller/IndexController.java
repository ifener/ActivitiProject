package com.wey.framework.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wey.framework.util.ContextUtil;

@Controller
public class IndexController {
	
	@GetMapping("/index")
	public String index() {
		String loginId = ContextUtil.getContext().getUser().getLoginId();
		return "index";
	}
	
	@GetMapping("/top")
	public String top() {
		return "top";
	}
	
	@GetMapping("/left")
	public String left() {
		return "left";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
}
