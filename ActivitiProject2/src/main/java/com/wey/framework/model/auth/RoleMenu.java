package com.wey.framework.model.auth;

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
import com.wey.framework.model.common.Menu;

@Entity
@Table(name="BAS_ROLE_MENU")
public class RoleMenu extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Role role;
	private Menu menu;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.REFRESH})
	@JoinColumn(name="ROLE_ID", nullable=false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.REFRESH})
	@JoinColumn(name="MENU_ID", nullable=false)
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
