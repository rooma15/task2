package com.epam.esm.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {

    public static final Logger lOGGER = LogManager.getLogger();

    public static String getCurrentFormattedDate(){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date());
    }
}
