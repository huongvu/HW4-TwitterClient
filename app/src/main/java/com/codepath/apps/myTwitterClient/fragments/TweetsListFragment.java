package com.codepath.apps.myTwitterClient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.myTwitterClient.R;
import com.codepath.apps.myTwitterClient.adapters.TweetsAdapter;
import com.codepath.apps.myTwitterClient.interfaces.EndlessScrollListener;
import com.codepath.apps.myTwitterClient.models.twitterfeature.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUONGVU on 4/4/2016.
 */
public abstract class TweetsListFragment extends Fragment {
    private TweetsAdapter adapter;
    private List<Tweet> tweetsResponse;
    private long maxId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tweetsResponse = new ArrayList<>();

        adapter = new TweetsAdapter(tweetsResponse);

        adapter.setEndlessScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int position) {
                if (position >= (tweetsResponse.size() - 5)) {
                    maxId = Long.valueOf(tweetsResponse.get(tweetsResponse.size() - 1).getTweetId());
                    populateTimeline(maxId);
                }
                return true;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        RecyclerView rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);

        rvTweets.setHasFixedSize(true);

        // Attach the adapter to the recyclerview to populate items
        rvTweets.setAdapter(adapter);

        rvTweets.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    public void addTweet(Tweet tweet){
        tweetsResponse.add(tweet);
    }

    public void addSingleTweet(int position, Tweet tweet){
        tweetsResponse.add(position,tweet);
        adapter.notifyItemInserted(position);
    }

    public void notifyAdapter(){
        int curSize = adapter.getItemCount();
        adapter.notifyItemRangeInserted(curSize, tweetsResponse.size());
    }

    protected abstract void populateTimeline(long maxId);
}
