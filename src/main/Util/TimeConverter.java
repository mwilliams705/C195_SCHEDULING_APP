package main.Util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConverter {

    public static LocalDateTime localToUTC(LocalDateTime localDateTime){

        ZonedDateTime toZDT = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = toZDT.withZoneSameInstant(ZoneId.of("UTC"));
        return utczdt.toLocalDateTime();
    }

    public static LocalDateTime localToEST(LocalDateTime localDateTime){

        ZonedDateTime toZDT = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estzdt = toZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        return estzdt.toLocalDateTime();
    }



    public static LocalDateTime utcToLocal(LocalDateTime localDateTime){
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime toLocalTZ = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        return toLocalTZ.toLocalDateTime();

    }


}
