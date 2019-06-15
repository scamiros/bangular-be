package it.lucius.instagram.io;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class RequestResponse {
	
	String responseString;
	public RequestResponse(String responseAsString) {
		this.responseString = responseAsString;
	}
	
	public JSONObject getJSON() {
		
		JSONObject res = null;
		res = (JSONObject) JSONValue.parse(getResponseString());
		return res;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
}
