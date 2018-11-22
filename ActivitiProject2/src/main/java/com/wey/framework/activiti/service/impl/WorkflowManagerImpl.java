package com.wey.framework.activiti.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wey.framework.activiti.bo.ProcessDefinitionBO;
import com.wey.framework.activiti.constants.Constants;
import com.wey.framework.activiti.model.TaskInfo;
import com.wey.framework.activiti.model.Workflow;
import com.wey.framework.activiti.service.WorkflowManager;
import com.wey.framework.exception.ServiceException;
import com.wey.framework.util.Context;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Service
public class WorkflowManagerImpl implements WorkflowManager{

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	private static final String APPLICANT="applicant";
	
	@Override
	@Transactional
	public void deploy(ZipInputStream zis, String name) {
		try {
			Deployment deploy = repositoryService.createDeployment().name(name).addZipInputStream(zis).deploy();
			System.out.println(deploy.getId());
			
	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("部署时没有找到文件："+e.getMessage());
		}
		
	}

	/**
	 * 查找流程定义
	 */
	@Override
	public Pagination findProcessDefinition(Pagination page) {
	    
		//latestVersion()方法，只查询最新的流程
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion();
		ProcessDefinitionBO processDefinitionBO = (ProcessDefinitionBO)page.getSearch();
		if(processDefinitionBO!=null) {
			String processKey = processDefinitionBO.getProcessKey();
			String processName = processDefinitionBO.getProcessName();
			if(StringUtils.hasText(processKey)) {
				processDefinitionQuery = processDefinitionQuery.processDefinitionKey(processKey);
			}
			
			if(StringUtils.hasText(processName)) {
				processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike("%"+processName+"%");
			}
		}
		
		
		
		//查询出总数据条数
		long rowTotal = processDefinitionQuery.count();
		int pageIndex = page.getPageIndex();
		int pageSize = page.getPageSize();
		//查询出当前页的数据
		List<ProcessDefinition> processDefinitions = processDefinitionQuery.active().orderByProcessDefinitionId().desc().listPage((pageIndex-1)*pageSize, pageSize);
		
		page.setRowTotal((int)rowTotal);
		page.setDatas(processDefinitions);
		return page;
	}

	@Override
	public InputStream findProcessDefinitionImage(String deploymentId, String resourceName) {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}

	@Override
	public void deleteProcessDefinition(String deploymentId) {
		//非级联删除，
		//repositoryService.deleteDeployment(deploymentId);
		//true级联删除，如果该流程有启动过的实例也一同删除
		repositoryService.deleteDeployment(deploymentId,true);
	}

	/**
	 * 启动流程
	 */
	@Override
	public void start(Workflow workflow) {
		String businessKey = workflow.getBusinessKey();
		String processDefinitionKey = workflow.getProcessDefinitionKey();
		
		Long userId = ContextUtil.getContext().getUserId();
		Map<String,Object> variables = workflow.getVariables();
		//启动流程的时候将当前用户设置到申请人内
		variables.put(APPLICANT, userId);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,variables);
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		taskService.complete(task.getId());
	}

	/**
	 * 通过办理人跟流程定义KEY查找任务
	 */
	@Override
	public List<TaskInfo> findTaskList(String assignee, String processDefinitionKey) {
		List<TaskInfo> taskInfoes = new ArrayList<TaskInfo>();
		List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).taskAssignee(assignee).orderByTaskCreateTime().desc().list();
		if(list!=null && list.size()>0) {
			for(Task task:list) {
				TaskInfo taskInfo = new TaskInfo();
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				taskInfo.setTask(task);
				taskInfo.setProcessInstance(processInstance);
				taskInfo.setBusinessKey(processInstance.getBusinessKey());
				if(StringUtils.hasText(processInstance.getBusinessKey())) {
					taskInfo.setBizId(Long.parseLong(processInstance.getBusinessKey()));
				}
				taskInfoes.add(taskInfo);
			}
		}
		
		return taskInfoes;
	}

}
