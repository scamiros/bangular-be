package it.lucius.customers.models.instagram.pod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the instagram_pod_member database table.
 * 
 */
@Entity
@Table(name="instagram_pod_member")
@NamedQuery(name="InstagramPodMember.findAll", query="SELECT i FROM InstagramPodMember i")
public class InstagramPodMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pod_member")
	private int idPodMember;

	@ManyToOne
	@JoinColumn(name="id_pod_user")
	private InstagramPodUser instagramPodUser;

	//bi-directional many-to-one association to InstagramPodGroup
	@ManyToOne
	@JoinColumn(name="id_pod_group")
	private InstagramPodGroup instagramPodGroup;

	public InstagramPodMember() {
	}

	public int getIdPodMember() {
		return this.idPodMember;
	}

	public void setIdPodMember(int idPodMember) {
		this.idPodMember = idPodMember;
	}

	public InstagramPodUser getInstagramPodUser() {
		return instagramPodUser;
	}

	public void setInstagramPodUser(InstagramPodUser instagramPodUser) {
		this.instagramPodUser = instagramPodUser;
	}

	public InstagramPodGroup getInstagramPodGroup() {
		return this.instagramPodGroup;
	}

	public void setInstagramPodGroup(InstagramPodGroup instagramPodGroup) {
		this.instagramPodGroup = instagramPodGroup;
	}

}