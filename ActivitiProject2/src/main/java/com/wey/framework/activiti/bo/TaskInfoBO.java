package com.wey.framework.activiti.bo;

import org.activiti.engine.task.Task;

public class TaskInfoBO {
	
	private Task task;
	private Object bizObj;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Object getBizObj() {
		return bizObj;
	}
	public void setBizObj(Object bizObj) {
		this.bizObj = bizObj;
	}
	
	
}
