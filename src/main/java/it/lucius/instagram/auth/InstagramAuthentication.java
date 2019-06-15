package it.lucius.instagram.auth;

import it.lucius.instagram.InstagramSession;
import it.lucius.instagram.auth.AccessToken;
import it.lucius.instagram.exception.InstagramException;
import it.lucius.instagram.io.PostMethod;
import it.lucius.instagram.io.UriFactory;
import it.lucius.instagram.model.User;
import it.lucius.instagram.util.UriConstructor;

import java.util.HashMap;

import org.json.simple.JSONObject;


public class InstagramAuthentication {
	String redirectUri;
	String clientId;
	String clientSecret;
	AccessToken accessToken;
	User sessionUser;

	protected String getRedirectUri() {
		return redirectUri;
	}

	public InstagramAuthentication setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
		return this;
	}

	protected String getClientId() {
		return clientId;
	}

	public InstagramAuthentication setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public String getAuthorizationUri() throws InstagramException {
		if (getClientId() == null || getRedirectUri() == null) {
			throw new InstagramException("Please make sure that the " + "clientId and redirectUri fields are set");
		}
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("client_id", getClientId());
		args.put("redirect_uri", getRedirectUri());
		args.put("response_type", "token");
		// args.put("scope", "likes+comments+relationships+basic");
		String uri = (new UriConstructor()).constructUri(UriFactory.Auth.USER_AUTHORIZATION, args, false);
		return uri;
	}

	public AccessToken build(String code) throws InstagramException {
		if (getClientSecret() == null || getClientId() == null || getRedirectUri() == null) {
			throw new InstagramException(
					"Please make sure that the" + "clientId, clientSecret and redirectUri fields are set");
		}
		HashMap<String, Object> postArgs = new HashMap<String, Object>();
		postArgs.put("client_id", getClientId());
		postArgs.put("client_secret", getClientSecret());
		postArgs.put("grant_type", "authorization_code");
		postArgs.put("redirect_uri", getRedirectUri());
		postArgs.put("code", code);

		JSONObject response = (new PostMethod().setPostParameters(postArgs)
				.setMethodURI(UriFactory.Auth.GET_ACCESS_TOKEN_INTERNAL)).call().getJSON();

		try {
			setAccessToken(new AccessToken((String) response.get("access_token")));
			setSessionUser(new User((JSONObject) response.get("user"), getAccessToken().getTokenString()));
		} catch (Exception e) {
			throw new InstagramException("JSON parsing error");
		}
		return getAccessToken();
	}

	public AccessToken getAccessToken() throws InstagramException {
		if (accessToken == null)
			throw new InstagramException(
					"Token has not been fetched, please call build(String code) " + "before calling getAccessToken()");
		else
			return accessToken;
	}

	protected void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public User getAuthenticatedUser() throws InstagramException {
		if (accessToken == null)
			throw new InstagramException("No user has been authenticated yet");
		else
			return sessionUser;
	}

	protected void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	protected String getClientSecret() {
		return clientSecret;
	}

	public InstagramAuthentication setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

}
