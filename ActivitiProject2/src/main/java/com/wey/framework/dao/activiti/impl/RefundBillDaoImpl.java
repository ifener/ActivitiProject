package com.wey.framework.dao.activiti.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wey.framework.activiti.model.RefundBill;
import com.wey.framework.dao.activiti.RefundBillDao;
import com.wey.framework.dao.impl.GenericDaoImpl;

@Repository
public class RefundBillDaoImpl extends GenericDaoImpl<RefundBill, Long> implements RefundBillDao {

	public RefundBillDaoImpl() {
		super(RefundBill.class);
	}
	
	@Autowired
	void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
}
