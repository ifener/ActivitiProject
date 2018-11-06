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
		System.out.println("部署成功：ID=" + deploy.getId());
		
		processEngine.getIdentityService().saveGroup(new GroupEntity("CEO"));
		processEngine.getIdentityService().saveUser(new UserEntity("小斌"));
		processEngine.getIdentityService().createMembership("小斌", "CEO");
		System.out.println("设置角色完成");
	}

	@Test
	public void processInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("CandidateGroup");
		System.out.println("启动流程实例：" + processInstance.getId());
	}

	@Test
	public void findTaskByCandidateUser() {
		// 查找候选人的任务
		String candidateUser = "小斌";
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
	public void complete() {
		String taskId = "3405";
		processEngine.getTaskService().complete(taskId);
	}

}
