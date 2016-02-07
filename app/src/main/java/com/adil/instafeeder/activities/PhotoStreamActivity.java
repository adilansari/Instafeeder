package com.adil.instafeeder.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.adapters.InstagramPostsAdapter;
import com.adil.instafeeder.models.InstagramPost;
import com.adil.instafeeder.network.InstagramApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoStreamActivity extends AppCompatActivity {

    private static final String TAG = PhotoStreamActivity.class.getSimpleName();

    private ArrayList<InstagramPost> instagramPosts;
    public static InstagramPostsAdapter postsAdapter;
    public static SwipeRefreshLayout swipeContainer;
    private InstagramApiClient instagramClient = new InstagramApiClient();

    @Bind(R.id.lvPhotos) ListView lvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stream);
        ButterKnife.bind(this);

        instagramPosts = new ArrayList<>();
        postsAdapter = new InstagramPostsAdapter(this, R.layout.item_post, instagramPosts);

        lvPosts.setAdapter(postsAdapter);

        instagramClient.fetchPopularPhotos();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                instagramClient.fetchPopularPhotos();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
