package it.lucius.customers.bean;

import it.lucius.customers.models.Role;

public class RoleBean {

	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	public final static String ROLE_USER = "ROLE_USER";
	
	private Integer idRole;
	private String role;
	
	
	public RoleBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleBean(Role role) {
		super();
		this.idRole = role.getIdRole();
		this.role = role.getRole();
	}
	
	
	public Integer getIdRole() {
		return idRole;
	}
	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
