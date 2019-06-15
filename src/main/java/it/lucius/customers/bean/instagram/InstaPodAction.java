package it.lucius.customers.bean.instagram;

import it.lucius.customers.models.instagram.pod.InstagramPodAction;

public class InstaPodAction {

	private int idPodAction;

	private int comment;

	private int like;

	private InstaPodContent instagramPodContent;

	private InstaPodMember instagramPodMember;

	
	public InstaPodAction() {
		super();
	}

	public InstaPodAction(InstagramPodAction a) {
		super();
		this.idPodAction = a.getIdPodAction();
		this.comment = a.getComment();
		this.like = a.getLike();
		this.instagramPodMember = new InstaPodMember(a.getInstagramPodMember());
		this.instagramPodContent = new InstaPodContent(a.getInstagramPodContent());
	}

	public int getIdPodAction() {
		return idPodAction;
	}

	public void setIdPodAction(int idPodAction) {
		this.idPodAction = idPodAction;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public InstaPodContent getInstagramPodContent() {
		return instagramPodContent;
	}

	public void setInstagramPodContent(InstaPodContent instagramPodContent) {
		this.instagramPodContent = instagramPodContent;
	}

	public InstaPodMember getInstagramPodMember() {
		return instagramPodMember;
	}

	public void setInstagramPodMember(InstaPodMember instagramPodMember) {
		this.instagramPodMember = instagramPodMember;
	}
}
