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
		
		//�����̶����벿����ص�service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//�����������
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("����������").addZipInputStream(zipInputStream).
				deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * ����һ������
	 */
	@Test
	public void startProcessInstance() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("BaoxiaoFlow");
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
	
	/**
	 * ��һ���ڵ�����ʱ�������̱���
	 */
	@Test
	public void auditProcessTask() {
		String taskId="1804";
		Map<String, Object> maps = new HashMap<String,Object>(16);
		maps.put("amount", 1000);
		maps.put("manager", "����"); //������������Ա
		processEngine.getTaskService().complete(taskId,maps);
		System.out.println("�������");
	}
	
	/**
	 * �ڶ����ڵ��������
	 */
	@Test
	public void auditProcessTaskOfManager() {
		String taskId="903";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������");
	}
	
}
