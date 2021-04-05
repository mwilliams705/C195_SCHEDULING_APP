package main.Util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Michael Williams - 001221520
 *
 * This class holds static methods to handle time conversions needed by the application.
 */
public class TimeConverter {


    /**
     * This static method converts a provided LocalDateTime object to the EST timezone.
     * @param localDateTime
     * @return
     */
    public static LocalDateTime localToEST(LocalDateTime localDateTime){

        ZonedDateTime toZDT = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estzdt = toZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        return estzdt.toLocalDateTime();
    }


}
