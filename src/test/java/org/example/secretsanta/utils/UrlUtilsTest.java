package org.example.secretsanta.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlUtilsTest {

    @Test
    void testExtractRoomNumberFromUrl() {
        String url1 = "Тебя пригласили в комнату Teesss присоединяйся по ссылке http://localhost:8080/room/4/join";
        String url2 = "Тебя пригласили в комнату Teesss присоединяйся по ссылке http://localhost:8080/room/35/join";
        String url3 = "Тебя пригласили в комнату Teesss присоединяйся по ссылке http://localhost:8080/room/2/join";
        String url4 = "Тебя пригласили в комнату Teesss присоединяйся по ссылке http://localhost:8080/room/123/join";
        String url5 = "iusduvbxkjcvbkzxjcvb";

        String expected1 = "4";
        String expected2 = "35";
        String expected3 = "2";
        String expected4 = "123";
        String expected5 = "";

        assertEquals(expected1, UrlUtils.extractRoomNumberFromUrl(url1));
        assertEquals(expected2, UrlUtils.extractRoomNumberFromUrl(url2));
        assertEquals(expected3, UrlUtils.extractRoomNumberFromUrl(url3));
        assertEquals(expected4, UrlUtils.extractRoomNumberFromUrl(url4));
        assertEquals(expected5, UrlUtils.extractRoomNumberFromUrl(url5));
    }
}