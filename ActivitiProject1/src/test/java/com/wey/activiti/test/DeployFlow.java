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
		
		//�����̶����벿����ص�service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//�����������
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("����������").addClasspathResource("diagrams/audit.bpmn").addClasspathResource("diagrams/audit.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	@Test
	public void startProcessInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("audit");
		System.out.println("ID="+processInstance.getId()+"���̶���ID="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * ��ѯ��ǰ����
	 */
	@Test
	public void findMyPersonalTask() {
		//��������taskAssignee
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("����").list();
		if(list!=null && list.size()>0) {
			for(Task t:list) {
				System.out.println("����ID��"+t.getId());
				System.out.println("����ִ��ID��"+t.getExecutionId());
				System.out.println("�������ƣ�"+t.getName());
				System.out.println("��������ˣ�"+t.getAssignee());
			}
		}
	}
	
	@Test
	public void auditProcessTask() {
		String taskId="104";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������");
	}
}
