package com.wey.activiti.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class DeployFlow {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcessDefinition() {
		
		//与流程定义与部署相关的service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("审批单流程").addClasspathResource("diagrams/audit.bpmn").addClasspathResource("diagrams/audit.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcessInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("audit");
		System.out.println("ID="+processInstance.getId()+"流程定义ID="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查询当前任务
	 */
	@Test
	public void findMyPersonalTask() {
		//个人任务：taskAssignee
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("张三").list();
		if(list!=null && list.size()>0) {
			for(Task t:list) {
				System.out.println("任务ID："+t.getId());
				System.out.println("任务执行ID："+t.getExecutionId());
				System.out.println("任务名称："+t.getName());
				System.out.println("任务办理人："+t.getAssignee());
			}
		}
	}
	
	@Test
	public void auditProcessTask() {
		String taskId="204";
		processEngine.getTaskService().complete(taskId);
		System.out.println("任务完成");
	}
}
