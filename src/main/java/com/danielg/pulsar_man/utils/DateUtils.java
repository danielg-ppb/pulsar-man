package com.danielg.pulsar_man.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String convertLongToDateTimeString(long epoch) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch),
                ZoneId.systemDefault()).format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

}
