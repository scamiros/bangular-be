package it.lucius.customers.bean.instagram;

import it.lucius.customers.models.instagram.pod.InstagramPodGroup;

public class InstaPodGroup {

	private int idPodGroup;
	private String groupName;
	private Boolean enabled;
	
	public InstaPodGroup() {
		super();
	}

	public InstaPodGroup(InstagramPodGroup g) {
		super();
		this.idPodGroup = g.getIdPodGroup();
		this.groupName = g.getGroupName();
		this.enabled = g.getEnabled();
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public int getIdPodGroup() {
		return idPodGroup;
	}
	public void setIdPodGroup(int idPodGroup) {
		this.idPodGroup = idPodGroup;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
