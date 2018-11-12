package com.wey.framework.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wey.framework.model.BaseObject;

@Entity
@Table(name="BAS_DEPARTMENT")
public class Department extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String departmentName;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}

	@Column(name="DEPARTMENT_NAME")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	

}
