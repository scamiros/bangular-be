package it.lucius.customers.bean.instagram;

import it.lucius.customers.models.instagram.InstagramImage;

public class InstagramImageBean {

	private int idImage;
	
	private int offSlide;
	
	private String uri;

	public InstagramImageBean(InstagramImage im) {
		this.idImage = im.getIdImage();
		this.offSlide = im.getOffSlide();
		this.uri = im.getUri();
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

	public int getOffSlide() {
		return offSlide;
	}

	public void setOffSlide(int offSlide) {
		this.offSlide = offSlide;
	}

}