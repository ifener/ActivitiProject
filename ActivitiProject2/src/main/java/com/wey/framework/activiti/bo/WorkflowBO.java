package com.wey.framework.activiti.bo;

import java.io.Serializable;

public class WorkflowBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String transition;
	private String advice;

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

}
