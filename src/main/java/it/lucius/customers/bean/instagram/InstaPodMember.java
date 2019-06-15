package it.lucius.customers.bean.instagram;

import it.lucius.customers.models.instagram.pod.InstagramPodMember;

public class InstaPodMember {

	private int idPodMember;

	private InstaPodUser instagramPodUser;

	private InstaPodGroup instagramPodGroup;

	public InstaPodMember() {
		super();
	}

	public InstaPodMember(InstagramPodMember m) {
		super();
		this.idPodMember = m.getIdPodMember(); 
		this.instagramPodUser = new InstaPodUser(m.getInstagramPodUser()); 
		this.instagramPodGroup = new InstaPodGroup(m.getInstagramPodGroup());
	}

	public InstaPodUser getInstagramPodUser() {
		return instagramPodUser;
	}

	public void setInstagramPodUser(InstaPodUser instagramPodUser) {
		this.instagramPodUser = instagramPodUser;
	}

	public int getIdPodMember() {
		return idPodMember;
	}

	public void setIdPodMember(int idPodMember) {
		this.idPodMember = idPodMember;
	}

	public InstaPodGroup getInstagramPodGroup() {
		return instagramPodGroup;
	}

	public void setInstagramPodGroup(InstaPodGroup instagramPodGroup) {
		this.instagramPodGroup = instagramPodGroup;
	}
	
	
}
