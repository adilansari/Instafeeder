package com.adil.instafeeder.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adil on 2/2/16.
 */
public class InstagramPost {
    public String mediaId;
    public String user;
    public String imageUrl;
    public List<InstagramComment> comments;
    public String caption;
    public int likesCount;
    public List<InstagramUser> likers;
    public int commentsCount;
    public long createdTime;
    public String mediaType;

    public static final String TAG = InstagramPost.class.getSimpleName();

    public static InstagramPost fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            return null;

        InstagramPost post = new InstagramPost();

        JSONObject comments = jsonObject.getJSONObject("comments");
        post.commentsCount = comments.getInt("count");

        JSONObject caption = jsonObject.optJSONObject("caption");
        if (caption != null)
            post.caption = caption.getString("text");

        JSONObject likes = jsonObject.getJSONObject("likes");
        post.likesCount = likes.getInt("count");

        post.mediaId = jsonObject.getString("id");

        JSONObject user = jsonObject.getJSONObject("user");
        post.user = user.getString("username");

        JSONObject images = jsonObject.getJSONObject("images");
        post.imageUrl = images.getJSONObject("standard_resolution").getString("url");

        Log.i(TAG, "likesCount: "+ post.likesCount + "caption: " + post.caption + "commentsCount: "+ post.commentsCount + "mediaId" + post.mediaId);

        return post;
    }

    public static List<InstagramPost> fromJson(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null)
            return null;

        List<InstagramPost> instagramPosts = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject;

            try{
                jsonObject = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            InstagramPost post = fromJson(jsonObject);

            if (post != null) {
                instagramPosts.add(post);
            }
        }


        return instagramPosts;
    }

}
