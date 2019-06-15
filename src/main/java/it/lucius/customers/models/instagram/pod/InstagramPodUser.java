package it.lucius.customers.models.instagram.pod;

import java.io.Serializable;
import javax.persistence.*;

import it.lucius.customers.models.User;


/**
 * The persistent class for the instagram_pod_user database table.
 * 
 */
@Entity
@Table(name="instagram_pod_user")
@NamedQuery(name="InstagramPodUser.findAll", query="SELECT i FROM InstagramPodUser i")
public class InstagramPodUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pod_user")
	private Integer idPodUser;

	@Column(name="profile_picture")
	private String profilePicture;

	private String username;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	public InstagramPodUser() {
	}

	public Integer getIdPodUser() {
		return this.idPodUser;
	}

	public void setIdPodUser(Integer idPodUser) {
		this.idPodUser = idPodUser;
	}

	public String getProfilePicture() {
		return this.profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}