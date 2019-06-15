package it.lucius.customers.bean.instagram;

import it.lucius.customers.models.instagram.InstastatsDay;
import it.lucius.customers.models.instagram.InstastatsMonth;
import it.lucius.customers.models.instagram.InstastatsWeek;

public class InstaStatsself {

	private Long followersCount;
	private Long followersDiff;
	private Long followingCount;
	private Long followingDiff;
	private Long mediaCount;
	private Long mediaDiff;
	private Long likesCount;
	private Long likesDiff;
	private Long commentsCount;
	private Long commentsDiff;
	private String lastUpdated;
	
	public InstaStatsself(Object day) {
		super();
		
		if(day instanceof InstastatsDay) {
			this.setFollowersCount(((InstastatsDay) day).getFollowersCount());
			this.setFollowingCount(((InstastatsDay) day).getFollowingCount());
			this.setMediaCount(((InstastatsDay) day).getMediaCount());
			this.setLikesCount(((InstastatsDay) day).getLikesCount());
			this.setCommentsCount(((InstastatsDay) day).getCommentsCount());
			
			this.setFollowersDiff(new Long(0));
			this.setFollowingDiff(new Long(0));
			this.setMediaDiff(new Long(0));
			this.setLikesDiff(new Long(0));
			this.setCommentsDiff(new Long(0));
			
		} else if(day instanceof InstastatsWeek) {
		
			this.setFollowersCount(((InstastatsWeek) day).getFollowersCount());
			this.setFollowingCount(((InstastatsWeek) day).getFollowingCount());
			this.setMediaCount(((InstastatsWeek) day).getMediaCount());
			this.setLikesCount(((InstastatsWeek) day).getLikesCount());
			this.setCommentsCount(((InstastatsWeek) day).getCommentsCount());
			
			this.setFollowersDiff(new Long(0));
			this.setFollowingDiff(new Long(0));
			this.setMediaDiff(new Long(0));
			this.setLikesDiff(new Long(0));
			this.setCommentsDiff(new Long(0));
			
		} else if(day instanceof InstastatsMonth) {
			
			this.setFollowersCount(((InstastatsMonth) day).getFollowersCount());
			this.setFollowingCount(((InstastatsMonth) day).getFollowingCount());
			this.setMediaCount(((InstastatsMonth) day).getMediaCount());
			this.setLikesCount(((InstastatsMonth) day).getLikesCount());
			this.setCommentsCount(((InstastatsMonth) day).getCommentsCount());
			
			this.setFollowersDiff(new Long(0));
			this.setFollowingDiff(new Long(0));
			this.setMediaDiff(new Long(0));
			this.setLikesDiff(new Long(0));
			this.setCommentsDiff(new Long(0));
		}
	}

	public InstaStatsself(InstastatsDay day, InstastatsDay dayDiff) {
		super();
		this.setFollowersCount(day.getFollowersCount());
		this.setFollowingCount(day.getFollowingCount());
		this.setMediaCount(day.getMediaCount());
		this.setLikesCount(day.getLikesCount());
		this.setCommentsCount(day.getCommentsCount());
		
		this.setFollowersDiff(this.getFollowersCount() - dayDiff.getFollowersCount());
		this.setFollowingDiff(this.getFollowingCount() - dayDiff.getFollowingCount());
		this.setLikesDiff(this.getLikesCount() - dayDiff.getLikesCount());
		this.setCommentsDiff(this.getCommentsCount() - dayDiff.getCommentsCount());
		this.setMediaCount(this.getMediaCount() - dayDiff.getMediaCount());
		
	}
	
	public InstaStatsself(InstastatsWeek day, InstastatsWeek dayDiff) {
		super();
		this.setFollowersCount(day.getFollowersCount());
		this.setFollowingCount(day.getFollowingCount());
		this.setMediaCount(day.getMediaCount());
		this.setLikesCount(day.getLikesCount());
		this.setCommentsCount(day.getCommentsCount());
		
		this.setFollowersDiff(this.getFollowersCount() - dayDiff.getFollowersCount());
		this.setFollowingDiff(this.getFollowingCount() - dayDiff.getFollowingCount());
		this.setLikesDiff(this.getLikesCount() - dayDiff.getLikesCount());
		this.setCommentsDiff(this.getCommentsCount() - dayDiff.getCommentsCount());
		this.setMediaCount(this.getMediaCount() - dayDiff.getMediaCount());
		
	}
	
	public InstaStatsself(InstastatsMonth day, InstastatsMonth dayDiff) {
		super();
		this.setFollowersCount(day.getFollowersCount());
		this.setFollowingCount(day.getFollowingCount());
		this.setMediaCount(day.getMediaCount());
		this.setLikesCount(day.getLikesCount());
		this.setCommentsCount(day.getCommentsCount());
		
		this.setFollowersDiff(this.getFollowersCount() - dayDiff.getFollowersCount());
		this.setFollowingDiff(this.getFollowingCount() - dayDiff.getFollowingCount());
		this.setLikesDiff(this.getLikesCount() - dayDiff.getLikesCount());
		this.setCommentsDiff(this.getCommentsCount() - dayDiff.getCommentsCount());
		this.setMediaCount(this.getMediaCount() - dayDiff.getMediaCount());
		
	}
	
	public Long getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}
	public Long getFollowersDiff() {
		return followersDiff;
	}
	public void setFollowersDiff(Long followersDiff) {
		this.followersDiff = followersDiff;
	}
	public Long getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(Long followingCount) {
		this.followingCount = followingCount;
	}
	public Long getFollowingDiff() {
		return followingDiff;
	}
	public void setFollowingDiff(Long followingDiff) {
		this.followingDiff = followingDiff;
	}
	public Long getMediaCount() {
		return mediaCount;
	}
	public void setMediaCount(Long mediaCount) {
		this.mediaCount = mediaCount;
	}
	public Long getMediaDiff() {
		return mediaDiff;
	}
	public void setMediaDiff(Long mediaDiff) {
		this.mediaDiff = mediaDiff;
	}
	public Long getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}
	public Long getLikesDiff() {
		return likesDiff;
	}
	public void setLikesDiff(Long likesDiff) {
		this.likesDiff = likesDiff;
	}
	public Long getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(Long commentsCount) {
		this.commentsCount = commentsCount;
	}
	public Long getCommentsDiff() {
		return commentsDiff;
	}
	public void setCommentsDiff(Long commentsDiff) {
		this.commentsDiff = commentsDiff;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
