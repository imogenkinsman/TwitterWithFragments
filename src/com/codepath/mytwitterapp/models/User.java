package com.codepath.mytwitterapp.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

// https://dev.twitter.com/docs/api/1.1/get/users/show
public class User implements Serializable {

	private static final long serialVersionUID = 2172542919238116422L;
	
	private long id;
	private String name;
	private String profileImgUrl;
	private String screenName;
	private String tagline;
	private int followersCount;
	private int friendsCount;
	
	public User() {
		super();
	}
	
	public static User fromJson(JSONObject object) {
		User user = new User();
		
		try {
			user.id = object.getLong("id");
			user.profileImgUrl = object.getString("profile_image_url");
			user.name = object.getString("name");
			user.screenName = object.getString("screen_name");
			user.tagline = object.getString("description");
			user.followersCount = object.getInt("followers_count");
			user.friendsCount = object.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	public String getProfileImageUrl() {
		return profileImgUrl;
	}
	
	public String getName() {
		return name;
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getTagline() {
		return tagline;
	}
	
	public int getFollowersCount() {
		return followersCount;
	}
	
	public int getFriendsCount() {
		return friendsCount;
	}
	
}
