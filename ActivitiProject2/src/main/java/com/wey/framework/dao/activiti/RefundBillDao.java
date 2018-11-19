package com.wey.framework.dao.activiti;


import com.wey.framework.activiti.model.RefundBill;
import com.wey.framework.dao.GenericDao;
import com.wey.framework.util.Pagination;

public interface RefundBillDao extends GenericDao<RefundBill, Long> {

	void findByPage(Pagination page);
}
