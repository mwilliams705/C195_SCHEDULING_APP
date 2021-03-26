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

    public static void fromSchoolMaterial(){
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();

        ZonedDateTime zdtOut = ldtIn.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtOutFinal = zdtOutToLocalTZ.toLocalDateTime();

        System.out.println("Timestamp");
        System.out.println(ts);
        System.out.println("Local Date Time");
        System.out.println(ldt);
        System.out.println("Zoned Date Time");
        System.out.println(zdt);
        System.out.println("Zoned Date Time Converted");
        System.out.println(utczdt);
        System.out.println("To Local Date TIme");
        System.out.println(ldtIn);
        System.out.println(zdtOut);
        System.out.println(zdtOutToLocalTZ);
        System.out.println(ldtOutFinal);
    }
}
