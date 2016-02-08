package com.adil.instafeeder.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.activities.CommentsDialog;
import com.adil.instafeeder.models.InstagramComment;
import com.adil.instafeeder.models.InstagramPost;
import com.adil.instafeeder.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adil on 2/3/16.
 */
public class InstagramPostsAdapter extends ArrayAdapter<InstagramPost>{

    private static final String TAG = InstagramPostsAdapter.class.getSimpleName();
    private InstagramComment lastComment;

    public static class ViewHolder{
        @Bind(R.id.username) TextView tvUserName;
        @Bind(R.id.timeDiff) TextView tvTime;
        @Bind(R.id.ivProfilePicture) ImageView imgProfile;
        @Bind(R.id.ivPhoto) ImageView imgView;
        @Bind(R.id.likesCount) TextView tvLikesCount;
        @Bind(R.id.tvCaptionUsername) TextView tvCaptionUsername;
        @Bind(R.id.tvCaption) TextView tvCaption;
        @Bind(R.id.tvCommentsCount) TextView tvCommentsCount;
        @Bind(R.id.tvLastCommentUser) TextView tvLastCommentUsername;
        @Bind(R.id.tvLastCommentText) TextView tvLastCommentText;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public InstagramPostsAdapter(Context context, int resource, List<InstagramPost> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.item_post, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        InstagramPost post = getItem(position);
        final String mediaId = post.mediaId;

        holder.tvUserName.setText(post.user.username);

        holder.tvTime.setText(Utils.getRelativeTimeSpan(post.createdTime));

        holder.imgProfile.setImageResource(0);
        Picasso.with(getContext()).load(post.user.imageUrl).into(holder.imgProfile);

        Picasso.with(getContext()).load(post.imageUrl).into(holder.imgView);

        holder.tvLikesCount.setText(Utils.templatifyLikesCount(post.likesCount));

        holder.tvCaptionUsername.setText(post.user.username);

        holder.tvCaption.setText(post.caption);

        holder.tvCommentsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentsDialog(mediaId);
            }
        });
        holder.tvCommentsCount.setText(Utils.templatifyCommentsCount(post.commentsCount));

        lastComment = post.fetchLastComment();
        if (lastComment != null) {
            holder.tvLastCommentUsername.setText(lastComment.user.username);
            holder.tvLastCommentText.setText(lastComment.text);
        }

        Log.i(TAG, "likesCount: " + post.likesCount + " mediaId: " + post.mediaId+ " commentsCount: " + post.commentsCount + " user: " + post.user);

        return v;
    }

    private void showCommentsDialog(String mediaId){
        FragmentActivity activity = (FragmentActivity) getContext();
        FragmentManager fm = activity.getSupportFragmentManager();
        CommentsDialog commentsDialog = CommentsDialog.newInstance(mediaId);
        commentsDialog.show(fm, "dialog");
    }
}
