package com.wey.framework.activiti.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wey.framework.util.ContextUtil;

@RestController
public class IndexController {
	
	@GetMapping("/index")
	public String index() {
		String loginId = ContextUtil.getContext().getUser().getLoginId();
		return "index:"+loginId;
	}
}
