package com.codepath.apps.myTwitterClient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.myTwitterClient.R;
import com.codepath.apps.myTwitterClient.fragments.UserTimeLineFragment;
import com.codepath.apps.myTwitterClient.models.twitterdata.User;
import com.codepath.apps.myTwitterClient.network.TwitterApplication;
import com.codepath.apps.myTwitterClient.network.TwitterClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private TwitterClient client;
    private User user;
    private String screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = TwitterApplication.getRestClient();


        screenName = getIntent().getStringExtra("screen_name");

        if(savedInstanceState == null) {
            UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimeLineFragment);
            ft.commit();

        }

        getUserInformation();

    }


    public void getUserInformation() {

        client.getUserInformation(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                GsonBuilder gsonBuilder = new GsonBuilder();

                Gson gson = gsonBuilder.create();

                user = gson.fromJson(response.toString(), User.class);

                getSupportActionBar().setTitle("@" + user.getUser_name());

                populateUserProfile(user);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", "onFailure: " + errorResponse.toString());
            }
        });

    }

    private void populateUserProfile(User user){
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTag  = (TextView) findViewById(R.id.tvTag);
        TextView tvFollower = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing= (TextView) findViewById(R.id.tvFollowing);
        ImageView imageView = (ImageView) findViewById(R.id.ivUserProfile);

        tvName.setText(user.getName());
        tvTag.setText(user.getTagLine());
        tvFollower.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");

        Glide.with(this)
                .load(user.getProfileUrl())
                .placeholder(R.drawable.ic_launcher) // can also be a drawable
                .error(R.drawable.ic_launcher) // will be displayed if the image cannot be loaded
                .fitCenter()
                .into(imageView);
    }
}
