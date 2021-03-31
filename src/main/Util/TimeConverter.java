package main.Util;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConverter {

    public static LocalDateTime localToUTC(Timestamp timestamp){
        LocalDateTime toLDT = timestamp.toLocalDateTime();
        ZonedDateTime toZDT = toLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = toZDT.withZoneSameInstant(ZoneId.of("UTC"));
        return utczdt.toLocalDateTime();
    }

    public static LocalDateTime utcToLocal(LocalDateTime localDateTime){
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime toLocalTZ = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        return toLocalTZ.toLocalDateTime();

    }

    public static LocalDateTime utcToEST(LocalDateTime localDateTime){
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime toLocalTZ = zdt.withZoneSameInstant(ZoneId.of(ZoneId.of("EST").toString()));
        return toLocalTZ.toLocalDateTime();

    }
}
