package com.wey.framework.activiti.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wey.framework.activiti.bo.CommentBO;
import com.wey.framework.activiti.bo.ProcessDefinitionBO;
import com.wey.framework.activiti.model.TaskInfo;
import com.wey.framework.activiti.model.Workflow;
import com.wey.framework.activiti.service.WorkflowManager;
import com.wey.framework.exception.ServiceException;
import com.wey.framework.model.auth.User;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Service
public class WorkflowManagerImpl implements WorkflowManager {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	// @Autowired
	// private DynamicBpmnService dynamicBpmnService;

	private static final String APPLICANT = "applicant";

	@Override
	@Transactional
	public void deploy(ZipInputStream zis, String name) {
		try {
			Deployment deploy = repositoryService.createDeployment().name(name).addZipInputStream(zis).deploy();
			System.out.println(deploy.getId());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("部署时没有找到文件：" + e.getMessage());
		}

	}

	/**
	 * 查找流程定义
	 */
	@Override
	public Pagination findProcessDefinition(Pagination page) {

		// latestVersion()方法，只查询最新的流程
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
				.latestVersion();
		ProcessDefinitionBO processDefinitionBO = (ProcessDefinitionBO) page.getSearch();
		if (processDefinitionBO != null) {
			String processKey = processDefinitionBO.getProcessKey();
			String processName = processDefinitionBO.getProcessName();
			if (StringUtils.hasText(processKey)) {
				processDefinitionQuery = processDefinitionQuery.processDefinitionKey(processKey);
			}

			if (StringUtils.hasText(processName)) {
				processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike("%" + processName + "%");
			}
		}

		// 查询出总数据条数
		long rowTotal = processDefinitionQuery.count();
		int pageIndex = page.getPageIndex();
		int pageSize = page.getPageSize();
		// 查询出当前页的数据
		List<ProcessDefinition> processDefinitions = processDefinitionQuery.active().orderByProcessDefinitionId().desc()
				.listPage((pageIndex - 1) * pageSize, pageSize);

		page.setRowTotal((int) rowTotal);
		page.setDatas(processDefinitions);
		return page;
	}

	@Override
	public InputStream findProcessDefinitionImage(String deploymentId, String resourceName) {
		return repositoryService.getResourceAsStream(deploymentId, resourceName);
	}

	@Override
	public void deleteProcessDefinition(String deploymentId) {
		// 非级联删除，
		// repositoryService.deleteDeployment(deploymentId);
		// true级联删除，如果该流程有启动过的实例也一同删除
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**
	 * 启动流程
	 */
	@Override
	public void start(Workflow workflow) {
		String businessKey = workflow.getBusinessKey();
		String processDefinitionKey = workflow.getProcessDefinitionKey();

		Long userId = ContextUtil.getContext().getUserId();
		Map<String, Object> variables = workflow.getVariables();
		// 启动流程的时候将当前用户设置到申请人内
		variables.put(APPLICANT, userId);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,
				variables);

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

		// 添加批注
		addComment(task.getId(), task.getProcessInstanceId(), workflow.getApprove(), workflow.getAdvice());
		// 提单时审批
		taskService.complete(task.getId());
	}

	@Override
	public boolean singal(Workflow workflow) {
		Task task = taskService.createTaskQuery().taskId(workflow.getTaskId()).singleResult();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("transition", workflow.getTransition());

		// 添加批注
		addComment(task.getId(), task.getProcessInstanceId(), workflow.getApprove(), workflow.getAdvice());
		// 完成任务
		taskService.complete(task.getId(), variables);

		// 检查流程是否结束了
		// ProcessInstance pi =
		// runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
		// .singleResult();

		long count = historyService.createHistoricProcessInstanceQuery().finished()
				.processDefinitionId(task.getProcessInstanceId()).count();

		if (count > 0) {
			// 如果流程实例到了就表示流程结束了
			return true;
		}

		return false;
	}

	/**
	 * 添加批注
	 * 
	 * @param taskId
	 * @param processInstanceId
	 * @param type
	 * @param advice
	 */
	private void addComment(String taskId, String processInstanceId, String type, String advice) {
		User user = ContextUtil.getContext().getUser();
		String userId = user.getEmployeeName() + "[" + user.getLoginId() + "]";
		// 设置用户
		Authentication.setAuthenticatedUserId(userId);
		// 添加审批意见
		taskService.addComment(taskId, processInstanceId, type, advice);
	}

	/**
	 * 通过办理人跟流程定义KEY查找任务
	 */
	@Override
	public List<TaskInfo> findTaskList(String assignee, String processDefinitionKey) {
		List<TaskInfo> taskInfoes = new ArrayList<TaskInfo>();
		List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
				.taskAssignee(assignee).orderByTaskCreateTime().desc().list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				TaskInfo taskInfo = new TaskInfo();
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
						.processInstanceId(task.getProcessInstanceId()).singleResult();
				taskInfo.setTask(task);
				taskInfo.setProcessInstance(processInstance);
				taskInfo.setBusinessKey(processInstance.getBusinessKey());
				if (StringUtils.hasText(processInstance.getBusinessKey())) {
					taskInfo.setBizId(Long.parseLong(processInstance.getBusinessKey()));
				}
				taskInfoes.add(taskInfo);
			}
		}

		return taskInfoes;
	}

	/**
	 * 查找历史的批注信息
	 */
	@Override
	public List<CommentBO> findHistoricalComments(String processKey, Long bizId) {
		try {
			List<CommentBO> list = new ArrayList<CommentBO>();
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceBusinessKey(bizId.toString()).processDefinitionKey(processKey).singleResult();
			if (historicProcessInstance != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(historicProcessInstance.getId());

				comments.sort(new Comparator<Comment>() {

					@Override
					public int compare(Comment o1, Comment o2) {
						return o1.getTime().before(o2.getTime()) ? -1 : 1;
					}
				});

				if (comments != null && comments.size() > 0) {
					for (Comment comment : comments) {
						CommentBO commentBO = new CommentBO();
						commentBO.setComment(comment);
						HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery()
								.taskId(comment.getTaskId()).singleResult();
						if (taskInstance != null) {
							commentBO.setTaskName(taskInstance.getName());
						}
						list.add(commentBO);
					}
				}
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取历史批注失败:" + e.getMessage());
		}
	}

	/**
	 * 通过流程定义KEY跟业务ID获取流程定义
	 */
	@Override
	public ProcessDefinition findProcessDefinitionByBizId(String processKey, Long bizId) {
		try {
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceBusinessKey(bizId.toString()).processDefinitionKey(processKey).singleResult();

			return repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取数据失败:" + e.getMessage());
		}
	}

	@Override
	public Map<String, Object> findCurrentTaskCoordinate(String processKey, Long bizId) {
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceBusinessKey(bizId.toString()).processDefinitionKey(processKey).singleResult();

			BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

			Task task = taskService.createTaskQuery().processInstanceBusinessKey(bizId.toString())
					.processDefinitionKey(processKey).singleResult();

			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(task.getTaskDefinitionKey());

			maps.put("x", graphicInfo.getX());
			maps.put("y", graphicInfo.getY());
			maps.put("width", graphicInfo.getWidth());
			maps.put("height", graphicInfo.getHeight());

			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取数据失败:" + e.getMessage());
		}
	}

}
