package ru.sberschool.secretsanta.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Вспомогательный класс для работы с датой
 */
public class DateUtils {

    /**
     * Метод для конвретации даты в sql.Date формат
     *
     * @param now Дата которую надо конвретировать
     * @return Конвретированная дата
     */
    public static Date convertDateToSqlDate(LocalDateTime now) {
        java.util.Date currentDate = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        return new Date(currentDate.getTime());
    }

}
