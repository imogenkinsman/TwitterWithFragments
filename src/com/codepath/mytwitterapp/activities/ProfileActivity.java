package com.codepath.mytwitterapp.activities;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.R;
import com.codepath.mytwitterapp.MyTwitterApp;
import com.codepath.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
	}
	
	public void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
		
	}
	
	public void loadProfileInfo() {
		Intent i = getIntent();
		
		if (i != null && i.hasExtra("screen_name")){
			MyTwitterApp.getRestClient().getUserInfo(i.getStringExtra("screen_name"), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonUser) {
					User u = User.fromJson(jsonUser);
					getActionBar().setTitle("@" + u.getScreenName());
					populateProfileHeader(u);
				}
			});
		} else {
			MyTwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonUser) {
					User u = User.fromJson(jsonUser);
					getActionBar().setTitle("@" + u.getScreenName());
					populateProfileHeader(u);
				}
			});
		}
	}	
}
