package com.wey.framework.activiti.model;

import java.util.HashMap;
import java.util.Map;

public class Workflow {
	
	public Workflow(String processDefinitionKey, String businessKey) {
		super();
		this.processDefinitionKey = processDefinitionKey;
		this.businessKey = businessKey;
	}
	
	private Map<String, Object> variables = new HashMap<String,Object>();
	private String processDefinitionKey;
	private String businessKey;
	
	
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
	public void addVariables(String key,Object value) {
		variables.put(key, value);
	}
	
	
}
