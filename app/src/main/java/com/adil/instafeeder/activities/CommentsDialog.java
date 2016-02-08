package com.adil.instafeeder.activities;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adil.instafeeder.R;
import com.adil.instafeeder.adapters.CommentsAdapter;
import com.adil.instafeeder.models.InstagramComment;
import com.adil.instafeeder.network.InstagramApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsDialog extends DialogFragment{

    public static CommentsAdapter commentsAdapter;
    private InstagramApiClient instagramApiClient;
    private List<InstagramComment> instagramComments;
    private String mediaId;

    @Bind(R.id.lvComments) ListView lvComments;

    public CommentsDialog() {
    }

    public static CommentsDialog newInstance(String mediaId){
        CommentsDialog frag = new CommentsDialog();
        Bundle args = new Bundle();
        args.putString("mediaId", mediaId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        getDialog().setTitle("COMMENTS");
        View view = inflater.inflate(R.layout.fragment_comment, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mediaId = getArguments().getString("mediaId");
        instagramComments = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(getContext(), R.layout.comment, instagramComments);

        lvComments.setAdapter(commentsAdapter);

        instagramApiClient = new InstagramApiClient();
        instagramApiClient.fetchComments(mediaId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
