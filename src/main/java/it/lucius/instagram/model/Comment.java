package it.lucius.instagram.model;

import org.json.simple.JSONObject;

import it.lucius.instagram.exception.InstagramException;

public class Comment extends InstagramModel {
	String createdTimestamp;
	String text;
	String sender;
	String id;

	public Comment(JSONObject obj, String accessToken)
			throws InstagramException {
		super(obj, accessToken);
		setCreatedTimestamp((String) obj.get("created_time"));
		setText((String) obj.get("text"));
		setId((String) obj.get("id"));
		JSONObject ob = (JSONObject) obj.get("from");
		setSender((String) ob.get("username"));
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	protected void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getText() {
		return text;
	}

	protected void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	protected void setSender(String sender) {
		this.sender = sender;
	}

    /**
     * Checks if two comment objects are equal
     * @param o The object to be compared 
     * @return True of the two objects are equal, false otherwise
     */
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(o.getClass() != this.getClass()) return false;
		return ((Comment)o).getId().equals(getId());
	}
}