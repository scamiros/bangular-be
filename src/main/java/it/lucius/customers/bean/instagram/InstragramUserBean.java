package it.lucius.customers.bean.instagram;

import java.util.List;

import it.lucius.customers.models.instagram.InstagramUser;

public class InstragramUserBean {

	private Long idInstagramUser;

	private int id;

	private String token;

	private String userName;

	private String fullName;
	private String profilePictureURI;
	private String bio;
	private String website;
	private String mediaCountString;
	private String followerCountString;
	private String followingCountString;
	
	private List<InstagramMediaBean> media;
	
	public InstragramUserBean(InstagramUser u) {
		
		this.id = u.getId();
		this.idInstagramUser = u.getIdInstagramUser();
		this.userName = u.getUsername();
		this.fullName = u.getFullName();
		this.profilePictureURI = u.getProfilePictureURI();
		this.bio = u.getBio();
		this.website = u.getWebsite();
		this.mediaCountString = u.getMediaCountString();
		this.followerCountString = u.getFollowerCountString();
		this.followingCountString = u.getFollowingCountString();
	}


	public List<InstagramMediaBean> getMedia() {
		return media;
	}


	public void setMedia(List<InstagramMediaBean> media) {
		this.media = media;
	}


	public String getFullName() {
		return fullName;
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

	public Long getIdInstagramUser() {
		return idInstagramUser;
	}

	public void setIdInstagramUser(Long idInstagramUser) {
		this.idInstagramUser = idInstagramUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
