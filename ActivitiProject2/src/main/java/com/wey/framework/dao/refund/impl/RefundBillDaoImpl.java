package com.wey.framework.dao.refund.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wey.framework.bo.refund.RefundBillBO;
import com.wey.framework.dao.impl.GenericDaoImpl;
import com.wey.framework.dao.refund.RefundBillDao;
import com.wey.framework.model.refund.RefundBill;
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
	    findPage(hql, page,userId);
	}

	@Override
	public void findTaskList(Pagination page) {
		Criteria criteria = createCriteria();
		
		RefundBillBO refundBillBO = (RefundBillBO)page.getSearch();
		if(refundBillBO!=null) {
			Long[] ids = refundBillBO.getIds();
			if(ids!=null && ids.length>0) {
				criteria.add(Restrictions.in("id", ids));
			}
		}
			
		findByPage(criteria,page);
	}
	
}
