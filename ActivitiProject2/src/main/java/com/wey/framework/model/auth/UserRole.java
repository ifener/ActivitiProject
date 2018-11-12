package com.wey.framework.model.auth;

import javax.persistence.CascadeType;
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
@Table(name="BAS_USER_ROLE")
public class UserRole extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Role role;
	
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

	@ManyToOne(optional=false,fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	@JoinColumn(name="ROLE_ID")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
