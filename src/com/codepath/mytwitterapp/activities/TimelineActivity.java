package com.codepath.mytwitterapp.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.R;
import com.codepath.mytwitterapp.MyTwitterApp;
import com.codepath.mytwitterapp.adapters.TweetsAdapter;
import com.codepath.mytwitterapp.helpers.EndlessScrollListener;
import com.codepath.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	private static final int REQUEST_CODE = 0;
	TweetsAdapter twtAdapter;
	PullToRefreshListView lvTweets;
	ArrayList<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupViews();
		setListeners();
		fetchTimelineAsync(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void setupViews() {
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
	}
	
	public void setListeners() {
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				MyTwitterApp.getRestClient().getOldTimeLine(tweets.get(tweets.size() - 1).getTweetId(), new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {	
						twtAdapter.addAll(Tweet.fromJson(jsonTweets));
					}
				});
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				fetchTimelineAsync(false);
			}
		});
	}
	
	private void fetchTimelineAsync(final boolean firstLoad) {
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				tweets = Tweet.fromJson(jsonTweets);
					
				
				if (firstLoad) {
					twtAdapter = new TweetsAdapter(getBaseContext(), tweets);
					lvTweets.setAdapter(twtAdapter);
				} else {
					twtAdapter.clear();
					twtAdapter.addAll(tweets);
					lvTweets.onRefreshComplete();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				Log.e("ERROR", e.toString());
				Toast.makeText(TimelineActivity.this, "Unable To Access Tweets", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == 1) {
			Tweet newTweet = (Tweet) data.getSerializableExtra("tweet");
			twtAdapter.insert(newTweet, 0);
			Toast.makeText(this, "Tweet Posted", Toast.LENGTH_SHORT).show();
		}
	}

}
