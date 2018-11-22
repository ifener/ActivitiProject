package com.wey.framework.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.task.Task;

import com.wey.framework.activiti.model.TaskInfo;
import com.wey.framework.activiti.model.Workflow;
import com.wey.framework.util.Pagination;

public interface WorkflowManager {

	/**
	 * 部署工作流
	 * @param file
	 * @param name
	 */
	void deploy(ZipInputStream zis,String name);
	
	/**
	 * 查找流程定义
	 * @param page
	 * @return
	 */
	Pagination findProcessDefinition(Pagination page);
	
	/**
	 * 查找流程定义的图片
	 * @param deploymentId
	 * @param resourceName
	 * @return
	 */
	InputStream findProcessDefinitionImage(String deploymentId,String resourceName);
	
	/**
	 * 通过流程部署ID删除数据
	 * @param deploymentId
	 */
	void deleteProcessDefinition(String deploymentId);
	
	/**
	 * 启动流程
	 * @param workflow
	 */
	void start(Workflow workflow);
	
	List<TaskInfo> findTaskList(String assignee,String processDefinitionKey);
	
}
