package com.codepath.apps.myTwitterClient.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.myTwitterClient.models.twitterfeature.Tweet;
import com.codepath.apps.myTwitterClient.models.twitterfeature.TweetsList;
import com.codepath.apps.myTwitterClient.network.TwitterApplication;
import com.codepath.apps.myTwitterClient.network.TwitterClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by HUONGVU on 4/5/2016.
 */
public class HomeTimeLineFragment extends TweetsListFragment {
    private TwitterClient client;

    private long currentId = 1;
    private int numberTweets =  20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(currentId);
    }


    public void populateTimeline(long maxId) {

        client.getHomeTimeline(maxId, numberTweets, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                String myCustom_JSONResponse;

                myCustom_JSONResponse = "{\"TweetsList\":" + response.toString() + "}";
                Log.d("Data", "onSuccess: " + myCustom_JSONResponse);

                GsonBuilder gsonBuilder = new GsonBuilder();

                Gson gson = gsonBuilder.create();

                TweetsList tweetsData = gson.fromJson(myCustom_JSONResponse, TweetsList.class);

                List<Tweet> tweetRespone = tweetsData.tweetsList;

                for (Tweet tweet : tweetRespone) {
                    addTweet(tweet);
                }

                notifyAdapter();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", "onFailure: " + errorResponse.toString());
            }
        });



    }


}
