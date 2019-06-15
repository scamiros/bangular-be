package it.lucius.customers.bean.instagram;

import java.util.List;

public class InstagramPodGroupBean {

	private InstaPodGroup group;
	private List<InstaPodMember> members;
	private List<InstaPodContent> contents;
	
	public InstaPodGroup getGroup() {
		return group;
	}
	public void setGroup(InstaPodGroup group) {
		this.group = group;
	}
	public List<InstaPodMember> getMembers() {
		return members;
	}
	public void setMembers(List<InstaPodMember> members) {
		this.members = members;
	}
	public List<InstaPodContent> getContents() {
		return contents;
	}
	public void setContents(List<InstaPodContent> contents) {
		this.contents = contents;
	}
}
