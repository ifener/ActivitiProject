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
 * 排它网关
 * @author User
 *
 */
public class ExclusiveGatewayFlowTest {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcessDefinitionFromClassPath() {
		
		//与流程定义与部署相关的service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("审批单流程").addClasspathResource("flows/exclusiveGatewayFlow/ExclusiveGatewayFlow.bpmn").addClasspathResource("flows/exclusiveGatewayFlow/ExclusiveGatewayFlow.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcessInstance() {
		Map<String, Object> maps = new HashMap<String, Object>(16);
		maps.put("user", "小明");
		maps.put("manager", "小华");
		maps.put("finance", "小李");
		maps.put("ceo", "小月");
		maps.put("amount", 1600);
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("EXCLUSIVEGATEWAYFLOW",maps);
		System.out.println("ID="+processInstance.getId()+"流程定义ID="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查询当前任务
	 */
	@Test
	public void findMyPersonalTask() {
		//个人任务：taskAssignee
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("小明").list();
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
		String taskId="3103";
		TaskService taskService = processEngine.getTaskService();
		//taskService.setVariable(taskId, "amount", 200);
		taskService.complete(taskId);
		System.out.println("任务完成");
	}
	
}
