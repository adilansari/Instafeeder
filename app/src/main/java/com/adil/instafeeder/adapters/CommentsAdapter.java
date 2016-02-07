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
import com.adil.instafeeder.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adil on 2/6/16.
 */
public class CommentsAdapter extends ArrayAdapter<InstagramComment>{

    public static final String TAG = CommentsAdapter.class.getSimpleName();

    public CommentsAdapter(Context context, int resource, List<InstagramComment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.comment, null);
        }

        InstagramComment comment = getItem(position);

        ImageView imgProfilePicture = (ImageView) v.findViewById(R.id.ivCommentUserPicture);
        imgProfilePicture.setImageResource(0);
        Picasso.with(getContext()).load(comment.user.imageUrl).into(imgProfilePicture);

        TextView tvCommentUsername = (TextView) v.findViewById(R.id.tvCommentUsername);
        tvCommentUsername.setText(comment.user.username);

        TextView tvCommentRelativeTime = (TextView) v.findViewById(R.id.tvCommentRelativeTime);
        tvCommentRelativeTime.setText(Utils.getRelativeTimeSpan(comment.createdTime));

        TextView tvComment = (TextView) v.findViewById(R.id.tvCommentText);
        tvComment.setText(comment.text);

        Log.v(TAG, "user: " + comment.user.username + " comment: " + comment.text);

        return v;
    }
}
