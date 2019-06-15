package it.lucius.customers.models.instagram.pod;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the instagram_pod_content database table.
 * 
 */
@Entity
@Table(name="instagram_pod_content")
@NamedQuery(name="InstagramPodContent.findAll", query="SELECT i FROM InstagramPodContent i")
public class InstagramPodContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pod_content")
	private Integer idPodContent;

	@Column(name="dt_posted")
	private Timestamp dtPosted;

	@Column(name="content_uri")
	private String contentUri;
	
	//bi-directional many-to-one association to InstagramPodMember
	@ManyToOne
	@JoinColumn(name="id_pod_member")
	private InstagramPodMember instagramPodMember;

	public InstagramPodContent() {
	}

	public String getContentUri() {
		return contentUri;
	}

	public void setContentUri(String contentUri) {
		this.contentUri = contentUri;
	}

	public Integer getIdPodContent() {
		return this.idPodContent;
	}

	public void setIdPodContent(Integer idPodContent) {
		this.idPodContent = idPodContent;
	}

	public Timestamp getDtPosted() {
		return this.dtPosted;
	}

	public void setDtPosted(Timestamp dtPosted) {
		this.dtPosted = dtPosted;
	}

	public InstagramPodMember getInstagramPodMember() {
		return instagramPodMember;
	}

	public void setInstagramPodMember(InstagramPodMember instagramPodMember) {
		this.instagramPodMember = instagramPodMember;
	}

}