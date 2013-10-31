package code.codepath.mytwitterapp.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.mytwitterapp.MyTwitterApp;
import com.codepath.mytwitterapp.helpers.EndlessScrollListener;
import com.codepath.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		fetchTimelineAsync();
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inf, parent, savedInstanceState);
		return v;
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
	
	@Override
	public void onResume() {
		// TODO: this hacky, but I'm having trouble inserting the Tweet response in onActivityResult in TimelineActvity. Fix this.
		fetchTimelineAsync();
		
		getListView().setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (getListView().getCount() > 1){
					Long tweetId = getAdapter().getItem(totalItemsCount - 2).getTweetId();
					
					MyTwitterApp.getRestClient().getOldTimeLine(tweetId, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray jsonTweets) {					
							getAdapter().addAll(Tweet.fromJson(jsonTweets));
						}
					});
				}
			}
		});
		super.onResume();
	}
}
