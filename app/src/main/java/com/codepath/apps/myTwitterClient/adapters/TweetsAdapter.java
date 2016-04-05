package com.codepath.apps.myTwitterClient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.myTwitterClient.R;
import com.codepath.apps.myTwitterClient.activities.ProfileActivity;
import com.codepath.apps.myTwitterClient.interfaces.EndlessScrollListener;
import com.codepath.apps.myTwitterClient.models.twitterdata.User;
import com.codepath.apps.myTwitterClient.models.twitterfeature.Tweet;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HUONGVU on 3/27/2016.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    // Store a member variable for the articles
    private List<Tweet> mHomeLineData;

    Context context;

    private EndlessScrollListener endlessScrollListener;

    public void setEndlessScrollListener(EndlessScrollListener endlessScrollListener) {
        this.endlessScrollListener = endlessScrollListener;
    }

    // Pass in the contact array into the constructor
    public TweetsAdapter(List<Tweet> homeLineData) {
        mHomeLineData = homeLineData;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        @Bind(R.id.tvName) TextView tweetName;
        @Bind(R.id.tvUserName) TextView tweetUserName;
        @Bind(R.id.tvTimeStamp) TextView tweetTimeStamp;
        @Bind(R.id.tvTweetContent) TextView tweetContent;
        @Bind(R.id.ivUserProfile) ImageView tweetProfileUrl;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.tweet_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(tweetView);


        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final TweetsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String imageUrl;
        User user;

        if(mHomeLineData.get(position).getReTweetedStatus()!= null){
            viewHolder.tweetName.setText(mHomeLineData.get(position).getReTweetedStatus().getTweetUser().getName());
            viewHolder.tweetUserName.setText("@" + mHomeLineData.get(position).getReTweetedStatus().getTweetUser().getUser_name());
            viewHolder.tweetContent.setText(mHomeLineData.get(position).getReTweetedStatus().getBody());
            viewHolder.tweetTimeStamp.setText(mHomeLineData.get(position).getReTweetedStatus().getTimeStamp());
            imageUrl = mHomeLineData.get(position).getReTweetedStatus().getTweetUser().getProfileUrl();
            viewHolder.tweetProfileUrl.setTag(mHomeLineData.get(position).getReTweetedStatus().getTweetUser().getUser_name());
        }
        else {
            viewHolder.tweetName.setText(mHomeLineData.get(position).getTweetUser().getName());
            viewHolder.tweetUserName.setText("@" + mHomeLineData.get(position).getTweetUser().getUser_name());
            viewHolder.tweetContent.setText(mHomeLineData.get(position).getBody());
            viewHolder.tweetTimeStamp.setText(mHomeLineData.get(position).getTimeStamp());
            imageUrl = mHomeLineData.get(position).getTweetUser().getProfileUrl();
            viewHolder.tweetProfileUrl.setTag(mHomeLineData.get(position).getTweetUser().getUser_name());
        }


        Glide.with(viewHolder.tweetProfileUrl.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher) // can also be a drawable
                .error(R.drawable.ic_launcher) // will be displayed if the image cannot be loaded
                .fitCenter()
                .into(viewHolder.tweetProfileUrl);

        final int VISIBLE_THRESHOLD = 5;

        // you can cache getItemCount() in a member variable for more performance tuning
        if(position == getItemCount() - VISIBLE_THRESHOLD) {
            if(endlessScrollListener != null) {
                endlessScrollListener.onLoadMore(position);
            }
        }

        viewHolder.tweetProfileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenName = v.getTag().toString();
                Intent i = new Intent(context,ProfileActivity.class);
                i.putExtra("screen_name", screenName);
                context.startActivity(i);
           }
        });

    }


    // Return the total count of items
    @Override
    public int getItemCount() {
        //return mArticles.size();
        if (mHomeLineData == null){
            return 0;
        }
        return mHomeLineData.size();
    }

}
