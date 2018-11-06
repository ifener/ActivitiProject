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
		System.out.println("部署成功：ID=" + deploy.getId());
	}

	@Test
	public void processInstance() {
		/**
		 * 启动流程并指定任务候选者
		 */
		Map<String, Object> candidateUsers = new HashMap<>(16);
		candidateUsers.put("users", "小文,小华,小尧,小立");
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("CandidateUserFlow", candidateUsers);
		System.out.println("启动流程实例：" + processInstance.getId());
	}

	@Test
	public void findTaskByCandidateUser() {
		// 查找候选人的任务
		String candidateUser = "小文";
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskCandidateUser(candidateUser).list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务ID=" + task.getId());
				System.out.println("名称Name=" + task.getName());
				System.out.println("审批=" + task.getAssignee());
			}
		}
	}

	@Test
	public void findTaskCandidateUser() {
		/**
		 * 查找任务的办理候选人
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
		 * 查找任务的办理候选人
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
		 * 认领任务，用户可以是候选者列表中成员，也可以不是
		 */
		String taskId = "3405";
		//String userId="小斌";
	    //processEngine.getTaskService().claim(taskId, userId);

		String userId = "小文";
		processEngine.getTaskService().claim(taskId, userId);
	}

	@Test
	public void discardClaimTask() {
		/**
		 * 将个人任务回退到组任务，前提这个任务之前是组任务
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
