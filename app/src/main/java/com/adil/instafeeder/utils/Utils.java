package com.adil.instafeeder.utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by adil on 2/4/16.
 */
public class Utils {

    public static String getRelativeTimeSpan(long time){
        Log.d("TIME", String.valueOf(time) + " "+ System.currentTimeMillis()+" "+localTimeStamp());

        String relativeTime  = (String) DateUtils.getRelativeTimeSpanString(time*1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

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

    public static String templatifyLikesCount(int likesCount){
        StringBuilder builder = new StringBuilder("♥ ");
        builder.append(getReadableNumber(likesCount));
        builder.append(" likes");
        return builder.toString();
    }

    public static String templatifyCommentsCount(int commentsCount){
        StringBuilder builder = new StringBuilder("view all ");
        builder.append(getReadableNumber(commentsCount));
        builder.append(" comments");
        return builder.toString();
    }

    public static String getReadableNumber(int num){
        return NumberFormat.getNumberInstance(Locale.US).format(num);
    }

    public static long localTimeStamp(){
        Calendar calendar = Calendar.getInstance();

        Date date = new Date();
        DateFormat localDf = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        localDf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Log.d("DATE", localDf.format(date));

        return calendar.getTimeInMillis();
    }
}
