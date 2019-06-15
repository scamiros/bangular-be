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

import it.lucius.customers.models.User;


/**
 * The persistent class for the instagram_pod_group database table.
 * 
 */
@Entity
@Table(name="instagram_pod_group")
@NamedQuery(name="InstagramPodGroup.findAll", query="SELECT i FROM InstagramPodGroup i")
public class InstagramPodGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pod_group")
	private int idPodGroup;

	@Column(name="group_name")
	private String groupName;

	@Column(columnDefinition = "TINYINT(4)")
    private Boolean enabled;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	public InstagramPodGroup() {
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public int getIdPodGroup() {
		return this.idPodGroup;
	}

	public void setIdPodGroup(int idPodGroup) {
		this.idPodGroup = idPodGroup;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}