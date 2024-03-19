package org.example.secretsanta.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    public void testConvertDateToSqlDate() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 31, 23, 59);

        Date expectedDate1 = convertLocalDateTimeToSqlDate(localDateTime1);
        Date expectedDate2 = convertLocalDateTimeToSqlDate(localDateTime2);

        assertEquals(expectedDate1, DateUtils.convertDateToSqlDate(localDateTime1));
        assertEquals(expectedDate2, DateUtils.convertDateToSqlDate(localDateTime2));
    }

    private Date convertLocalDateTimeToSqlDate(LocalDateTime localDateTime) {
        java.util.Date currentDate = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return new Date(currentDate.getTime());
    }
}