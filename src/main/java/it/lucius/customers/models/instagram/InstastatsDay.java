package it.lucius.customers.models.instagram;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the instastats_followers database table.
 * 
 */
@Entity
@Table(name="instastats_day")
@NamedQuery(name="InstastatsDay.findAll", query="SELECT i FROM InstastatsDay i")
public class InstastatsDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStats;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtStats;

	private Long followersCount;
	private Long followingCount;
	private Long mediaCount;
	private Long likesCount;
	private Long commentsCount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	
	private Long idInstagram;

	public InstastatsDay() {
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(Long followingCount) {
		this.followingCount = followingCount;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public Long getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Long commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Long getMediaCount() {
		return mediaCount;
	}

	public void setMediaCount(Long mediaCount) {
		this.mediaCount = mediaCount;
	}

	public Long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}

	public int getIdStats() {
		return this.idStats;
	}

	public void setIdStats(int idStats) {
		this.idStats = idStats;
	}

	
	public Date getDtStats() {
		return dtStats;
	}

	public void setDtStats(Date dtStats) {
		this.dtStats = dtStats;
	}

	public Long getIdInstagram() {
		return idInstagram;
	}

	public void setIdInstagram(Long idInstagram) {
		this.idInstagram = idInstagram;
	}
}