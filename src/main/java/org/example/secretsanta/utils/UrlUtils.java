package org.example.secretsanta.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    public static String extractRoomNumberFromUrl (String url) {

        Pattern pattern = Pattern.compile("room/(\\d+)/join");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }

    }

}
