package com.adil.instafeeder.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adil on 2/2/16.
 */
public class InstagramUser {
    public String username;
    public String imageUrl;

    public static InstagramUser fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            return null;
        InstagramUser instagramUser = new InstagramUser();
        instagramUser.username = jsonObject.getString("username");
        instagramUser.imageUrl = jsonObject.getString("profile_picture");

        return instagramUser;
    }
}
