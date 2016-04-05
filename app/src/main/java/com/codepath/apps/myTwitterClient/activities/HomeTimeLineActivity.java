package com.codepath.apps.myTwitterClient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.myTwitterClient.R;
import com.codepath.apps.myTwitterClient.adapters.TweetPagerAdapter;
import com.codepath.apps.myTwitterClient.fragments.HomeTimeLineFragment;
import com.codepath.apps.myTwitterClient.fragments.TweetComposeFragment;
import com.codepath.apps.myTwitterClient.interfaces.TweetComposeListener;
import com.codepath.apps.myTwitterClient.models.twitterfeature.Tweet;
import com.codepath.apps.myTwitterClient.network.TwitterApplication;
import com.codepath.apps.myTwitterClient.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class HomeTimeLineActivity extends AppCompatActivity implements TweetComposeListener {

    private TwitterClient client;
    private TweetPagerAdapter adapter;
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        // handle click here
        FragmentManager fm = getSupportFragmentManager();
        TweetComposeFragment editNameDiaglog = TweetComposeFragment.newInstance("");
        editNameDiaglog.show(fm, "");
    }

    public void onProfileVIew(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name","vuthehuong");
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometimeline);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter3128);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        client = TwitterApplication.getRestClient();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TweetPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);


    }

    private void postTimeline(final Tweet postTweet){
        client.postHomeTimeline(postTweet.getBody(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", "onFailure: " + errorResponse.toString());
            }
        });
    }

    @Override
    public void onClickTweet(Tweet content) {

        if(content.getBody().trim().length() != 0) {
            postTimeline(content);

            if(adapter.getRegisteredFragment(viewPager.getCurrentItem()) != null){
                HomeTimeLineFragment fragment = (HomeTimeLineFragment)adapter.getRegisteredFragment(0);
                fragment.addSingleTweet(0,content);
                Log.d("DEBUG", "onClickTweet: " + content.toString());
                fragment.notifyAdapter();
            }
        }
        else{

        }
    }


}
