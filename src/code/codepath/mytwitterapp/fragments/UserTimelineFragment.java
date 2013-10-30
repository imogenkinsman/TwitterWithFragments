package code.codepath.mytwitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.mytwitterapp.MyTwitterApp;
import com.codepath.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONArray jsonTweets) {
//				getAdapter().addAll(Tweet.fromJson(jsonTweets));
//			}
//		});
//	}
	public void setTimeline(String screenName) {
		MyTwitterApp.getRestClient().getUserTimeline(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});
	}
}
