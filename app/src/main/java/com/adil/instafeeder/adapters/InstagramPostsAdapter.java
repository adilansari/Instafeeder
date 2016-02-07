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
import com.adil.instafeeder.models.InstagramComment;
import com.adil.instafeeder.models.InstagramPost;
import com.adil.instafeeder.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adil on 2/3/16.
 */
public class InstagramPostsAdapter extends ArrayAdapter<InstagramPost>{

    private static final String TAG = InstagramPostsAdapter.class.getSimpleName();
    private InstagramComment lastComment;

    private TextView tvUserName;
    private TextView tvTime;
    private ImageView imgProfile;
    private ImageView imgView;
    private TextView tvLikesCount;
    private TextView tvCaptionUsername;
    private TextView tvCaption;
    private TextView tvCommentsCount;
    private TextView tvLastCommentUsername;
    private TextView tvLastCommentText;

    public InstagramPostsAdapter(Context context, int resource, List<InstagramPost> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.item_post, null);
        }

        InstagramPost post = getItem(position);

        tvUserName = (TextView) v.findViewById(R.id.username);
        tvUserName.setText(post.user.username);

        tvTime = (TextView) v.findViewById(R.id.timeDiff);
        tvTime.setText(Utils.getRelativeTimeSpan(post.createdTime));

        imgProfile = (ImageView) v.findViewById(R.id.ivProfilePicture);
        imgProfile.setImageResource(0);
        Picasso.with(getContext()).load(post.user.imageUrl).into(imgProfile);

        imgView = (ImageView) v.findViewById(R.id.ivPhoto);
        imgView.setImageResource(0);
        Picasso.with(getContext()).load(post.imageUrl).into(imgView);

        tvLikesCount = (TextView) v.findViewById(R.id.likesCount);
        tvLikesCount.setText(Utils.templatifyLikesCount(post.likesCount));

        tvCaptionUsername = (TextView) v.findViewById(R.id.tvCaptionUsername);
        tvCaptionUsername.setText(post.user.username);

        tvCaption = (TextView) v.findViewById(R.id.tvCaption);
        tvCaption.setText(post.caption);

        tvCommentsCount = (TextView) v.findViewById(R.id.tvCommentsCount);
        tvCommentsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvCommentsCount.setText(Utils.templatifyCommentsCount(post.commentsCount));

        lastComment = post.fetchLastComment();
        tvLastCommentUsername = (TextView) v.findViewById(R.id.tvLastCommentUser);
        tvLastCommentUsername.setText(lastComment.user.username);

        tvLastCommentText = (TextView) v.findViewById(R.id.tvLastCommentText);
        tvLastCommentText.setText(lastComment.text);

        Log.i(TAG, "likesCount: " + post.likesCount + " caption: " + post.caption + " commentsCount: " + post.commentsCount + " user: " + post.user);

        return v;
    }
}
