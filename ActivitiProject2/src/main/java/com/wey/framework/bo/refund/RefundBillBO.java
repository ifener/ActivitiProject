package com.wey.framework.bo.refund;

import com.wey.framework.bo.BaseBO;
import com.wey.framework.model.refund.RefundBill;

public class RefundBillBO extends BaseBO {

	public RefundBillBO() {
		super.setBaseVO(new RefundBill());
	}
	
	public RefundBill getRefundBill() {
		return (RefundBill)getBaseVO();
	}
	
	public void setRefundBill(RefundBill refundBill) {
		super.setBaseVO(refundBill);
	}
}
