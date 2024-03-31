package org.example.secretsanta.service.scheduler;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.service.ResultService;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы шедулера
 */
@Service
public class DrawingSchedulerImpl implements DrawingScheduler {

    private static final int PERIOD = 24 * 60 * 60 * 1000;

    private final RoomService roomService;
    private final ResultService resultService;

    public DrawingSchedulerImpl(RoomService roomService, ResultService resultService) {
        this.roomService = roomService;
        this.resultService = resultService;
    }

    /**
     *Метод для проведения автоматической жеребьевки, когда придет ее время
     */
    @Override
    @Scheduled(fixedDelay = PERIOD)
    public void scheduleDrawings() {
        List<RoomDTO> roomsToDraw = roomService.getByDrawDateLessThanEqual
                (DateUtils.convertDateToSqlDate(LocalDateTime.now()));

        for (RoomDTO room : roomsToDraw) {
            resultService.performDraw(room);
        }
    }

}
