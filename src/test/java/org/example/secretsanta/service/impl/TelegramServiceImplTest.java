package org.example.secretsanta.service.impl;

import org.example.secretsanta.telegram.SantaTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TelegramServiceImplTest {
    @Mock
    private SantaTelegramBot bot;

    @InjectMocks
    private TelegramServiceImpl telegramService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendMessageTest() {
        Long idChat = 123456789L;
        String message = "Test message";

        telegramService.sendMessage(idChat, message);

        verify(bot, times(1)).sendMessage(idChat, message);
    }
}