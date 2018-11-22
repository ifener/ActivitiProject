package com.wey.framework.dao.refund;


import com.wey.framework.dao.GenericDao;
import com.wey.framework.model.refund.RefundBill;
import com.wey.framework.util.Pagination;

public interface RefundBillDao extends GenericDao<RefundBill, Long> {

	void findByPage(Pagination page);
	
	void findTaskList(Pagination page);
}
