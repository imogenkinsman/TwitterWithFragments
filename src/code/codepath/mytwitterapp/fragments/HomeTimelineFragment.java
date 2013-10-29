package code.codepath.mytwitterapp.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.mytwitterapp.MyTwitterApp;
import com.codepath.mytwitterapp.helpers.EndlessScrollListener;
import com.codepath.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fetchTimelineAsync();
//		setupListeners();
	}
	
	private void fetchTimelineAsync() {
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				
				getAdapter().clear();
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
				getListView().onRefreshComplete(); // need to be set differently for firstLoad vs not?					
				
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				Log.e("ERROR", e.toString());
				Toast.makeText(getActivity(), "Unable To Access Tweets", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setupListeners() {
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
//				MyTwitterApp.getRestClient().getOldTimeLine(tweets.get(tweets.size() - 1).getTweetId(), new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONArray jsonTweets) {	
//						getAdapter().addAll(Tweet.fromJson(jsonTweets));
//					}
//				});
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				fetchTimelineAsync();
			}
		});
	}
}
