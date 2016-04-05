package com.codepath.apps.myTwitterClient.models.twitterdata;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HUONGVU on 3/27/2016.
 */
public class User {
    @SerializedName("name")
    private String name;

    @SerializedName("screen_name")
    private String user_name;

    @SerializedName("profile_image_url")
    private String profileUrl;

    @SerializedName("followers_count")
    private int followersCount;


    @SerializedName("friends_count")
    private int followingCount;

    @SerializedName("description")
    private String tagLine;

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }


    public String getTagLine() {
        return tagLine;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

}
