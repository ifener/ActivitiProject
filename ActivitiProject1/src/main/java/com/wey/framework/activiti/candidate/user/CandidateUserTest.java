package com.wey.framework.activiti.candidate.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class CandidateUserTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploymentProcess() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addInputStream("CandidateUserFlow.bpmn",
				this.getClass().getClassLoader().getResourceAsStream("flows/candidateUser/CandidateUserFlow.bpmn"));
		deploymentBuilder.addInputStream("CandidateUserFlow.png",
				this.getClass().getClassLoader().getResourceAsStream("flows/candidateUser/CandidateUserFlow.png"));
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println("����ɹ���ID=" + deploy.getId());
	}

	@Test
	public void processInstance() {
		/**
		 * �������̲�ָ�������ѡ��
		 */
		Map<String, Object> candidateUsers = new HashMap<>(16);
		candidateUsers.put("users", "С��,С��,СҢ,С��");
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("CandidateUserFlow", candidateUsers);
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
	public void findTaskCandidateUser() {
		/**
		 * ��������İ����ѡ��
		 */
		String taskId = "3405";
		List<IdentityLink> list = processEngine.getTaskService().getIdentityLinksForTask(taskId);
		if (list != null && list.size() > 0) {
			for (IdentityLink id : list) {
				System.out.println(id.getUserId() + "---" + id.getTaskId() + "-----" + id.getType());
			}
		}
	}

	@Test
	public void findTaskHistoricCandidateUser() {
		/**
		 * ��������İ����ѡ��
		 */
		String taskId = "3405";
		List<HistoricIdentityLink> list = processEngine.getHistoryService().getHistoricIdentityLinksForTask(taskId);
		if (list != null && list.size() > 0) {
			for (HistoricIdentityLink id : list) {
				System.out.println(id.getUserId() + "---" + id.getTaskId() + "-----" + id.getType());
			}
		}
	}

	@Test
	public void claimTask() {
		/**
		 * ���������û������Ǻ�ѡ���б��г�Ա��Ҳ���Բ���
		 */
		String taskId = "3405";
		//String userId="С��";
	    //processEngine.getTaskService().claim(taskId, userId);

		String userId = "С��";
		processEngine.getTaskService().claim(taskId, userId);
	}

	@Test
	public void discardClaimTask() {
		/**
		 * ������������˵�������ǰ���������֮ǰ��������
		 */
		String taskId = "3405";
		processEngine.getTaskService().setAssignee(taskId, null);
	}
	
	@Test
	public void complete() {
		String taskId = "3405";
		processEngine.getTaskService().complete(taskId);
	}

}
