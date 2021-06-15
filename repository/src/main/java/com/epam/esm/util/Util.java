package com.epam.esm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class Util {
    public static LocalDateTime getCurrentFormattedDate(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

        df.setTimeZone(tz);
        return LocalDateTime.parse(df.format(new Date()));
    }
}
