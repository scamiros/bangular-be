package it.lucius.instagram.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import it.lucius.instagram.exception.InstagramException;

public abstract class APIMethod {
	String methodUri;
	String type;
	String accessToken;

	abstract protected InputStream performRequest();
	
	public APIMethod() {}

	public RequestResponse call() throws InstagramException {
		String line = "", chunk;
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(performRequest()));
			
			while ((chunk = rd.readLine()) != null) 
				line += chunk;
				//System.out.println(line);
		} catch (Throwable e) {
			 throw new InstagramException(e.getMessage());
		}
		return new RequestResponse(line);
	}
	
	/*
	System.out.println(line);
	obj = new JSONObject(new JSONTokener(line));
	if ((obj.has("meta") && obj.getJSONObject("meta").getInt("code") != 200)
			|| (obj.has("code") && obj.getInt("code") != 200)) {
		throw new InstagramException("\nUri => " + getMethodUri()
				+ "\nResponse => " + obj.toString());
	}
throw new InstagramException("JSON Parsing Error\nUri => "
					+ getMethodUri() + "\nResponse => " + obj.toString());
	*/
	public String getType() {
		return type;
	}

	public String getMethodUri() {
		return methodUri;
	}

	public APIMethod setMethodURI(String methodURI) {
		this.methodUri = methodURI;
		return this;
	}
}