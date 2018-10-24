package com.wey.framework.activiti;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcessDefinitionFromClassPath() {
		
		//与流程定义与部署相关的service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("审批单流程").addClasspathResource("diagrams/audit.bpmn").addClasspathResource("diagrams/audit.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * 从ZIP是部署流程
	 */
	@Test
	public void deployProcessDefinitionFromZipFile() {
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/audit.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		
		//与流程定义与部署相关的service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//创建部署对象
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("审批单流程").addZipInputStream(zipInputStream).
				deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	
	/**
	 * 查询流程定义
	 */
	@Test
	public void findProcessDefinition() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//流程定义查询
		ProcessDefinitionQuery createProcessDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> list = createProcessDefinitionQuery.processDefinitionKey("audit").orderByProcessDefinitionVersion().desc().list();
		if(list!=null && list.size()>0) {
			for(ProcessDefinition pd:list) {
				System.out.println("流程定义ID="+pd.getId());
				//XML文件里面的name
				// <process id="audit" name="审批" isExecutable="true">
				System.out.println("流程定义NAME="+pd.getName()); 
				System.out.println("流程定义KEY="+pd.getKey());
				System.out.println("流程定义VERSION="+pd.getVersion());
				System.out.println("流程定义BPMN="+pd.getResourceName());
				System.out.println("流程定义PNG="+pd.getDiagramResourceName());
				System.out.println("流程定义部署对象ID="+pd.getDeploymentId());
			}
		}
	}
	
	/**
	 * 删除流程定义
	 */
	@Test
	public void deleteProcessDefinition() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String deploymentId="1";
		/**
		 * 只能删除没有没有启动过的流程，否则会报错
		 */
		//repositoryService.deleteDeployment(deploymentId);
		
		
		/**
		 * 删除启动过的流程，级联删除
		 */
		repositoryService.deleteDeployment(deploymentId,true);
		System.out.println("删除成功");
	}
	
	/**
	 * 查看流程图
	 * @throws IOException 
	 */
	@Test
	public void viewProcessDefinitionPic() throws IOException {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String deploymentId="301";
		String resourceName="";
		/**
		 * 资源文件
		 */
		List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
		if(deploymentResourceNames!=null && deploymentResourceNames.size()>0) {
			for(String name:deploymentResourceNames) {
				if(name.indexOf(".png")>0) {
					resourceName = name;
					break;
				}
			}
		}
		
		InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
		File file = new File("C:\\Users\\User\\Desktop\\temp\\1.png");
		//FileUtils.copyInputStreamToFile(resourceAsStream, file);
		FileOutputStream ops = new FileOutputStream(file);
		byte[] bys = new byte[1024];
		int len=0;
		while((len=resourceAsStream.read(bys, 0, bys.length))>0){
			ops.write(bys);
		}
		ops.close();
	}
	
	/**
	 * 获取所有的流程定义（有效的）
	 */
	@Test
	public void findAllProcessDefinitionPic() {
		List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().orderByProcessDefinitionVersion().asc().list();
		Map<String,ProcessDefinition> maps = new LinkedHashMap<>(16);
		if(list!=null && list.size()>0) {
			for(ProcessDefinition pd:list) {
				maps.put(pd.getKey(), pd);
			}
		}
		
		list = new ArrayList<>(maps.values());
		if(list!=null && list.size()>0) {
			for(ProcessDefinition pd:list) {
				System.out.println("流程定义ID="+pd.getId());
				//XML文件里面的name
				// <process id="audit" name="审批" isExecutable="true">
				System.out.println("流程定义NAME="+pd.getName()); 
				System.out.println("流程定义KEY="+pd.getKey());
				System.out.println("流程定义VERSION="+pd.getVersion());
				System.out.println("流程定义BPMN="+pd.getResourceName());
				System.out.println("流程定义PNG="+pd.getDiagramResourceName());
				System.out.println("流程定义部署对象ID="+pd.getDeploymentId());
			}
		}
	}
	
	/**
	 * 判断流程实例是否结束
	 */
	@Test
	public void isProcessEnd(){
        String		processInstanceId="processEngine";
        //查询正在执行的对象
		ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(pi==null) {
			System.out.println("流程已结束");
		} else {
			System.out.println("流程没有结束");
		}
	}
	
	
	/**
	 * 查询历史任务
	 */
	@Test
	public void queryHistoryTask() {
		List<HistoricTaskInstance> list = processEngine.getHistoryService() //历史相关的
		             .createHistoricTaskInstanceQuery()//创建历史任务实例查询
		             .taskAssignee("张三").list();
		if(list!=null && list.size()>0) {
			for(HistoricTaskInstance hti:list) {
				System.out.println("办理人："+hti.getAssignee());
				System.out.println("执行ID："+hti.getExecutionId());
				System.out.println("名称："+hti.getName());
				System.out.println("流程实例ID："+hti.getProcessInstanceId());
			}
		}
	}
}
