package it.lucius.customers.models.instagram.pod;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the instagram_pod_action database table.
 * 
 */
@Entity
@Table(name="instagram_pod_action")
@NamedQuery(name="InstagramPodAction.findAll", query="SELECT i FROM InstagramPodAction i")
public class InstagramPodAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pod_action")
	private Integer idPodAction;

	@Column(name="comment_")
	private Integer comment;

	@Column(name="like_")
	private Integer like;

	//bi-directional many-to-one association to InstagramPodContent
	@ManyToOne
	@JoinColumn(name="id_pod_content")
	private InstagramPodContent instagramPodContent;

	//bi-directional many-to-one association to InstagramPodMember
	@ManyToOne
	@JoinColumn(name="id_pod_member")
	private InstagramPodMember instagramPodMember;

	public Integer getIdPodAction() {
		return this.idPodAction;
	}

	public void setIdPodAction(Integer idPodAction) {
		this.idPodAction = idPodAction;
	}

	public Integer getComment() {
		return this.comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public Integer getLike() {
		return this.like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public InstagramPodContent getInstagramPodContent() {
		return this.instagramPodContent;
	}

	public void setInstagramPodContent(InstagramPodContent instagramPodContent) {
		this.instagramPodContent = instagramPodContent;
	}

	public InstagramPodMember getInstagramPodMember() {
		return this.instagramPodMember;
	}

	public void setInstagramPodMember(InstagramPodMember instagramPodMember) {
		this.instagramPodMember = instagramPodMember;
	}

}