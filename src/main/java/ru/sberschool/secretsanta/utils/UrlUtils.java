package ru.sberschool.secretsanta.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Вспомогательный класс для работы с URL
 */
public class UrlUtils {

    /**
     * Метод для извлечения номера комнаты из URL
     *
     * @param url URL
     * @return Номер комнаты
     */
    public static String extractRoomNumberFromUrl(String url) {

        Pattern pattern = Pattern.compile("room/(\\d+)/join");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }

    }

}
