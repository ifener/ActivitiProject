package com.wey.framework.dao.activiti.impl;

import org.springframework.stereotype.Repository;

import com.wey.framework.activiti.model.RefundBill;
import com.wey.framework.dao.activiti.RefundBillDao;
import com.wey.framework.dao.impl.GenericDaoImpl;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.Pagination;

@Repository
public class RefundBillDaoImpl extends GenericDaoImpl<RefundBill, Long> implements RefundBillDao {

	public RefundBillDaoImpl() {
		super(RefundBill.class);
	}
	
	

	@Override
	public void findByPage(Pagination page) {
	    Long userId = ContextUtil.getContext().getUserId();
	    String hql="from RefundBill where user.id=?";
	    findPage(hql, page, userId);
	}
	
}
