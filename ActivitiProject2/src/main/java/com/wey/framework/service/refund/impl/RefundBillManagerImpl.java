package com.wey.framework.service.refund.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wey.framework.activiti.bo.TaskInfoBO;
import com.wey.framework.activiti.bo.WorkflowBO;
import com.wey.framework.activiti.constants.Constants;
import com.wey.framework.activiti.constants.WorkflowStatus;
import com.wey.framework.activiti.model.TaskInfo;
import com.wey.framework.activiti.model.Workflow;
import com.wey.framework.activiti.service.WorkflowManager;
import com.wey.framework.bo.refund.RefundBillBO;
import com.wey.framework.dao.refund.RefundBillDao;
import com.wey.framework.exception.ServiceException;
import com.wey.framework.model.refund.RefundBill;
import com.wey.framework.service.auth.UserManager;
import com.wey.framework.service.impl.GenericManagerImpl;
import com.wey.framework.service.refund.RefundBillManager;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Service
public class RefundBillManagerImpl extends GenericManagerImpl<RefundBill, Long> implements RefundBillManager {

	@Autowired
	private void setRefundBillDao(RefundBillDao refundBillDao) {
		super.setGenericDao(refundBillDao);
	}

	@Autowired
	WorkflowManager workflowManager;

	@Autowired
	UserManager userManager;

	@Override
	public RefundBillDao getDao() {
		return (RefundBillDao) super.getDao();
	}

	@Override
	public void findByPage(Pagination page) {
		this.getDao().findByPage(page);
	}

	@Override
	@Transactional
	public RefundBill saveAndStart(RefundBill refundBill) {
		try {
			refundBill = save(refundBill);

			Workflow workflow = new Workflow(PROCESS_NAME, refundBill.getId() + "");
			// 注入相关的参数
			workflow.addVariables("amount", refundBill.getRefundAmount());
			// 副经理
			workflow.addVariables("assistantManager",
					userManager.findUserIdByDeptIdAndPositionId(refundBill.getUser().getDepartmentId(), 1L));
			// 副经理
			workflow.addVariables("manager",
					userManager.findUserIdByDeptIdAndPositionId(refundBill.getUser().getDepartmentId(), 2L));
			// 总经理
			workflow.addVariables("generalManager", userManager.findUserIdByDeptIdAndPositionId(0L, 4L));
			// CEO
			workflow.addVariables("ceo", userManager.findUserIdByDeptIdAndPositionId(0L, 5L));

			workflowManager.start(workflow);
			return refundBill;
		} catch (Exception e) {
			throw new ServiceException("发生异常" + e.getMessage());
		}
	}

	@Override
	@Transactional
	public RefundBill saveAuditing(RefundBill refundBill, WorkflowBO workflowBO) {
		try {

			refundBill = this.get(refundBill.getId());

			if (refundBill == null) {
				throw new ServiceException("找不到单据！");
			}

			List<TaskInfo> taskInfoList = workflowManager.findTaskList(ContextUtil.getContext().getUserId() + "",
					PROCESS_NAME);
			Task task = null;
			if (taskInfoList != null && taskInfoList.size() > 0) {
				for (TaskInfo taskInfo : taskInfoList) {
					if (taskInfo.getBizId() == refundBill.getId()) {
						task = taskInfo.getTask();
						break;
					}
				}
			}

			if (task == null) {
				throw new ServiceException("找不到任务！");
			}

			String approve = Constants.Y.equals(workflowBO.getTransition()) ? Constants.TRANSITION_PASS
					: Constants.TRANSITION_UNPASS;
			Workflow workflow = new Workflow(task.getId(), workflowBO.getTransition(), approve, workflowBO.getAdvice());

			if (Constants.Y.equals(workflowBO.getTransition())) {
				// 审批中
				refundBill.setAuditStatus(WorkflowStatus.PROCESSING.getCode());
			} else {
				// 审批不通过
				refundBill.setAuditStatus(WorkflowStatus.UNPASS.getCode());
			}

			if (workflowManager.singal(workflow)) {
				if (Constants.Y.equals(workflowBO.getTransition())) {
					// 审批通过
					refundBill.setAuditStatus(WorkflowStatus.PASS.getCode());
				}
			}

			refundBill = this.save(refundBill);
			return refundBill;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new com.wey.framework.exception.ServiceException("An error was occured " + e.getMessage());
		}
	}

	/**
	 * 查找任务
	 */
	@Override
	@Transactional
	public void findTaskList(Pagination page, RefundBillBO refundBillBO) {
		List<TaskInfo> findTaskList = workflowManager.findTaskList(ContextUtil.getContext().getUserId() + "",
				PROCESS_NAME);
		List<Long> bizIds = new ArrayList<Long>();
		if (findTaskList != null && findTaskList.size() > 0) {
			for (TaskInfo task : findTaskList) {
				bizIds.add(task.getBizId());
			}
		}

		refundBillBO.setIds(bizIds.toArray(new Long[bizIds.size()]));
		page.setSearch(refundBillBO);
		getDao().findTaskList(page);

		List<RefundBill> refundBills = page.getDatas();
		if (refundBills != null && refundBills.size() > 0) {
			List<TaskInfoBO> list = new ArrayList<TaskInfoBO>();
			for (RefundBill r : refundBills) {
				for (TaskInfo task : findTaskList) {
					if (r.getId().equals(task.getBizId())) {
						TaskInfoBO taskInfoBO = new TaskInfoBO();
						taskInfoBO.setTask(task.getTask());
						taskInfoBO.setBizObj(r);
						list.add(taskInfoBO);
					}
				}
			}
			page.setDatas(list);
		}
	}

}
