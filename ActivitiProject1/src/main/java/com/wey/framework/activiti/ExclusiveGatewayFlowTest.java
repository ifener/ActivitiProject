package com.wey.framework.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * ��������
 * @author User
 *
 */
public class ExclusiveGatewayFlowTest {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcessDefinitionFromClassPath() {
		
		//�����̶����벿����ص�service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//�����������
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("����������").addClasspathResource("flows/exclusiveGatewayFlow/ExclusiveGatewayFlow.bpmn").addClasspathResource("flows/exclusiveGatewayFlow/ExclusiveGatewayFlow.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * ����һ������
	 */
	@Test
	public void startProcessInstance() {
		Map<String, Object> maps = new HashMap<String, Object>(16);
		maps.put("user", "С��");
		maps.put("manager", "С��");
		maps.put("finance", "С��");
		maps.put("ceo", "С��");
		maps.put("amount", 1600);
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("EXCLUSIVEGATEWAYFLOW",maps);
		System.out.println("ID="+processInstance.getId()+"���̶���ID="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * ��ѯ��ǰ����
	 */
	@Test
	public void findMyPersonalTask() {
		//��������taskAssignee
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("С��").list();
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
		String taskId="3103";
		TaskService taskService = processEngine.getTaskService();
		//taskService.setVariable(taskId, "amount", 200);
		taskService.complete(taskId);
		System.out.println("�������");
	}
	
}
