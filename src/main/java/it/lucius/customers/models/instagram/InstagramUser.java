package it.lucius.customers.models.instagram;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import it.lucius.customers.models.User;


/**
 * The persistent class for the instagram_users database table.
 * 
 */
@Entity
@Table(name="instagram_users")
@NamedQuery(name="InstagramUser.findAll", query="SELECT i FROM InstagramUser i")
public class InstagramUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Long idInstagramUser;

	private String token;

	private String username;

	private String fullName;
	private String profilePictureURI;
	private String bio;
	private String website;
	private String mediaCountString;
	private String followerCountString;
	private String followingCountString;
	
	@Transient
	private List<InstagramMedia> mediaList;
	
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	public InstagramUser() {
	}

	public Long getIdInstagramUser() {
		return this.idInstagramUser;
	}

	public String getFullName() {
		return fullName;
	}

	public List<InstagramMedia> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<InstagramMedia> mediaList) {
		this.mediaList = mediaList;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfilePictureURI() {
		return profilePictureURI;
	}

	public void setProfilePictureURI(String profilePictureURI) {
		this.profilePictureURI = profilePictureURI;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMediaCountString() {
		return mediaCountString;
	}

	public void setMediaCountString(String mediaCountString) {
		this.mediaCountString = mediaCountString;
	}

	public String getFollowerCountString() {
		return followerCountString;
	}

	public void setFollowerCountString(String followerCountString) {
		this.followerCountString = followerCountString;
	}

	public String getFollowingCountString() {
		return followingCountString;
	}

	public void setFollowingCountString(String followingCountString) {
		this.followingCountString = followingCountString;
	}

	public void setIdInstagramUser(Long idInstagramUser) {
		this.idInstagramUser = idInstagramUser;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}