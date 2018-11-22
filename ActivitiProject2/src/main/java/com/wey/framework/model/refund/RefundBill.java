package com.wey.framework.model.refund;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wey.framework.activiti.constants.WorkflowStatus;
import com.wey.framework.model.BaseObject;
import com.wey.framework.model.auth.User;

@Entity
@Table(name="WEY_REFUND_BILL")
public class RefundBill extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	private BigDecimal refundAmount;
	
	private String subject;
	
	private String content;
	
	private Timestamp refundTime;
	
	private Long auditStatus;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}

	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name="REFUND_AMOUNT")
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(name="SUBJECT")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="REFUND_TIME")
	public Timestamp getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Timestamp refundTime) {
		this.refundTime = refundTime;
	}

	@Column(name="AUDIT_STATUS")
	public Long getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Override
	@Column(name="CREATED_BY",nullable=true)
	public Long getCreatedBy() {
		return this.createdBy;
	}

	@Override
	@Column(name="CREATED_DATE",nullable=true)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	@Override
	@Column(name="UPDATED_BY",nullable=true)
	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	@Override
	@Column(name="UPDATED_DATE",nullable=true)
	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}


	@Override
	@Column(name="ENABLED_FLAG",nullable=true)
	public String getEnabledFlag() {
		return this.enabledFlag;
	}
	
	@Transient
	public String getAuditDesc() {
		if(this.auditStatus==null) {
			return null;
		}
		return WorkflowStatus.getWorkflowName(this.auditStatus);
			
	}
	
	
}
