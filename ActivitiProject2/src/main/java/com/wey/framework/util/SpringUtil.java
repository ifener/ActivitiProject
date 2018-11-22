package com.wey.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
	
	private static ApplicationContext ctx = null;
	
	public static Object getBean(String beanName){
		Object object = null;
		if(ctx!=null){
			object = ctx.getBean(beanName);
		}
		return object;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicateionContext) throws BeansException {
		ctx = applicateionContext;
	}

}
