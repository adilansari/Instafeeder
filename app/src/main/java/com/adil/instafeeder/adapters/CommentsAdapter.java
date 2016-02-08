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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adil on 2/6/16.
 */
public class CommentsAdapter extends ArrayAdapter<InstagramComment>{

    public static final String TAG = CommentsAdapter.class.getSimpleName();

    private InstagramComment comment;

    public static class ViewHolder {
        @Bind(R.id.ivCommentUserPicture) ImageView imgProfilePicture;
        @Bind(R.id.tvCommentUsername) TextView tvCommentUsername;
        @Bind(R.id.tvCommentRelativeTime) TextView tvCommentRelativeTime;
        @Bind(R.id.tvCommentText) TextView tvComment;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public CommentsAdapter(Context context, int resource, List<InstagramComment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        ViewHolder holder;

        if (v == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.comment, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        comment = getItem(position);

        holder.imgProfilePicture.setImageResource(0);
        Picasso.with(getContext()).load(comment.user.imageUrl).into(holder.imgProfilePicture);

        holder.tvCommentUsername.setText(comment.user.username);

        holder.tvCommentRelativeTime.setText(Utils.getRelativeTimeSpan(comment.createdTime));

        holder.tvComment.setText(comment.text);

        Log.v(TAG, "user: " + comment.user.username + " comment: " + comment.text);

        return v;
    }
}
