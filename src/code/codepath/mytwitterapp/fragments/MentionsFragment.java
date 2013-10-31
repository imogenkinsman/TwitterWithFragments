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

public class MentionsFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				Log.e("ERROR", e.toString());
				Toast.makeText(getActivity(), "Unable To Access Tweets", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		getListView().setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (getListView().getCount() > 1){
					Long tweetId = getAdapter().getItem(totalItemsCount - 2).getTweetId();
					
					MyTwitterApp.getRestClient().getOldMentions(tweetId, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray jsonTweets) {					
							getAdapter().addAll(Tweet.fromJson(jsonTweets));
						}
					});
				}
			}
		});
	}
}
