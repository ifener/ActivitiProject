package com.wey.framework.model.auth;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wey.framework.model.BaseObject;

@Entity
@Table(name="BAS_USER")
public class User extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loginId;
	
	private String loginPassword;
	
	private String employeeName;	
	
	private Long departmentId;
	
	private Long positionId;
	
	private List<Role> roles;
	
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	public Long getId() {
		return this.id;
	}

	@Column(name="LOGIN_ID")
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Column(name="LOGIN_PASSWORD")
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name="EMPLOYEE_NAME",nullable=true)
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Column(name="DEPARTMENT_ID",nullable=true)
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	@Column(name="POSITION_ID",nullable=true)
	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
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
}
