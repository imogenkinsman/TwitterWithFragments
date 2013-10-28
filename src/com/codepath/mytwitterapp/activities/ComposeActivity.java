package com.codepath.mytwitterapp.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.R;
import com.codepath.mytwitterapp.MyTwitterApp;
import com.codepath.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	
	private static final int TWEET_SUCCESS = 1;
	
	EditText etTweet;
	Button btnTweet;
	TextView tvTwitterHandle;
	ImageView ivUserProfile;
	TextView tvChars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setup();
		addListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void setup() {
		etTweet = (EditText) findViewById(R.id.etTweet);
		btnTweet = (Button) findViewById(R.id.btnTweet);
		btnTweet.setEnabled(false);
		tvTwitterHandle = (TextView) findViewById(R.id.tvUser);
		ivUserProfile = (ImageView) findViewById(R.id.ivUserProfile);
		tvChars = (TextView) findViewById(R.id.tvChars);
		
		MyTwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject object) {
				try {
					tvTwitterHandle.setText("@" + object.getString("screen_name"));
					ImageLoader.getInstance().displayImage(object.getString("profile_image_url"), ivUserProfile);
				} catch (JSONException e) {
					e.printStackTrace();
			    }
			}
		});
	}
	
	public void addListeners() {
		//disable the tweet button when there's no body content
		etTweet.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() == 0 || s.length() > 140) {
					btnTweet.setEnabled(false);
				} else {
					btnTweet.setEnabled(true);
				}
				tvChars.setText(Integer.toString(140 - s.length()));
			}
		});
	}
	
	public void tweet(View v) {
		String messageBody = etTweet.getText().toString();
		
		MyTwitterApp.getRestClient().postTweet(messageBody, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonTweet) {
				Tweet tweet = Tweet.fromJson(jsonTweet);
				
				Intent data = new Intent();
				data.putExtra("tweet", tweet);
				setResult(TWEET_SUCCESS, data);
				finish();
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				Log.e("ERROR", e.toString());
				Toast.makeText(ComposeActivity.this, "Unable To Post Tweet", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void cancel(View v) {
		finish();
	}

}
