package com.wey.framework.activit.sequence;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class SequenceFlowTest {

	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deploymentProcess() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("flows/sequenceFlows/SequenceFlow1.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		
		//与流程定义与部署相关的service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("报销单流程").addZipInputStream(zipInputStream).
				deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcessInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("BaoxiaoFlow");
		System.out.println("ID="+processInstance.getId()+"流程定义ID="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查询当前任务
	 */
	@Test
	public void findMyPersonalTask() {
		//个人任务：taskAssignee
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("李三").list();
		if(list!=null && list.size()>0) {
			for(Task t:list) {
				System.out.println("任务ID："+t.getId());
				System.out.println("任务执行ID："+t.getExecutionId());
				System.out.println("任务名称："+t.getName());
				System.out.println("任务办理人："+t.getAssignee());
			}
		}
	}
	
	/**
	 * 第一个节点审批时设置流程变量
	 */
	@Test
	public void auditProcessTask() {
		String taskId="1804";
		Map<String, Object> maps = new HashMap<String,Object>(16);
		maps.put("amount", 1000);
		maps.put("manager", "李三"); //给流程设置人员
		processEngine.getTaskService().complete(taskId,maps);
		System.out.println("任务完成");
	}
	
	/**
	 * 第二个节点完成任务
	 */
	@Test
	public void auditProcessTaskOfManager() {
		String taskId="903";
		processEngine.getTaskService().complete(taskId);
		System.out.println("任务完成");
	}
	
}
