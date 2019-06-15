package it.lucius.customers.models.instagram;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the insagram_carousel database table.
 * 
 */
@Entity
@Table(name="instagram_image")
@NamedQuery(name="InstagramImage.findAll", query="SELECT i FROM InstagramImage i")
public class InstagramImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idImage;
	
	private int offSlide;
	
	private String uri;

	//bi-directional many-to-one association to InstagramMedia
	@ManyToOne
	@JoinColumn(name="idMedia")
	private InstagramMedia instagramMedia;

	public InstagramImage() {
	}

	public int getIdImage() {
		return idImage;
	}

	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}


	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public InstagramMedia getInstagramMedia() {
		return this.instagramMedia;
	}

	public void setInstagramMedia(InstagramMedia instagramMedia) {
		this.instagramMedia = instagramMedia;
	}

	public int getOffSlide() {
		return offSlide;
	}

	public void setOffSlide(int offSlide) {
		this.offSlide = offSlide;
	}

}