package it.lucius.customers.bean.instagram;

import it.lucius.customers.models.instagram.pod.InstagramPodUser;

public class InstaPodUser {

	private Integer idPodUser;

	private String profilePicture;

	private String username;
	
	public InstaPodUser() {
		super();
	}

	public InstaPodUser(InstagramPodUser u) {
		super();
		this.idPodUser = u.getIdPodUser();
		this.profilePicture = u.getProfilePicture();
		this.username = u.getUsername();
	}
	
	public Integer getIdPodUser() {
		return idPodUser;
	}

	public void setIdPodUser(Integer idPodUser) {
		this.idPodUser = idPodUser;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
