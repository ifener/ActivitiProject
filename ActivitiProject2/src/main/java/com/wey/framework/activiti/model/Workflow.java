package com.wey.framework.activiti.model;

import java.util.HashMap;
import java.util.Map;

public class Workflow {

	private Map<String, Object> variables = new HashMap<String, Object>();
	private String processDefinitionKey;
	private String businessKey;
	private String taskId;
	private String advice;
	private String transition;
	private String approve;

	public Workflow(String processDefinitionKey, String businessKey) {
		super();
		this.processDefinitionKey = processDefinitionKey;
		this.businessKey = businessKey;
	}

	public Workflow(String taskId, String transition, String approve, String advice) {
		this.taskId = taskId;
		this.transition = transition;
		this.advice = advice;
		this.approve = approve;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public void addVariables(String key, Object value) {
		variables.put(key, value);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

}
