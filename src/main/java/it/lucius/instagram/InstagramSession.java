package it.lucius.instagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 Copyright (c) 2012 Sola Ogunsakin

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
import it.lucius.instagram.auth.AccessToken;
import it.lucius.instagram.exception.InstagramException;
import it.lucius.instagram.io.APIMethod;
import it.lucius.instagram.io.DeleteMethod;
import it.lucius.instagram.io.GetMethod;
import it.lucius.instagram.io.PostMethod;
import it.lucius.instagram.io.RequestResponse;
import it.lucius.instagram.io.UriFactory;
import it.lucius.instagram.model.Comment;
import it.lucius.instagram.model.Location;
import it.lucius.instagram.model.Media;
import it.lucius.instagram.model.Relationship;
import it.lucius.instagram.model.Tag;
import it.lucius.instagram.model.User;
import it.lucius.instagram.util.UriConstructor;


/**
 * Constains a methods used to interact with the API.
 * 
 * @author Sola Ogunsakin
 * @version 2012-08-22
 */
public class InstagramSession {

	String accessToken;
	User currentUser;
	UriConstructor uriConstructor;

	public InstagramSession() {
	}

	/**
	 * Creates a new Instagram session
	 * 
	 * @param accessToken
	 *            the session's access token
	 */
	public InstagramSession(AccessToken accessToken) {
		setAccessToken(accessToken.getTokenString());
		// this.currentUser = sessionUser;
		this.uriConstructor = new UriConstructor(getAccessToken());
	}

	protected String getAccessToken() {
		return accessToken;
	}

	protected void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Finds and returns a user with the given id. Throws an InstagramException
	 * if none is found or the user with that id cannot be accessed
	 * 
	 * @param userId
	 *            id of the user
	 * @return The user with the id passed
	 */
	public User getUserById(int userId) throws InstagramException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);
		try {
			JSONObject userObject = (new GetMethod()
					.setMethodURI(uriConstructor.constructUri(
							UriFactory.Users.GET_DATA, map, true))).call()
					.getJSON();
			if (userObject.containsKey("data")) {
				return new User((JSONObject) userObject.get("data"),
						getAccessToken());
			} else {
				throw new InstagramException("User with id = " + userId
						+ " cannot be accessed" + " or may not exist");
			}
		} catch (InstagramException e) {
			throw new InstagramException(
					"User with id = "
							+ userId
							+ " cannot be accessed"
							+ " or may not exist. This user may have deleted their account");
		}
	}
	
	public User getUserSelf() throws InstagramException {
		GetMethod getMethod = new GetMethod();
		APIMethod api = getMethod.setMethodURI(uriConstructor.constructUri(UriFactory.Users.GET_SELF, null, true));
		
		JSONObject userObject = null;
		User user = null;
		try {
			RequestResponse response = api.call();
			userObject = response.getJSON();
			
			if (userObject.containsKey("data")) {
				user = new User((JSONObject) userObject.get("data"),
						getAccessToken());
			} else {
				throw new InstagramException("Error: no data found in json response");
			}
		} catch (InstagramException e) {
			System.out.println(e.getMessage());
		}
		
		return user;
	}

	/**
	 * Finds and returns the most recent media published by the user with the id
	 * passed. Results are paginated, the required page must also be indicated
	 * 
	 * @param userId
	 *            id of the user
	 * @param pageNumber
	 *            the required result page. Must be > 0.
	 * @throws InstagramException
	 * @return List of recent media published by the user, within the page
	 *         number passed
	 */
	public List<Media> getRecentPublishedMedia(Long userId, int pageNumber, boolean isLight)
			throws InstagramException {

		if (pageNumber <= 0) {
			throw new InstagramException(
					"The page number must be greater than 0");
		} else {
			JSONObject object = null;
			List<Media> media = new ArrayList<Media>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user_id", userId);
			String uriString = uriConstructor.constructUri(
					UriFactory.Users.GET_RECENT_MEDIA, map, true);

			for (int i = 0; i < pageNumber; i++) {
				object = (new GetMethod().setMethodURI(uriString)).call()
						.getJSON();
				if (((JSONObject) object.get("pagination")).containsKey("next_url"))
					uriString = (String) ((JSONObject) object.get("pagination"))
							.get("next_url");
				else
					break;
			}

			JSONArray mediaItems = (JSONArray) object.get("data");
			for (int i = 0; i < mediaItems.size(); i++) {
				if((JSONObject) mediaItems.get(i) != null)
					media.add(new Media((JSONObject) mediaItems.get(i), getAccessToken(), isLight));
			}
			return media;
		}
	}

	/**
	 * Gets the recent media in the current user's feed. Results are paginated,
	 * the required page must also be indicated.
	 * 
	 * @param pageNumber
	 *            the required result page. Must be > 0.
	 * @throws InstagramException
	 * @return List of recent media in the current user's feed
	 */
//	public ArrayList<Media> getFeed(int pageNumber) throws InstagramException {
//		if (pageNumber <= 0) {
//			throw new InstagramException(
//					"The page number must be greater than 0");
//		} else {
//			JSONObject object = null;
//			ArrayList<Media> media = new ArrayList<Media>();
//			String uriString = uriConstructor.constructUri(
//					UriFactory.Users.GET_FEED, null, true);
//			try {
//
//				for (int i = 0; i < pageNumber; i++) {
//					object = (new GetMethod().setMethodURI(uriString)).call()
//							.getJSON();
//					if (object.getJSONObject("pagination").has("next_url"))
//						uriString = object.getJSONObject("pagination")
//								.getString("next_url");
//					else
//						break;
//				}
//
//				JSONArray mediaItems = object.getJSONArray("data");
//				for (int i = 0; i < mediaItems.length(); i++) {
//					media.add(new Media(mediaItems.getJSONObject(i),
//							accessToken));
//				}
//			} catch (JSONException e) {
//				throw new InstagramException("JSON parsing error");
//			}
//			return media;
//		}
//
//	}

	/**
	 * Gets the recent media that the current user has liked. Results are
	 * paginated, the required page must also be indicated.
	 * 
	 * @param pageNumber
	 *            the required result page. Must be > 0.
	 * @throws InstagramException
	 * @return List of recent media that the current user has liked
	 */
//	

	/**
	 * Gets the media with the id passed. Throws an InstagramException if no
	 * media with that is is found.
	 * 
	 * @param mediaId
	 *            the id of the media to be returned
	 * @throws InstagramException
	 * @return The media with the id passed
	 */
	public Media getMedia(String mediaId) throws InstagramException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("media_id", mediaId);
		JSONObject object = (new GetMethod().setMethodURI(uriConstructor
				.constructUri(UriFactory.Media.GET_MEDIA, map, true)))
				.call().getJSON();
		return (new Media((JSONObject) object.get("data"), getAccessToken(), false));
	}

	/**
	 * Searches for media by location and creation time.
	 * 
	 * @param latitude
	 *            latitude of location
	 * @param longitude
	 *            longitude of location
	 * @param minTimestamp
	 *            the min timestamp of media to be returned. Can be null if
	 *            needed.
	 * @param maxTimestamp
	 *            the max timestamp of media to be returned. Can be null if
	 *            needed.
	 * @param distance
	 *            the of the location. Can be null if needed.
	 * @throws InstagramException
	 * @return List of recent media that meet the search parameters
	 */
//	public List<Media> searchMedia(Object latitude, Object longitude,
//			Object minTimestamp, Object maxTimestamp, Object distance)
//			throws InstagramException {
//		ArrayList<Media> media = new ArrayList<Media>();
//		String uri = UriFactory.Media.SEARCH_MEDIA + "?access_token="
//				+ getAccessToken() + "&lat=" + latitude + "&lng=" + longitude
//				+ "&min_timestamp=" + minTimestamp + "&max_timestamp="
//				+ maxTimestamp + "&distance=" + distance;
//		JSONObject object = (new GetMethod().setMethodURI(uri)).call()
//				.getJSON();
//		try {
//			JSONArray mediaItems = object.getJSONArray("data");
//			for (int i = 0; i < mediaItems.length(); i++) {
//				media.add(new Media(mediaItems.getJSONObject(i),
//						getAccessToken()));
//			}
//			return media;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}

	/**
	 * Finds and returns the most popular media on instagram.
	 * 
	 * @throws InstagramException
	 * @return List of the most popular media on instagram.
	 */
//	public List<Media> getPopularMedia() throws InstagramException {
//		JSONObject object = null;
//		ArrayList<Media> media = new ArrayList<Media>();
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Media.GET_POPULAR_MEDIA, null, true);
//
//		object = (new GetMethod().setMethodURI(uriString)).call().getJSON();
//
//		try {
//			JSONArray mediaItems = object.getJSONArray("data");
//			for (int i = 0; i < mediaItems.length(); i++) {
//				media.add(new Media(mediaItems.getJSONObject(i),
//						getAccessToken()));
//			}
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//		return media;
//	}

	/**
	 * Searches for users by name.
	 * 
	 * @param name
	 *            the full name or username of the user to be returned
	 * @throws InstagramException
	 * @return List of users who match the search criteria
	 */
//	public List<User> searchUsersByName(String name) throws InstagramException {
//		ArrayList<User> users = new ArrayList<User>();
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Users.SEARCH_USER_BY_NAME, null, true)
//				+ "&q="
//				+ name;
//		try {
//			JSONArray userObjects = (new GetMethod().setMethodURI(uriString))
//					.call().getJSON().getJSONArray("data");
//			for (int i = 0; i < userObjects.length(); i++) {
//				users.add(new User(userObjects.getJSONObject(i),
//						getAccessToken()));
//			}
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//		return users;
//	}

	/**
	 * Gets a list of users that the user, whose id is passed, follows. Results
	 * are paginated, the required page must also be indicated
	 * 
	 * @param userId
	 *            id of the user whose follow list is to be returned
	 * @param pageNumber
	 *            the required result page. Must be > 0.
	 * @throws InstagramException
	 * @return List of users by page, that the user, whose id is passed,
	 *         follows.
	 */
//	public List<User> getFollows(int userId, int pageNumber)
//			throws InstagramException {
//		if (pageNumber <= 0) {
//			throw new InstagramException(
//					"The page number must be greater than 0");
//		}
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("user_id", userId);
//		ArrayList<User> users = new ArrayList<User>();
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Relationships.GET_FOLLOWS, map, true);
//
//		try {
//			for (int i = 0; i < pageNumber; i++) {
//				object = (new GetMethod().setMethodURI(uriString)).call()
//						.getJSON();
//				if (object.getJSONObject("pagination").has("next_url"))
//					uriString = object.getJSONObject("pagination").getString(
//							"next_url");
//				else
//					break;
//			}
//
//			JSONArray userObjects = object.getJSONArray("data");
//			for (int i = 0; i < userObjects.length(); i++) {
//				users.add(new User(userObjects.getJSONObject(i),
//						getAccessToken()));
//			}
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//		return users;
//
//	}
//
//	public List<User> getFollowers(int userId, int pageNumber)
//			throws InstagramException {
//		if (pageNumber <= 0) {
//			throw new InstagramException(
//					"The page number must be greater than 0");
//		}
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("user_id", userId);
//		ArrayList<User> users = new ArrayList<User>();
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Relationships.GET_FOLLOWERS, map, true);
//		try {
//			for (int i = 0; i < pageNumber; i++) {
//				object = (new GetMethod().setMethodURI(uriString)).call()
//						.getJSON();
//				if (object.getJSONObject("pagination").has("next_url"))
//					uriString = object.getJSONObject("pagination").getString(
//							"next_url");
//				else
//					break;
//			}
//
//			JSONArray userObjects = object.getJSONArray("data");
//			for (int i = 0; i < userObjects.length(); i++) {
//				users.add(new User(userObjects.getJSONObject(i),
//						getAccessToken()));
//			}
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//		return users;
//	}
//
//	public List<User> getFollowRequests() throws InstagramException {
//		JSONObject object = null;
//		ArrayList<User> users = new ArrayList<User>();
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Relationships.GET_FOLLOW_REQUESTS, null, true);
//
//		object = (new GetMethod().setMethodURI(uriString)).call().getJSON();
//
//		JSONArray userObjects;
//		try {
//			userObjects = object.getJSONArray("data");
//			for (int i = 0; i < userObjects.length(); i++) {
//				users.add(new User(userObjects.getJSONObject(i),
//						getAccessToken()));
//			}
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//		return users;
//	}
//
//	public Relationship getRelationshipWith(int userId)
//			throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("user_id", userId);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Relationships.GET_RELATIONSHIP_STATUS, map, true);
//
//		object = (new GetMethod().setMethodURI(uriString)).call().getJSON();
//
//		try {
//			return new Relationship(object.getJSONObject("data"),
//					getAccessToken());
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}
//
//	public boolean modifyRelationship(int userId, Relationship.Action action)
//			throws InstagramException {
//		String actionString = "";
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("user_id", userId);
//		HashMap<String, Object> args = new HashMap<String, Object>();
//
//		switch (action) {
//		case BLOCK:
//			actionString = "block";
//			break;
//		case UNBLOCK:
//			actionString = "unblock";
//			break;
//		case APPROVE:
//			actionString = "approve";
//			break;
//		case DENY:
//			actionString = "deny";
//			break;
//		case FOLLOW:
//			actionString = "follow";
//			break;
//		case UNFOLLOW:
//			actionString = "unfollow";
//			break;
//		}
//
//		args.put("action", actionString);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Relationships.MUTATE_RELATIONSHIP, map, true);
//		object = (new PostMethod().setPostParameters(args)
//				.setMethodURI(uriString)).call().getJSON();
//		try {
//			return object.getJSONObject("meta").getInt("code") == 200;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}
//
//	public Comment postComment(String mediaId, String text)
//			throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("media_id", mediaId);
//		HashMap<String, Object> args = new HashMap<String, Object>();
//		args.put("text", text);
//		args.put("access_token", getAccessToken());
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Comments.POST_MEDIA_COMMENT, map, false);
//		object = (new PostMethod().setPostParameters(args)
//				.setMethodURI(uriString)).call().getJSON();
//		try {
//			return new Comment(object.getJSONObject("data"), getAccessToken());
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}
//
//	public boolean removeComment(String mediaId, String commentId)
//			throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("media_id", mediaId);
//		map.put("comment_id", commentId);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Comments.DELETE_MEDIA_COMMENT, map, true);
//		try {
//			object = (new DeleteMethod().setMethodURI(uriString)).call()
//					.getJSON();
//		} catch (InstagramException e) {
//			throw new InstagramException("Comment cannot be deleted");
//		}
//		try {
//			return object.getJSONObject("meta").getInt("code") == 200;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}
//
//	public boolean likeMedia(String mediaId) throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("media_id", mediaId);
//		HashMap<String, Object> args = new HashMap<String, Object>();
//		args.put("access_token", getAccessToken());
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Likes.SET_LIKE, map, false);
//		object = (new PostMethod().setPostParameters(args)
//				.setMethodURI(uriString)).call().getJSON();
//		try {
//			return object.getJSONObject("meta").getInt("code") == 200;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}
//
//	public boolean removeMediaLike(String mediaId) throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("media_id", mediaId);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Likes.REMOVE_LIKE, map, true);
//		object = (new DeleteMethod().setMethodURI(uriString)).call().getJSON();
//		try {
//			return object.getJSONObject("meta").getInt("code") == 200;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}
//
//	public Tag getTag(String tagName) throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("tag_name", tagName);
//		String uriString = uriConstructor.constructUri(UriFactory.Tags.GET_TAG,
//				map, true);
//		object = (new GetMethod().setMethodURI(uriString)).call().getJSON();
//		try {
//			return new Tag(object.getJSONObject("data"), getAccessToken());
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}

//	public List<Media> getRecentMediaForTag(String tagName, int pageNumber)
//			throws InstagramException {
//		if (pageNumber <= 0) {
//			throw new InstagramException(
//					"The page number must be greater than 0");
//		}
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("tag_name", tagName);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Tags.GET_RECENT_TAGED_MEDIA, map, true);
//
//		try {
//			for (int i = 0; i < pageNumber; i++) {
//				object = (new GetMethod().setMethodURI(uriString)).call()
//						.getJSON();
//				if (object.getJSONObject("pagination").has("next_url"))
//					uriString = object.getJSONObject("pagination").getString(
//							"next_url");
//				else
//					break;
//			}
//
//			ArrayList<Media> media = new ArrayList<Media>();
//			JSONArray mediaItems = object.getJSONArray("data");
//			for (int i = 0; i < mediaItems.length(); i++) {
//				media.add(new Media(mediaItems.getJSONObject(i),
//						getAccessToken()));
//			}
//			return media;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}

//	public List<Tag> searchTags(String tagName) throws InstagramException {
//		JSONObject object = null;
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Tags.SEARCH_TAGS, null, true) + "&q=" + tagName;
//		object = (new GetMethod().setMethodURI(uriString)).call().getJSON();
//		ArrayList<Tag> tags = new ArrayList<Tag>();
//		try {
//			JSONArray tagItems = object.getJSONArray("data");
//			for (int i = 0; i < tagItems.length(); i++) {
//				tags.add(new Tag(tagItems.getJSONObject(i), getAccessToken()));
//			}
//			return tags;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}

//	public Location getLocation(int locationId) throws InstagramException {
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("location_id", locationId);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Locations.GET_LOCATION, map, true);
//		object = (new GetMethod().setMethodURI(uriString)).call().getJSON();
//		try {
//			return new Location(object.getJSONObject("data"), getAccessToken());
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}

//	public List<Media> getRecentMediaFromLocation(int locationId, int pageNumber)
//			throws InstagramException {
//		if (pageNumber <= 0) {
//			throw new InstagramException(
//					"The page number must be greater than 0");
//		}
//		JSONObject object = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("location_id", locationId);
//		String uriString = uriConstructor.constructUri(
//				UriFactory.Locations.GET_MEDIA_FROM_LOCATION, map, true);
//
//		try {
//			for (int i = 0; i < pageNumber; i++) {
//				object = (new GetMethod().setMethodURI(uriString)).call()
//						.getJSON();
//				if (object.getJSONObject("pagination").has("next_url"))
//					uriString = object.getJSONObject("pagination").getString(
//							"next_url");
//				else
//					break;
//			}
//			ArrayList<Media> media = new ArrayList<Media>();
//			JSONArray mediaItems = object.getJSONArray("data");
//			for (int i = 0; i < mediaItems.length(); i++) {
//				media.add(new Media(mediaItems.getJSONObject(i),
//						getAccessToken()));
//			}
//			return media;
//		} catch (JSONException e) {
//			throw new InstagramException("JSON parsing error");
//		}
//	}

}
