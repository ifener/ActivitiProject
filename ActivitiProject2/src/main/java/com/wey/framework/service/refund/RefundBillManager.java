package com.wey.framework.service.refund;

import com.wey.framework.activiti.bo.WorkflowBO;
import com.wey.framework.bo.refund.RefundBillBO;
import com.wey.framework.model.refund.RefundBill;
import com.wey.framework.service.GenericManager;
import com.wey.framework.util.Pagination;

public interface RefundBillManager extends GenericManager<RefundBill, Long> {

	public static final String PROCESS_NAME = "RefundBill";

	void findByPage(Pagination page);

	RefundBill saveAndStart(RefundBill refundBill);

	RefundBill saveAuditing(RefundBill refundBill, WorkflowBO workflowBO);

	void findTaskList(Pagination page, RefundBillBO refundBillBO);
}
