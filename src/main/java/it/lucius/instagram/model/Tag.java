package it.lucius.instagram.model;

import org.json.simple.JSONObject;

import it.lucius.instagram.exception.InstagramException;


public class Tag extends InstagramModel {

	int mediaCount;
	String name;

	public Tag(JSONObject obj, String accessToken) throws InstagramException {
		super(obj, accessToken);

		setName((String) obj.get("name"));
		setMediaCount((Integer) obj.get("media_count"));

	}

	protected void setMediaCount(int mediaCount) {
		this.mediaCount = mediaCount;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public int getMediaCount() {
		return mediaCount;
	}

	public String getName() {
		return name;
	}

}
