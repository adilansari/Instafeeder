package com.adil.instafeeder.utils;

import android.text.format.DateUtils;
import android.util.Log;

/**
 * Created by adil on 2/4/16.
 */
public class Utils {

    public static String getRelativeTimeSpan(long time){
        Log.d("TIME", String.valueOf(time) + " "+ System.currentTimeMillis());

        String relativeTime  = (String) DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        String[] tokens = relativeTime.split(" ");
        StringBuilder customRelativeTime = new StringBuilder(tokens[0]);

        if (tokens[1].toLowerCase().contains("mins")){
            customRelativeTime.append("m");
        } else if (tokens[1].toLowerCase().contains("hour")){
            customRelativeTime.append("h");
        } else if (tokens[1].toLowerCase().contains("day")){
            customRelativeTime.append("d");
        } else if (tokens[1].toLowerCase().contains("week")){
            customRelativeTime.append("w");
        }
        return customRelativeTime.toString();
    }
}
