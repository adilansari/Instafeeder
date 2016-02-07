package com.adil.instafeeder.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by adil on 2/2/16.
 */
public class InstagramComment implements Comparable<InstagramComment> {
    public InstagramUser user;
    public int createdTime;
    public String text;

    public static InstagramComment fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            return null;

        InstagramComment comment = new InstagramComment();
        comment.createdTime = jsonObject.getInt("created_time");
        comment.text = jsonObject.getString("text");
        comment.user = InstagramUser.fromJson(jsonObject.getJSONObject("from"));

        return comment;
    }

    public static List<InstagramComment> fromJson(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null)
            return null;

        List<InstagramComment> instagramComments = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            instagramComments.add(fromJson(jsonArray.getJSONObject(i)));
        }

        Collections.sort(instagramComments);

        return instagramComments;
    }

    @Override
    public int compareTo(InstagramComment another) {
        return this.createdTime - another.createdTime;
    }
}
