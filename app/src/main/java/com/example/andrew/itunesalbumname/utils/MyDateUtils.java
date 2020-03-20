package com.example.andrew.itunesalbumname.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateUtils {
    private static final String ITUNES_RESPONSE_FORMAT="yyyy-mm-dd'T'HH:mm:ss'Z'"; // 2017-12-25T08:00:00Z
    private static final String DATE_FORMAT = "MMM d yyyy"; // Oct 26 2017

    public static String getFormattedDate(String rawDate) {
        SimpleDateFormat utcFormat = new SimpleDateFormat(ITUNES_RESPONSE_FORMAT, Locale.ROOT);
        SimpleDateFormat displayedFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        try {
            Date date = utcFormat.parse(rawDate);
            return displayedFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String millisToMinutes(Integer rawMillis) {
        return String.format(Locale.getDefault(), "%02d:%02d", rawMillis / 60000, (rawMillis % 60000) / 1000);
    }
}
