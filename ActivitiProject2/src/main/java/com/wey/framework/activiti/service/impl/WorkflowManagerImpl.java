package com.wey.framework.activiti.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wey.framework.activiti.bo.ProcessDefinitionBO;
import com.wey.framework.activiti.service.WorkflowManager;
import com.wey.framework.exception.ServiceException;
import com.wey.framework.util.Pagination;

@Service
public class WorkflowManagerImpl implements WorkflowManager{

	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	@Transactional
	public void deploy(ZipInputStream zis, String name) {
		try {
			Deployment deploy = repositoryService.createDeployment().name(name).addZipInputStream(zis).deploy();
			System.out.println(deploy.getId());
			
	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("部署时没有找到文件："+e.getMessage());
		}
		
	}

	@Override
	public Pagination findProcessDefinition(Pagination page) {
	    
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		ProcessDefinitionBO processDefinitionBO = (ProcessDefinitionBO)page.getSearch();
		if(processDefinitionBO!=null) {
			String processKey = processDefinitionBO.getProcessKey();
			String processName = processDefinitionBO.getProcessName();
			if(StringUtils.hasText(processKey)) {
				processDefinitionQuery = processDefinitionQuery.processDefinitionKey(processKey);
			}
			
			if(StringUtils.hasText(processName)) {
				processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike("%"+processName+"%");
			}
		}
		
		//查询出总数据条数
		long rowTotal = processDefinitionQuery.count();
		int pageIndex = page.getPageIndex();
		int pageSize = page.getPageSize();
		//查询出当前页的数据
		List<ProcessDefinition> processDefinitions = processDefinitionQuery.orderByProcessDefinitionId().desc().listPage((pageIndex-1)*pageSize, pageSize);
		
		page.setRowTotal((int)rowTotal);
		page.setDatas(processDefinitions);
		return page;
	}

	@Override
	public InputStream findProcessDefinitionImage(String deploymentId, String resourceName) {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}

	@Override
	public void deleteProcessDefinition(String deploymentId) {
		//非级联删除，
		//repositoryService.deleteDeployment(deploymentId);
		//true级联删除，如果该流程有启动过的实例也一同删除
		repositoryService.deleteDeployment(deploymentId,true);
	}

}
