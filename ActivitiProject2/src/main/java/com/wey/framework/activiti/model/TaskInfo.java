package com.wey.framework.activiti.model;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TaskInfo {

	private Task task;
	private ProcessInstance processInstance;
	private String businessKey;
	private Long bizId;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public Long getBizId() {
		return bizId;
	}
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}
	
	
}
