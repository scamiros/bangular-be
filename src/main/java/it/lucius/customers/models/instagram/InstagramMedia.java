package it.lucius.customers.models.instagram;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the instagram_media database table.
 * 
 */
@Entity
@Table(name="instagram_media")
@NamedQuery(name="InstagramMedia.findAll", query="SELECT i FROM InstagramMedia i")
public class InstagramMedia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	private Date dtMedia;

	public Date getDtMedia() {
		return dtMedia;
	}

	public void setDtMedia(Date dtMedia) {
		this.dtMedia = dtMedia;
	}

	public String getUriVideo() {
		return uriVideo;
	}

	public void setUriVideo(String uriVideo) {
		this.uriVideo = uriVideo;
	}

	public InstagramMedia() {
	}

	public Integer getIdMedia() {
		return this.idMedia;
	}

	public void setIdMedia(Integer idMedia) {
		this.idMedia = idMedia;
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