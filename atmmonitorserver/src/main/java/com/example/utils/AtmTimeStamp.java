package com.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AtmTimeStamp {


    public static DateTimeFormatter stampFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    public static LocalDateTime getStamp() {
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        return LocalDateTime.parse( localDateTime.format(AtmTimeStamp.stampFormat()) );
    }

    public static LocalDateTime string2Stamp(String stime) {
        return LocalDateTime.parse(stime,AtmTimeStamp.stampFormat());
    }

}
