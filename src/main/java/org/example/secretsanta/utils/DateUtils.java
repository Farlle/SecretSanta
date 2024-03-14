package org.example.secretsanta.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    public static Date convertDateToSqlDate (LocalDateTime now) {
        java.util.Date currentDate = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date sqlDate = new Date(currentDate.getTime());
        return sqlDate;
    }

}
