package com.wey.framework.activiti.service;

import com.wey.framework.activiti.model.RefundBill;
import com.wey.framework.service.GenericManager;
import com.wey.framework.util.Pagination;

public interface RefundBillManager extends GenericManager<RefundBill, Long> {
	
	void findByPage(Pagination page);
}
