package it.lucius.customers.bean;

import it.lucius.customers.models.User;

public class UserProfile {

	private Integer id;
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private RoleBean  role;
	private String token;
	private boolean enabled;
	private boolean expired;
	private Integer lastInstaUser;
	
	public UserProfile(User u) {
		super();
		this.id = u.getId();
		this.username = u.getUsername();
		//this.password = u.getPassword();
		this.email = u.getEmail();
		this.firstname = u.getFirstname();  
		this.lastname = u.getLastname();
		this.role = new RoleBean(u.getRole());
		this.token = u.getToken();
		this.enabled = u.getEnabled();
		this.expired = u.getExpired();
		this.lastInstaUser = u.getLastInstaUser();
	}
	
	
	public Integer getLastInstaUser() {
		return lastInstaUser;
	}

	public void setLastInstaUser(Integer lastInstaUser) {
		this.lastInstaUser = lastInstaUser;
	}

	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public boolean isExpired() {
		return expired;
	}


	public void setExpired(boolean expired) {
		this.expired = expired;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public UserProfile() {
		super();
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
