package com.wey.framework.model.auth;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wey.framework.model.BaseObject;


@Entity
@Table(name="BAS_ROLE")
public class Role extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String roleName;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ROLE_ID")
	public Long getId() {
		return this.id;
	}

	@Column(name="ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
