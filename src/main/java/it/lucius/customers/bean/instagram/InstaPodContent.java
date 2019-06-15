package it.lucius.customers.bean.instagram;

import java.sql.Timestamp;
import java.util.List;

import it.lucius.customers.models.instagram.pod.InstagramPodContent;

public class InstaPodContent {

	private int idPodContent;
	
	private String contentUri;

	private Timestamp dtPosted;

	private InstaPodMember instagramPodMember;
	
	private List<InstaPodAction> actions;
	
	private int likes;
	
	private int comments;

	public InstaPodContent() {
		super();
	}

	public InstaPodContent(InstagramPodContent c) {
		super();
		this.idPodContent = c.getIdPodContent();
		this.dtPosted = c.getDtPosted();
		this.contentUri = c.getContentUri();
		this.instagramPodMember = new InstaPodMember(c.getInstagramPodMember());
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public List<InstaPodAction> getActions() {
		return actions;
	}

	public void setActions(List<InstaPodAction> actions) {
		this.actions = actions;
	}

	public String getContentUri() {
		return contentUri;
	}

	public void setContentUri(String contentUri) {
		this.contentUri = contentUri;
	}

	public int getIdPodContent() {
		return idPodContent;
	}

	public void setIdPodContent(int idPodContent) {
		this.idPodContent = idPodContent;
	}

	public Timestamp getDtPosted() {
		return dtPosted;
	}

	public void setDtPosted(Timestamp dtPosted) {
		this.dtPosted = dtPosted;
	}

	public InstaPodMember getInstagramPodMember() {
		return instagramPodMember;
	}

	public void setInstagramPodMember(InstaPodMember instagramPodMember) {
		this.instagramPodMember = instagramPodMember;
	}

}
