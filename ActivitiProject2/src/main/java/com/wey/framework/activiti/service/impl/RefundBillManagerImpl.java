package com.wey.framework.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wey.framework.activiti.model.RefundBill;
import com.wey.framework.activiti.service.RefundBillManager;
import com.wey.framework.dao.activiti.RefundBillDao;
import com.wey.framework.service.impl.GenericManagerImpl;
import com.wey.framework.util.Pagination;

@Service
public class RefundBillManagerImpl extends GenericManagerImpl<RefundBill, Long> implements RefundBillManager {

	@Autowired
	private void setRefundBillDao(RefundBillDao refundBillDao) {
		super.setGenericDao(refundBillDao);
	}
	
	public RefundBillDao getDao() {
		return (RefundBillDao)super.getDao();
	}
	
	@Override
	public void findByPage(Pagination page) {
		this.getDao().findByPage(page);
	}

	
	
}
