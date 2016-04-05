package com.codepath.apps.myTwitterClient.utils;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.codepath.apps.myTwitterClient.R;

/**
 * Created by HUONGVU on 4/5/2016.
 */
public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
