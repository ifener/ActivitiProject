package com.wey.framework.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

public class DeployFlow {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployProcessDefinition() {
		
		//与流程定义与部署相关的service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("审批单流程").addClasspathResource("flows/audit.bpmn").addClasspathResource("diagrams/audit.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
}
