package com.adil.instafeeder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.adapters.InstagramPostsAdapter;
import com.adil.instafeeder.models.InstagramPost;
import com.adil.instafeeder.network.InstagramApiClient;

import java.util.ArrayList;

public class PhotoStreamActivity extends AppCompatActivity {

    private ArrayList<InstagramPost> instagramPosts;
    public static InstagramPostsAdapter postsAdapter;
    InstagramApiClient instagramClient = new InstagramApiClient();
    private static final String TAG = PhotoStreamActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stream);

        instagramPosts = new ArrayList<>();
        postsAdapter = new InstagramPostsAdapter(this, R.layout.post_item, instagramPosts);

        ListView lvPosts = (ListView) findViewById(R.id.lvPhotos);
        lvPosts.setAdapter(postsAdapter);

        instagramClient.fetchPopularPhotos();
    }
}
