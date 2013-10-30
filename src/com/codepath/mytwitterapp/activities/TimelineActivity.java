package com.codepath.mytwitterapp.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import code.codepath.mytwitterapp.fragments.HomeTimelineFragment;
import code.codepath.mytwitterapp.fragments.MentionsFragment;

import com.codepath.apps.mytwitterapp.R;
import com.codepath.mytwitterapp.adapters.TweetsAdapter;
import com.codepath.mytwitterapp.models.Tweet;

public class TimelineActivity extends FragmentActivity implements TabListener {

	private static final int REQUEST_CODE = 0;
	TweetsAdapter twtAdapter;
	//PullToRefreshListView lvTweets;
	ArrayList<Tweet> tweets;
	Tab tabHome;
	Tab tabMentions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		//setupViews();
//		setListeners();
//		fetchTimelineAsync(true);
		setupNavigationTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment")
				.setIcon(R.drawable.ic_home)
				.setTabListener(this);
		
		tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsTimelineFragment")
				.setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}
	
//	public void setupViews() {
//		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
//		fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
//	}
	
//	public void setListeners() {
//		
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				MyTwitterApp.getRestClient().getOldTimeLine(tweets.get(tweets.size() - 1).getTweetId(), new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONArray jsonTweets) {	
//						fragmentTweets.getAdapter().addAll(Tweet.fromJson(jsonTweets));
//					}
//				});
//			}
//		});
//		
//		lvTweets.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
////				fetchTimelineAsync(false);
//			}
//		});
//	}
	
//	private void fetchTimelineAsync(final boolean firstLoad) {
//
//	}
	
	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == 1) {
//			ActionBar actionBar = getActionBar();
//			Tab tab = actionBar.getSelectedTab();
//			if(tab.getTag() == "HomeTimelineFragment") {
//				Tweet newTweet = (Tweet) data.getSerializableExtra("tweet");
//				HomeTimelineFragment fragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
//				fragment.getAdapter().insert(newTweet, 0);
//				Toast.makeText(this, "Tweet Posted", Toast.LENGTH_SHORT).show();
//			}
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
