package com.wey.framework.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wey.framework.model.BaseObject;

@Entity
@Table(name="BAS_POSITION")
public class Position extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String positonCode;
	
	private String positionName;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}

	@Column(name="POSITION_NAME")
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name="POSITION_CODE")
	public String getPositonCode() {
		return positonCode;
	}

	public void setPositonCode(String positonCode) {
		this.positonCode = positonCode;
	}

	
}
