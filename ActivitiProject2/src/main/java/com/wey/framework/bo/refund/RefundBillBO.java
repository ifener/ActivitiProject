package com.wey.framework.bo.refund;

import java.util.Date;

import com.wey.framework.bo.BaseBO;
import com.wey.framework.model.refund.RefundBill;

public class RefundBillBO extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date[] dateRange;

	public RefundBillBO() {
		super.setBaseVO(new RefundBill());
	}
	
	
	public RefundBill getRefundBill() {
		return (RefundBill)getBaseVO();
	}
	
	public void setRefundBill(RefundBill refundBill) {
		super.setBaseVO(refundBill);
	}

	public Date[] getDateRange() {
		return dateRange;
	}

	public void setDateRange(Date[] dateRange) {
		this.dateRange = dateRange;
	}
}
