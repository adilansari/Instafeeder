package com.adil.instafeeder.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.models.InstagramComment;

import java.util.List;

/**
 * Created by adil on 2/6/16.
 */
public class MinifiedCommentsAdapter extends ArrayAdapter<InstagramComment>{

    public static final String TAG = MinifiedCommentsAdapter.class.getSimpleName();

    public MinifiedCommentsAdapter(Context context, int resource, List<InstagramComment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.minified_comment, null);
        }

        InstagramComment comment = getItem(position);

        TextView tvUserName = (TextView) v.findViewById(R.id.tvCommentUser);
        tvUserName.setText(comment.user.username);

        TextView tvComment = (TextView) v.findViewById(R.id.tvComment);
        tvComment.setText(comment.text);

        Log.v(TAG, "user: " + comment.user.username + " comment: " + comment.text);

        return v;
    }
}
