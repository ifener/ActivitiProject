package com.wey.framework.controller.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.wey.framework.util.ExceptionUtil;

@ControllerAdvice
public class ExceptionControler {

	/**
	 * 此类要用@ControllerAdvice修饰一下
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView processUnauthenticatedException(final Throwable e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ExceptionUtil.getExceptionStack(e));
		mv.setViewName("errorPage");
		return mv;
	}
}
