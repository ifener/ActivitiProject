package com.wey.framework.model.common;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wey.framework.model.BaseObject;

@Entity
@Table(name="BAS_MENU_TITLE")
public class MenuTitle extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Menu menu;
	private String title;
	private String language;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}
	
	@ManyToOne(optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name="MENU_ID")
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="LANGUAGE")
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
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
