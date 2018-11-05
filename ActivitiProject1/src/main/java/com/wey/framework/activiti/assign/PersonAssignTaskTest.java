package com.wey.framework.activiti.assign;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class PersonAssignTaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deploymentProcess(){
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addInputStream("AssignPersonByClass.bpmn", this.getClass().getClassLoader().getResourceAsStream("flows/assign/AssignPersonByClass.bpmn"));
		deploymentBuilder.addInputStream("AssignPersonByClass.png", this.getClass().getClassLoader().getResourceAsStream("flows/assign/AssignPersonByClass.png"));
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println("部署成功：ID="+deploy.getId());
	}
	
	@Test
	public void processInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("AssignPersonByClass");
		System.out.println("启动流程实例："+processInstance.getId());
	}
	
	
	@Test
	public void findTask() {
		String taskAssign="张高丽";
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(taskAssign).list();
		if(list!=null && list.size()>0) {
			for(Task task:list) {
				System.out.println("任务ID="+task.getId());
				System.out.println("名称Name="+task.getName());
				System.out.println("审批="+task.getAssignee());
			}
		}
	}
}
