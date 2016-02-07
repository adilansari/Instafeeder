package com.adil.instafeeder.network;

import android.util.Log;

import com.adil.instafeeder.activities.PhotoStreamActivity;
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
    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private static final String TAG = InstagramApiClient.class.getSimpleName();
    private static final String BASE_URL = "https://api.instagram.com/";
    private static final String POPULAR_PHOTOS_ENDPOINT = "v1/media/popular";
    private List<InstagramPost> instagramPosts;

    public void fetchPopularPhotos(){
        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(getUrl(POPULAR_PHOTOS_ENDPOINT), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, response.toString());
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
                Log.e(TAG, "http failure", throwable);
            }
        });
    }

    private String getUrl(String endoint){
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append(endoint);
        builder.append("?client_id=");
        builder.append(CLIENT_ID);
        return builder.toString();
    }
}
