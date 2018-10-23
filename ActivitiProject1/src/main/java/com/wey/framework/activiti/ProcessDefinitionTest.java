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
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcessDefinitionFromClassPath() {
		
		//�����̶����벿����ص�service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//�����������
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		
		Deployment deployment = createDeployment.name("����������").addClasspathResource("diagrams/audit.bpmn").addClasspathResource("diagrams/audit.png").deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * ��ZIP�ǲ�������
	 */
	@Test
	public void deployProcessDefinitionFromZipFile() {
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/audit.zip");
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
	 * ��ѯ���̶���
	 */
	@Test
	public void findProcessDefinition() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//���̶����ѯ
		ProcessDefinitionQuery createProcessDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> list = createProcessDefinitionQuery.processDefinitionKey("audit").orderByProcessDefinitionVersion().desc().list();
		if(list!=null && list.size()>0) {
			for(ProcessDefinition pd:list) {
				System.out.println("���̶���ID="+pd.getId());
				//XML�ļ������name
				// <process id="audit" name="����" isExecutable="true">
				System.out.println("���̶���NAME="+pd.getName()); 
				System.out.println("���̶���KEY="+pd.getKey());
				System.out.println("���̶���VERSION="+pd.getVersion());
				System.out.println("���̶���BPMN="+pd.getResourceName());
				System.out.println("���̶���PNG="+pd.getDiagramResourceName());
				System.out.println("���̶��岿�����ID="+pd.getDeploymentId());
			}
		}
	}
	
	/**
	 * ɾ�����̶���
	 */
	@Test
	public void deleteProcessDefinition() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String deploymentId="1";
		/**
		 * ֻ��ɾ��û��û�������������̣�����ᱨ��
		 */
		//repositoryService.deleteDeployment(deploymentId);
		
		
		/**
		 * ɾ�������������̣�����ɾ��
		 */
		repositoryService.deleteDeployment(deploymentId,true);
		System.out.println("ɾ���ɹ�");
	}
	
	/**
	 * �鿴����ͼ
	 * @throws IOException 
	 */
	@Test
	public void viewProcessDefinitionPic() throws IOException {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String deploymentId="301";
		String resourceName="";
		/**
		 * ��Դ�ļ�
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
	 * ��ȡ���е����̶��壨��Ч�ģ�
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
				System.out.println("���̶���ID="+pd.getId());
				//XML�ļ������name
				// <process id="audit" name="����" isExecutable="true">
				System.out.println("���̶���NAME="+pd.getName()); 
				System.out.println("���̶���KEY="+pd.getKey());
				System.out.println("���̶���VERSION="+pd.getVersion());
				System.out.println("���̶���BPMN="+pd.getResourceName());
				System.out.println("���̶���PNG="+pd.getDiagramResourceName());
				System.out.println("���̶��岿�����ID="+pd.getDeploymentId());
			}
		}
	}
}
