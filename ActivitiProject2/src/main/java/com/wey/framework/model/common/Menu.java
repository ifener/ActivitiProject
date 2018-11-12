package com.wey.framework.model.common;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wey.framework.model.BaseObject;

@Entity
@Table(name="BAS_MENU")
public class Menu extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Menu parent;
	private String url;
	private String type;
	private String displayFlag;
	private Integer displayIndex;
	private List<MenuTitle> menuTitles;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MENU_ID")
	public Long getId() {
		return this.id;
	}


    @ManyToOne(optional=true,fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID",nullable=true)
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@Column(name="URL",length=200,nullable=true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    @Column(name="DISPLAY_INDEX",nullable=true)
	public Integer getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}

    @Column(name="TYPE",nullable=true)
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Column(name="DISPLAY_FLAG",nullable=true)
	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="menu",fetch=FetchType.EAGER)
	public List<MenuTitle> getMenuTitles() {
		return menuTitles;
	}


	public void setMenuTitles(List<MenuTitle> menuTitles) {
		this.menuTitles = menuTitles;
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
