package ru.sberschool.secretsanta.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Вспомогательный класс для работы с датой
 */
public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Метод для конвретации даты в sql.Date формат
     *
     * @param now Дата которую надо конвретировать
     * @return Конвретированная дата
     */
    public static Date convertDateToSqlDate(LocalDateTime now) {
        if (now == null) {
            throw new IllegalArgumentException("LocalDateTime cannot be null");
        }
        java.util.Date currentDate = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        return new Date(currentDate.getTime());
    }

}
