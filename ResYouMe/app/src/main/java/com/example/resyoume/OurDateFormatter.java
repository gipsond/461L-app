package com.example.resyoume;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OurDateFormatter {

    private static DateFormat cachedDateFormat;

    public static String formatDate(Date date) {
        if (cachedDateFormat == null) {
            Locale ourLocale = Locale.getDefault();
            cachedDateFormat = new SimpleDateFormat("yy-MM-dd h:mm a, z", ourLocale);
        }
        return cachedDateFormat.format(date);
    }

}
