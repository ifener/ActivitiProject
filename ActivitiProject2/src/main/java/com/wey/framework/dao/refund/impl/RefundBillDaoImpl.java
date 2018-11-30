package com.wey.framework.dao.refund.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.wey.framework.bo.refund.RefundBillBO;
import com.wey.framework.dao.impl.GenericDaoImpl;
import com.wey.framework.dao.refund.RefundBillDao;
import com.wey.framework.model.refund.RefundBill;
import com.wey.framework.util.ContextUtil;
import com.wey.framework.util.DateUtil;
import com.wey.framework.util.Pagination;

@Repository
public class RefundBillDaoImpl extends GenericDaoImpl<RefundBill, Long> implements RefundBillDao {

	public RefundBillDaoImpl() {
		super(RefundBill.class);
	}

	@Override
	public void findByPage(Pagination page) {
		Long userId = ContextUtil.getContext().getUserId();
		String hql = "from RefundBill where user.id=?";
		List<Object> parameterValues = new ArrayList<Object>();
		parameterValues.add(userId);
		RefundBillBO refundBillBO = (RefundBillBO) page.getSearch();
		if (refundBillBO != null) {
			String subject = refundBillBO.getRefundBill().getSubject();
			Date[] dateRange = refundBillBO.getDateRange();

			if (StringUtils.hasText(subject)) {
				hql += " and subject like ?";
				parameterValues.add(parseLike(subject, true, true));
			}

			if (dateRange != null && dateRange.length == 2) {
				hql += " and refundTime>=?";
				hql += " and refundTime<=?";
				try {
					parameterValues.add(DateUtil
							.convertStringToTimestamp(DateUtil.convertDateToString(dateRange[0]) + " 00:00:00"));
					parameterValues.add(DateUtil
							.convertStringToTimestamp(DateUtil.convertDateToString(dateRange[1]) + " 23:59:59"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		hql += " order by id desc";

		findPage(hql, page, parameterValues.toArray(new Object[parameterValues.size()]));
	}

	@Override
	public void findTaskList(Pagination page) {
		Criteria criteria = createCriteria();

		RefundBillBO refundBillBO = (RefundBillBO) page.getSearch();
		if (refundBillBO != null) {
			Long[] ids = refundBillBO.getIds();
			String subject = refundBillBO.getRefundBill().getSubject();
			Date[] dateRange = refundBillBO.getDateRange();

			if (ids != null && ids.length > 0) {
				criteria.add(Restrictions.in("id", ids));
			}

			if (StringUtils.hasText(subject)) {
				criteria.add(Restrictions.like("subject", parseLike(subject, true, true)));
			}

			if (dateRange != null && dateRange.length == 2) {
				try {

					criteria.add(Restrictions.ge("refundTime", DateUtil
							.convertStringToTimestamp(DateUtil.convertDateToString(dateRange[0]) + " 00:00:00")));

					criteria.add(Restrictions.le("refundTime", DateUtil
							.convertStringToTimestamp(DateUtil.convertDateToString(dateRange[1]) + " 23:59:59")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		criteria.addOrder(Order.desc("id"));

		findByPage(criteria, page);
	}

}
