package com.adil.instafeeder.network;

import android.util.Log;

import com.adil.instafeeder.activities.CommentsDialog;
import com.adil.instafeeder.activities.PhotoStreamActivity;
import com.adil.instafeeder.models.InstagramComment;
import com.adil.instafeeder.models.InstagramPost;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Instagram API client.
 */
public class InstagramApiClient {
    private static final String TAG = InstagramApiClient.class.getSimpleName();

    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private static final String BASE_URL = "https://api.instagram.com/v1/media/";
    private static final String POPULAR_PHOTOS_ENDPOINT = "popular";

    private List<InstagramPost> instagramPosts;
    private List<InstagramComment> instagramComments;

    public void fetchPopularPhotos(){
        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(getPopularPhotosUrl(), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    instagramPosts = InstagramPost.fromJson(response.getJSONArray("data"));
                    Log.d(TAG, "loaded "+ instagramPosts.size()+ " items");
                    PhotoStreamActivity.postsAdapter.clear();
                    PhotoStreamActivity.postsAdapter.addAll(instagramPosts);
                    PhotoStreamActivity.swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, "popular posts http failure", throwable);
            }
        });
    }

    public void fetchComments(String mediaId){
        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(getCommentsUrl(mediaId), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    instagramComments = InstagramComment.fromJson(response.getJSONArray("data"));
                    Log.d(TAG, "loaded "+ instagramComments.size()+ " items");
                    CommentsDialog.commentsAdapter.addAll(instagramComments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, "comments http failure", throwable);
            }
        });
    }

    private String getPopularPhotosUrl(){
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append(POPULAR_PHOTOS_ENDPOINT);
        builder.append("?client_id=");
        builder.append(CLIENT_ID);
        return builder.toString();
    }

    private String getCommentsUrl(String mediaId){
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append(mediaId);
        builder.append("/comments");
        builder.append("?client_id=");
        builder.append(CLIENT_ID);
        return builder.toString();
    }
}
