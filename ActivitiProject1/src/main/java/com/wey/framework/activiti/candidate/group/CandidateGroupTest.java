package com.wey.framework.activiti.candidate.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class CandidateGroupTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploymentProcess() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addInputStream("CandidateGroupFlow.bpmn",
				this.getClass().getClassLoader().getResourceAsStream("flows/candidateGroup/CandidateGroupFlow.bpmn"));
		deploymentBuilder.addInputStream("CandidateGroupFlow.png",
				this.getClass().getClassLoader().getResourceAsStream("flows/candidateGroup/CandidateGroupFlow.png"));
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println("����ɹ���ID=" + deploy.getId());
		
		processEngine.getIdentityService().saveGroup(new GroupEntity("CEO"));
		processEngine.getIdentityService().saveUser(new UserEntity("С��"));
		processEngine.getIdentityService().createMembership("С��", "CEO");
		System.out.println("���ý�ɫ���");
	}

	@Test
	public void processInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("CandidateGroup");
		System.out.println("��������ʵ����" + processInstance.getId());
	}

	@Test
	public void findTaskByCandidateUser() {
		// ���Һ�ѡ�˵�����
		String candidateUser = "С��";
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskCandidateUser(candidateUser).list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("����ID=" + task.getId());
				System.out.println("����Name=" + task.getName());
				System.out.println("����=" + task.getAssignee());
			}
		}
	}

	
	@Test
	public void complete() {
		String taskId = "3405";
		processEngine.getTaskService().complete(taskId);
	}

}
