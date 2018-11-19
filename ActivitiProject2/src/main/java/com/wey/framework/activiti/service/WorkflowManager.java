package com.wey.framework.activiti.service;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

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
	
	
}
