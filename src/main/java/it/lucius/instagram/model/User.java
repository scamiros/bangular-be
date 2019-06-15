package it.lucius.instagram.model;
import java.util.List;

import org.json.simple.JSONObject;

import it.lucius.customers.models.instagram.InstagramUser;
import it.lucius.customers.util.InstagramUtil;
import it.lucius.instagram.exception.InstagramException;
import it.lucius.json.JSONException;

public class User extends InstagramModel {
	int id;
	Long idLong;
	String userName;
	String fullName;
	String profilePictureURI;
	String bio;
	String website;
	Long mediaCount = new Long(-1);
	Long followerCount = new Long(-1);
	Long followingCount = new Long(-1);
	String mediaCountString;
	String followerCountString;
	String followingCountString;
	
	List<Media> recentMedia;

	public User(JSONObject obj, String accessToken) throws InstagramException {
		
		super(obj, accessToken);
		
		setIdLong(getLong(obj, "id"));
		setUserName((String) obj.get("username"));
		setFullName((String) obj.get("full_name"));
		setProfilePictureURI((String) obj.get("profile_picture"));
		
		setWebsite((String) obj.get("website"));
		setBio((String) obj.get("bio"));
		
		if(obj.containsKey("counts")) {
			JSONObject counts = (JSONObject) obj.get("counts");
			setFollowerCount((Long) counts.get("followed_by"));
			setFollowingCount((Long) counts.get("follows"));
			setMediaCount((Long) counts.get("media"));
			setFollowerCountString(InstagramUtil.formatSocialNumber((Long) counts.get("followed_by")));
			setFollowingCountString(InstagramUtil.formatSocialNumber((Long) counts.get("follows")));
			setMediaCountString(InstagramUtil.formatSocialNumber((Long) counts.get("media")));			
		} 
	}
	
	public List<Media> getRecentMedia() {
		return recentMedia;
	}

	public void setRecentMedia(List<Media> recentMedia) {
		this.recentMedia = recentMedia;
	}

	public int getId() {
		return id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	protected void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public Long getIdLong() {
		return idLong;
	}

	public void setIdLong(Long idLong) {
		this.idLong = idLong;
	}

	protected void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getProfilePictureURI() {
		return profilePictureURI;
	}
	
	protected void setProfilePictureURI(String profilePictureURI) {
		this.profilePictureURI = profilePictureURI;
	}
	
	protected void setBio(String bio) {
		this.bio = bio;
	}
	
	protected void setWebsite(String website) {
		this.website = website;
	}
	
	protected void setMediaCount(Long mediaCount) {
		this.mediaCount = mediaCount;
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

	protected void setFollowerCount(Long followerCount) {
		this.followerCount = followerCount;
	}
	
	protected void setFollowingCount(Long followingCount) {
		this.followingCount = followingCount;
	}
	
	public String getBio() {
		return bio;
	}

	public String getWebsite() {
		return website;
	}

	public Long getMediaCount() {
		return mediaCount;
	}

	public Long getFollowerCount() {
		return followerCount;
	}

	public Long getFollowingCount() {
		return followingCount;
	}

	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(o.getClass() != this.getClass()) return false;
		return ((User)o).getId() == getId();
	}
	
	public long getLong(JSONObject obj, String key) {
        Object object = obj.get(key);
        return object instanceof Number
            ? ((Number)object).longValue()
            : Long.parseLong((String)object);
    }
}
