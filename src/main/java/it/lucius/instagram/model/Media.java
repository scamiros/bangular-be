package it.lucius.instagram.model;

import java.text.SimpleDateFormat;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.lucius.instagram.exception.InstagramException;
import it.lucius.instagram.io.GetMethod;
import it.lucius.instagram.io.UriFactory;
import it.lucius.instagram.util.UriConstructor;

/**
 * Object for a piece of Media
 * with the JSON representation
 * <pre>{
 * 			"attribution":null,
 * 			"tags":["", ""],
 * 			"type":"image",
 * 			"location":null,
 * 			"comments": {
 * 				"count":0,
 * 				"data":[ {
 * 							"username":"",
 * 							"profile_picture":"",
 * 							"id":"",
 * 							"full_name":""
 * 						} ]
 * 			},
 * 			"filter":"",
 * 			"created_time":"",
 * 			"link":"",
 * 			"likes": {
 * 				"count": 0,
 * 				"data":[ {
 * 							"username":"",
 * 							"profile_picture":"",
 * 							"id":"",
 * 							"full_name":""
 * 						} ],
 * 			},
 * 			"images": {
 * 				"low_resolution": {
 * 					"url":"",
 * 					"width":0,
 * 					"height":0
 * 				},
 * 				"thumbnail": {
 * 					"url":"",
 * 					"width":0,
 * 					"height":0
 * 				},
 * 				"standard_resolution":{
 * 					"url":"",
 * 					"width":0,"height":0}
 * 				},
 * 			"caption": { 
 * 				"created_time":"0",
 * 				"text":"",
 * 				"from": {
 * 					"username":"",
 * 					"profile_picture":"",
 * 					"id":"",
 * 					"full_name":""
 * 				},
 * 				"id":""
 * 			},
 * 			"user_has_liked":false,
 * 			"id":"",
 * 			"user": {
 * 				"username":"",
 * 				"website":"",
 * 				"bio":"",
 * 				"profile_picture":"",
 * 				"full_name":"",
 * 				"id":""
 * 			}
 * }</pre>
 * @author Sola Ogunsakin
 * @version 2012-08-22
 */
public class Media extends InstagramModel {

	/** 
	 * Types of image filters
	 */
	public static enum Filters {
		TOASTER, HUDSON, SIERRA, INKWELL, NORMAL, AMARO, RISE, VALENCIA
	}
	
	/** 
	 * Type of Media
	 */
	protected String type;
	
	/** 
	 * The image filter
	 */
	protected String filter;
	
	/** 
	 * The link for this media
	 */
	protected String link;
	
	/** 
	 * List of tags used in this media
	 */
	protected List<String> tags;

	/** 
	 * Low resolution image version of the media's image
	 */
	protected Image lowResolutionImage;

	/** 
	 * Thumbnail resolution image version of the media's image
	 */
	protected Image thumbnailImage;
	
	/**
	 * Standard resolution version of the media's image
	 */
	protected Image standardResolutionImage;
	
	/**
	 * List of lazyloaded Comment objects for this media 
	 */
	protected List<Comment> comments;
	
	/**
	 * List of lazyloaded User object for users who
	 * represents liked the media
	 */
	//protected List<User> likers;
	
	/**
	 * User who created this media
	 */
	//protected User user;
	
	/**
	 * Location object representing where this media was created
	 */
	protected Location location = null;
	
	/**
	 * The creation timestamp as a string
	 */
	protected String createdTimestamp;
	
	/**
	 * Id of this media as a string
	 */
	protected String id;
	
	/**
	 * Caption object representing this media's caption
	 */
	protected Caption caption;
	
	/**
	 * Boolean indicating if the current user has liked this media
	 */
	protected Boolean userHasLikedMedia;
	
	public Date createdDate;

	/**
	 * Used to construct formatted api urls
	 */
	UriConstructor uriConstructor;
	
	protected Video standardResolution;
	
	public List<Image> carousel;
	Long commentsCount;
	Long likesCount;
	
    public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	/**
     * Makes a new Media object out of a JSONObject
     * @param obj json object used to create this media
     * @param accessToken API access token used for lazyloaded api requests
     * @throws InstagramException
     */
	public Media(JSONObject obj, String accessToken, boolean isLight) throws InstagramException {
		super(obj, accessToken);
		uriConstructor = new UriConstructor(getAccessToken());
			
		if(isLight) {
			this.setId((String) obj.get("id"));
			this.setCreatedTimestamp((String) obj.get("created_time"));
			JSONObject images = (JSONObject) obj.get("images");
		 	this.setLowResolutionImage(this.new Image((JSONObject) images.get("low_resolution")));
		 	if(obj.containsKey("comments")) {
				JSONObject comments = (JSONObject) obj.get("comments");
				this.setCommentsCount((Long) comments.get("count"));
			}
			
			if(obj.containsKey("likes")) {
				JSONObject likes = (JSONObject) obj.get("likes");
				this.setLikesCount((Long) likes.get("count"));
			}
		 	
		} else {
			if(obj.containsKey("caption")) 
				this.setCaption(this.new Caption((JSONObject) obj.get("caption")));			
			this.setId((String) obj.get("id"));
			this.setCreatedTimestamp((String) obj.get("created_time"));
			this.setFilter((String) obj.get("filter"));
			this.setLink((String) obj.get("link"));
			this.setType((String) obj.get("type"));
			this.setUserHasLikedMedia((Boolean) obj.get("user_has_liked"));
			
			if(obj.containsKey("comments")) {
				JSONObject comments = (JSONObject) obj.get("comments");
				this.setCommentsCount((Long) comments.get("count"));
			}
			
			if(obj.containsKey("likes")) {
				JSONObject likes = (JSONObject) obj.get("likes");
				this.setLikesCount((Long) likes.get("count"));
			}
			
		 	JSONObject images = (JSONObject) obj.get("images");
		 	this.setLowResolutionImage(this.new Image((JSONObject) images.get("low_resolution")));
		 	this.setThumbnailImage(this.new Image((JSONObject) images.get("thumbnail")));
		 	this.setStandardResolutionImage(this.new Image((JSONObject) images.get("standard_resolution")));
			
		 	if(this.getType().equalsIgnoreCase("video")) {
			 	
			 	this.setStandardResolution(this.new Video((JSONObject)((JSONObject) obj.get("videos")).get("standard_resolution")));
		 	
		 	} else if(this.getType().equalsIgnoreCase("carousel")) {
		 		
		 		ArrayList<Image> carousel = new ArrayList<Image>();
				JSONArray carouselObjs = (JSONArray) obj.get("carousel_media");
				for(int i = 0; i < carouselObjs.size(); i++) {
					
					JSONObject imagesC = (JSONObject) carouselObjs.get(i);
					JSONObject image = (JSONObject) imagesC.get("images");
					
					if(i == 0) {
						this.setLowResolutionImage(this.new Image((JSONObject) image.get("low_resolution")));
					 	this.setThumbnailImage(this.new Image((JSONObject) image.get("thumbnail")));
					 	this.setStandardResolutionImage(this.new Image((JSONObject) image.get("standard_resolution")));
					}
				 	
					Image im = new Image((JSONObject) image.get("standard_resolution"));
					carousel.add(im);
				}
				this.carousel = carousel;
				
				this.setTags(tags);
		 	}
		 	
			if(obj.containsKey("location") && obj.get("location") != null)
				this.setLocation(new Location((JSONObject) obj.get("location"), accessToken));
			
			ArrayList<String> tags = new ArrayList<String>();
			JSONArray tagStrings = (JSONArray) obj.get("tags");
			for(int i = 0; i < tagStrings.size(); i++) {
				tags.add((String) tagStrings.get(i));
			}
			
			this.setTags(tags);
			//this.comments = getComments();
		}
	}
	
    
	public List<Image> getCarousel() {
		return carousel;
	}


	public void setCarousel(List<Image> carousel) {
		this.carousel = carousel;
	}


	public Long getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Long commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	/**
     * Returns the type of this media
     * @return The type of this media 
     */
	public String getType() {
		return type;
	}

	protected void setType(String type) {
		this.type = type;
	}

    /**
     * Returns the type filter for this media's image
     * @return The type filter for this media's image 
     */
	public String getFilter() {
		return filter;
	}


	protected void setFilter(String filter) {
		this.filter = filter;
	}

    /**
     * Returns the Caption object representing this media's caption
     * @return The Caption object representing this media's caption 
     */
	public Caption getCaption() {
		return caption;
	}

    /**
     * Returns the url link to this media
     * @return The url link to this media 
     */
	public String getLink() {
		return link;
	}


	protected void setLink(String link) {
		this.link = link;
	}

    /**
     * Lazy-loads and returns a list of comments for this media
     * @return A list of lazy-loaded comments for this media 
     */
	
    /**
     * Returns the User object of this media's creator
     * @return The User object of this media's creator 
     */
//	public User getUser() {
//		return user;
//	}
//
//
//	protected void setUser(User user) {
//		this.user = user;
//	}

    /**
     * Returns the Location object representing location that this 
     * media was created
     * @return Location object representing location that this 
     * media was created 
     */
	public Location getLocation() {
		return location;
	}


	public Video getStandardResolution() {
		return standardResolution;
	}

	public void setStandardResolution(Video standardResolution) {
		this.standardResolution = standardResolution;
	}

	protected void setLocation(Location location) {
		this.location = location;
	}

    /**
     * Indicated whether the current user has liked this media
     * @return a boolean indicating whether the current user 
     * has liked this media
     */
	public Boolean userHasLikedMedia() {
		return userHasLikedMedia;
	}

	protected void setUserHasLikedMedia(Boolean userHasLikedMedia) {
		this.userHasLikedMedia = userHasLikedMedia;
	}
	
    /**
     * Returns this media's creation timestamp a string
     * @return This media's creation timestamp a string
     */
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}


	protected void setCreatedTimestamp(String createdTimestamp) {
		
		SimpleDateFormat formatPast = new SimpleDateFormat("dd MMMM yyyy");
		SimpleDateFormat formatRecent = new SimpleDateFormat("dd MMMM");
		
		Date dt = new Date(Long.valueOf(createdTimestamp) * 1000);
		Calendar calDt = Calendar.getInstance();
		calDt.setTime(dt);
		
		this.createdDate = dt;
		Date now = new Date();
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(now);
		
		if(calNow.get(Calendar.YEAR) == calDt.get(Calendar.YEAR))
			this.createdTimestamp = StringUtils.upperCase(formatRecent.format(dt));
		else
			this.createdTimestamp = StringUtils.upperCase(formatPast.format(dt));
	}

    /**
     * Returns the id of this media
     * @return The id of this media
     */
	public String getId() {
		return id;
	}


	protected void setId(String id) {
		this.id = id;
	}

    /**
     * Returns the low resolution image for this media
     * @return The low resolution image for this media
     */
	public Image getLowResolutionImage() {
		return lowResolutionImage;
	}


	protected void setLowResolutionImage(Image lowResolutionImage) {
		this.lowResolutionImage = lowResolutionImage;
	}

    /**
     * Returns the thumbnail image for this media
     * @return The thumbnail image for this media
     */
	public Image getThumbnailImage() {
		return thumbnailImage;
	}


	protected void setThumbnailImage(Image thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

    /**
     * Returns the standard resolution image for this media
     * @return The standard resolution image for this media
     */
	public Image getStandardResolutionImage() {
		return standardResolutionImage;
	}


	protected void setStandardResolutionImage(Image standardResolutionImage) {
		this.standardResolutionImage = standardResolutionImage;
	}


	protected void setCaption(Caption caption) {
		this.caption = caption;
	}
	
    /**
     * Lazy-Loads and returns a list of users who have liked this media
     * @return A lazy-loaded list of users who have liked this media
     */
	

    /**
     * Returns a list of tags (as strings) used in this media
     * @return A list of tags (as strings) used in this media
     */
	public List<String> getTags() {
		return tags;
	}

	protected void setTags(List<String> tags) {
		this.tags = tags;
	}

    /**
     * Checks if two media objects are equal
     * @param o The object to be compared 
     * @return True of the two objects are equal, false otherwise
     */
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(o.getClass() != this.getClass()) return false;
		return ((Media)o).getId().equals(getId());
	}
	
	/**
	 * Object for a media image
	 * with the JSON representation
	 * <pre>
	 * 		{
	 * 			"url":"",
	 * 			"width":0,
	 * 			"height":0
	 * 		}
	 * </pre>
	 * @author Sola Ogunsakin
	 * @version 2012-08-22
	 */
	public class Image {
		/**
		 * Link to this image
		 */
		String uri;
		
		/**
		 * Width of this image
		 */
		int width;
		
		/**
		 * Height of this image
		 */
		int heigth;
	
	    /**
	     * Makes a new Image object out of a JSONObject
	     * @param obj json object used to create this image
	     * @throws InstagramException
	     */
		public Image(JSONObject obj) {
			this.setUri((String) obj.get("url"));
//			this.setWidth((Long) obj.get("width"));
//			this.setHeigth((Long) obj.get("height"));
		}
		
	    /**
	     * Returns the url link to this image
	     * @return The url link to this image 
	     */
		public String getUri() {
			return uri;
		}

	    /**
	     * Sets this image's url
	     * @param url url for this image 
	     */
		protected void setUri(String uri) {
			this.uri = uri;
		}
	
	    /**
	     * Returns the width of this image
	     * @return The width of this image 
	     */
		public int getWidth() {
			return width;
		}
		
	    /**
	     * Sets this image's width
	     * @param width width of this image 
	     */
		protected void setWidth(int width) {
			this.width = width;
		}
		
	    /**
	     * Returns the height of this image
	     * @return The height of this image 
	     */
		public int getHeigth() {
			return heigth;
		}
		
	    /**
	     * Sets this image's height
	     * @param height height of this image 
	     */
		protected void setHeigth(int heigth) {
			this.heigth = heigth;
		}

	    /**
	     * Checks if two image objects are equal
	     * @param o The object to be compared 
	     * @return True of the two objects are equal, false otherwise
	     */
		public boolean equals(Object o) {
			if(o == null) return false;
			if(o == this) return true;
			if(o.getClass() != this.getClass()) return false;
			return ((Media.Image)o).getUri().equals(getUri());
		}
	}
	
	/**
	 * Object for a media caption
	 * with the JSON representation
	 * <pre>
	 *	{ 
	 * 		"created_time":"",
	 * 		"text":"",
	 * 		"from": {
	 * 			"username":"",
	 * 			"profile_picture":"",
	 * 			"id":"",
	 * 			"full_name":""
	 * 		},
	 * 		"id":""
	 * 	}
	 * </pre>
	 * @author Sola Ogunsakin
	 * @version 2012-08-22
	 */
	public class Caption {

		/**
		 * Caption text
		 */
		String text;
		
		/**
		 * Caption's creation timestamp as a string
		 */
		String createdTimestamp;
		
		/**
		 * User object representing the caption's creator
		 */
		User from;
		
		/**
		 * Caption ID as a string
		 */
		String id;
	
	    /**
	     * Makes a new caption object out of a JSONObject
	     * @param captionObject json object used to create this caption
	     * @throws InstagramException
	     */
		public Caption(JSONObject captionObject) throws InstagramException {
			this.setId((String) captionObject.get("id"));
			this.setFrom(new User((JSONObject) captionObject.get("from"), accessToken));
			this.setText((String) captionObject.get("text"));
			this.setCreatedTimestamp((String) captionObject.get("created_time"));
		}
		
	    /**
	     * Returns the text for this caption
	     * @return The text for this caption 
	     */
		public String getText() {
			return text;
		}

	    /**
	     * Sets the text for this caption
	     * @param text the text for this caption 
	     */	
		protected void setText(String text) {
			this.text = text;
		}

	    /**
	     * Returns the caption's creation timestamp as a string
	     * @return The caption's creation timestamp as a string 
	     */
		public String getCreatedTimestamp() {
			return createdTimestamp;
		}

	    /**
	     * Sets the creation timestamp for this caption
	     * @param createdTimestamp the string representation of the creation timestamp for this caption 
	     */	
		protected void setCreatedTimestamp(String createdTimestamp) {
			this.createdTimestamp = createdTimestamp;
		}

	    /**
	     * Returns the creator of this caption
	     * @return The creator of this caption 
	     */
		public User getFrom() {
			return from;
		}

	    /**
	     * Sets creator of this caption
	     * @param from The user object representing the creator of the caption 
	     */	
		protected void setFrom(User from) {
			this.from = from;
		}

	    /**
	     * Returns id of this caption
	     * @return The id of this caption 
	     */
		public String getId() {
			return id;
		}

	    /**
	     * Sets creator of this caption
	     * @param from The user object representing the creator of the caption 
	     */
		protected void setId(String id) {
			this.id = id;
		}		
		
	    /**
	     * Checks if two caption objects are equal
	     * @param o The object to be compared 
	     * @return True of the two objects are equal, false otherwise
	     */
		public boolean equals(Object o) {
			if(o == null) return false;
			if(o == this) return true;
			if(o.getClass() != this.getClass()) return false;
			return ((Media.Caption)o).getId().equals(getId());
		}		
	}
	
	public class Video {
		
		String id;
		String url;
		int width;
		int heigth;
		
		public Video(JSONObject obj) throws InstagramException {
			this.setUrl((String) obj.get("url"));
//			this.setWidth((Integer) obj.get("width"));
//			this.setHeigth((Integer) obj.get("height"));
			this.setId((String) obj.get("id"));
		}
		
		public String getId() {
			return id;
		}
		protected void setId(String id) {
			this.id = id;
		}
		public String getUrl() {
			return url;
		}
		protected void setUrl(String url) {
			this.url = url;
		}
		public int getWidth() {
			return width;
		}
		protected void setWidth(int width) {
			this.width = width;
		}
		public int getHeigth() {
			return heigth;
		}
		protected void setHeigth(int heigth) {
			this.heigth = heigth;
		}
		
	}
	
	/**
     * Lazy-loads and returns a list of comments for this media
     * @return A list of lazy-loaded comments for this media 
     */
	public List<Comment> getComments() throws  InstagramException {
		if(comments == null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("media_id", getId());
			JSONObject object = (new GetMethod()
								.setMethodURI(uriConstructor.constructUri(UriFactory.Comments.GET_MEDIA_COMMENTS, map, true))
								)
								.call().getJSON();
			ArrayList<Comment> comments =  new ArrayList<Comment>();
			JSONArray commentObjects = (JSONArray) object.get("data");
			for(int i = 0; i < commentObjects.size(); i++) {
				comments.add(new Comment((JSONObject) commentObjects.get(i), accessToken));
			}
			setComments(comments);	
		}
		return comments;
	}
	
	protected void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
