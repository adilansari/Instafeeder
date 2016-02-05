package com.adil.instafeeder.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.models.InstagramPost;
import com.adil.instafeeder.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adil on 2/3/16.
 */
public class InstagramPostsAdapter extends ArrayAdapter<InstagramPost>{

    private static final String TAG = InstagramPostsAdapter.class.getSimpleName();

    public InstagramPostsAdapter(Context context, int resource, List<InstagramPost> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.post_item, null);
        }

        InstagramPost post = getItem(position);
        TextView tvUserName = (TextView) v.findViewById(R.id.username);
        TextView tvTime = (TextView) v.findViewById(R.id.timeDiff);

        ImageView imgView = (ImageView) v.findViewById(R.id.ivPhoto);
        ImageView imgProfile = (ImageView) v.findViewById(R.id.ivProfilePicture);

        TextView tvLikesCount = (TextView) v.findViewById(R.id.likesCount);
        TextView tvCaption = (TextView) v.findViewById(R.id.tvCaption);

        tvUserName.setText(post.user.username);
        tvTime.setText(Utils.getRelativeTimeSpan(post.createdTime));
        imgProfile.setImageResource(0);
        Picasso.with(getContext()).load(post.user.imageUrl).into(imgProfile);

        imgView.setImageResource(0);
        Picasso.with(getContext()).load(post.imageUrl).into(imgView);

        tvCaption.setText(post.caption);
        tvLikesCount.setText(Utils.templatifyLikesCount(post.likesCount));

        Log.i(TAG, "likesCount: " + post.likesCount + " caption: " + post.caption + " commentsCount: " + post.commentsCount + " user: " + post.user);

        return v;
    }
}
