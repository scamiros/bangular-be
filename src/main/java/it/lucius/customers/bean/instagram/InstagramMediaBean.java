package it.lucius.customers.bean.instagram;

import java.util.List;

import it.lucius.customers.models.instagram.InstagramMedia;


public class InstagramMediaBean {
	private Integer idMedia;

	private String caption;

	private Long commentsCount;

	private String createdTimestamp;

	private String filter;

	private Long idInstagram;

	private String idInstagramMedia;

	private Long likesCount;

	private String link;

	private String location;

	private String type;

	private String uriLow;

	private String uriStandard;

	private String uriThumb;
	
	private String uriVideo;

	private List<InstagramImageBean> instagramImage;
		
	public String getUriVideo() {
		return uriVideo;
	}

	public void setUriVideo(String uriVideo) {
		this.uriVideo = uriVideo;
	}

	public InstagramMediaBean(InstagramMedia m) {
		
		this.setCaption(m.getCaption());
		this.setCommentsCount(m.getCommentsCount());
		this.setCreatedTimestamp(m.getCreatedTimestamp());
		this.setFilter(m.getFilter());
		this.setIdInstagram(m.getIdInstagram());
		this.setIdInstagramMedia(m.getIdInstagramMedia());
		this.setLikesCount(m.getLikesCount());
		this.setLink(m.getLink());
		this.setLocation(m.getLocation());
		this.setType(m.getType());
		
		this.setUriLow(m.getUriLow());
		this.setUriStandard(m.getUriStandard());
		this.setUriThumb(m.getUriThumb());
		
		if(this.getType().equalsIgnoreCase("video"))
			this.setUriVideo(m.getUriVideo());
	}

	public Integer getIdMedia() {
		return this.idMedia;
	}

	public void setIdMedia(Integer idMedia) {
		this.idMedia = idMedia;
	}

	public List<InstagramImageBean> getInstagramImage() {
		return instagramImage;
	}

	public void setInstagramImage(List<InstagramImageBean> instagramImage) {
		this.instagramImage = instagramImage;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUriLow() {
		return this.uriLow;
	}

	public void setUriLow(String uriLow) {
		this.uriLow = uriLow;
	}

	public String getUriStandard() {
		return this.uriStandard;
	}

	public void setUriStandard(String uriStandard) {
		this.uriStandard = uriStandard;
	}

	public String getUriThumb() {
		return this.uriThumb;
	}

	public void setUriThumb(String uriThumb) {
		this.uriThumb = uriThumb;
	}

	public Long getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Long commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Long getIdInstagram() {
		return idInstagram;
	}

	public void setIdInstagram(Long idInstagram) {
		this.idInstagram = idInstagram;
	}

	public String getIdInstagramMedia() {
		return idInstagramMedia;
	}

	public void setIdInstagramMedia(String idInstagramMedia) {
		this.idInstagramMedia = idInstagramMedia;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}
}