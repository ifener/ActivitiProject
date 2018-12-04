package com.wey.framework.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.ProcessDefinition;

import com.wey.framework.activiti.bo.CommentBO;
import com.wey.framework.activiti.model.TaskInfo;
import com.wey.framework.activiti.model.Workflow;
import com.wey.framework.util.Pagination;

public interface WorkflowManager {

	/**
	 * 部署工作流
	 * 
	 * @param file
	 * @param name
	 */
	void deploy(ZipInputStream zis, String name);

	/**
	 * 查找流程定义
	 * 
	 * @param page
	 * @return
	 */
	Pagination findProcessDefinition(Pagination page);

	/**
	 * 查找流程定义的图片
	 * 
	 * @param deploymentId
	 * @param resourceName
	 * @return
	 */
	InputStream findProcessDefinitionImage(String deploymentId, String resourceName);

	/**
	 * 通过流程部署ID删除数据
	 * 
	 * @param deploymentId
	 */
	void deleteProcessDefinition(String deploymentId);

	/**
	 * 启动流程
	 * 
	 * @param workflow
	 */
	void start(Workflow workflow);

	/**
	 * 审批流程
	 * 
	 * @param workflow
	 * @return
	 */
	boolean singal(Workflow workflow);

	/**
	 * 查找流程
	 * 
	 * @param assignee
	 * @param processDefinitionKey
	 * @return
	 */
	List<TaskInfo> findTaskList(String assignee, String processDefinitionKey);

	/**
	 * 查找历史的批注信息
	 * 
	 * @param processKey
	 * @param bizId
	 */
	List<CommentBO> findHistoricalComments(String processKey, Long bizId);

	/**
	 * 通过业务ID跟流程定义KEY查找当前的流程定义
	 * 
	 * @param bizId
	 * @return
	 */
	ProcessDefinition findProcessDefinitionByBizId(String processKey, Long bizId);

	/**
	 * 获取当前业务数据的流程坐标
	 * 
	 * @param processKey
	 * @param bizId
	 * @return
	 */
	Map<String, Object> findCurrentTaskCoordinate(String processKey, Long bizId);

}
