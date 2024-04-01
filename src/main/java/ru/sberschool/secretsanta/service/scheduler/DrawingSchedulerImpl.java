package ru.sberschool.secretsanta.service.scheduler;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.service.ResultService;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.utils.DateUtils;
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
