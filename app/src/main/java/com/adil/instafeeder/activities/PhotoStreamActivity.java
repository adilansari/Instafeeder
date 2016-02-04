package com.adil.instafeeder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.adapters.InstagramPostsAdapter;
import com.adil.instafeeder.models.InstagramPost;
import com.adil.instafeeder.network.InstagramApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotoStreamActivity extends AppCompatActivity {

    private ArrayList<InstagramPost> instagramPosts;
    private InstagramPostsAdapter postsAdapter;
//    InstagramApiClient instagramClient = new InstagramApiClient();
    private static final String TAG = PhotoStreamActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stream);

        instagramPosts = new ArrayList<>();
        postsAdapter = new InstagramPostsAdapter(this,R.layout.post_item ,instagramPosts);

        ListView lvPosts = (ListView) findViewById(R.id.lvPhotos);
        lvPosts.setAdapter(postsAdapter);

        fetchPopularPhotos();
    }

    public void fetchPopularPhotos(){

        final String POPULAR_PHOTOS_ENDPOINT = "v1/media/popular";
        AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(getUrl(POPULAR_PHOTOS_ENDPOINT), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, response.toString());
                try {
                    instagramPosts = (ArrayList<InstagramPost>) InstagramPost.fromJson(response.getJSONArray("data"));
                    postsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, "http failure", throwable);
            }
        });

        Log.i(TAG, "updating list of popular photos");
    }

    private String getUrl(String endoint){
        final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
        final String TAG = InstagramApiClient.class.getSimpleName();
        final String BASE_URL = "https://api.instagram.com/";

        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append(endoint);
        builder.append("?client_id=");
        builder.append(CLIENT_ID);
        return builder.toString();
    }
}
