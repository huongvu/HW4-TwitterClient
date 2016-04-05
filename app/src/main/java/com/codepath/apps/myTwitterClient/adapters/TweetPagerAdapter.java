package com.codepath.apps.myTwitterClient.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.myTwitterClient.fragments.HomeTimeLineFragment;
import com.codepath.apps.myTwitterClient.fragments.MentionTimeLineFragment;

/**
 * Created by HUONGVU on 4/5/2016.
 */
public class TweetPagerAdapter extends SmartFragmentStatePagerAdapter  {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "HOME", "MENTION"};

    public TweetPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new HomeTimeLineFragment();
        }
        else{
            return new MentionTimeLineFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
