package org.example.secretsanta.service.scheduler;

import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.example.secretsanta.service.ResultService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class DrawingScheduler {

    private final RoomRepository roomRepository;
    private final ResultService resultService;

    public DrawingScheduler(RoomRepository roomRepository, ResultService resultService) {
        this.roomRepository = roomRepository;
        this.resultService = resultService;
    }

    @Scheduled(fixedDelay =24 * 60 * 60 * 1000)
    public void scheduleDrawings() {
        LocalDateTime now = LocalDateTime.now();
        java.util.Date currentDate = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date sqlDate = new Date(currentDate.getTime());

        List<RoomEntity> roomsToDraw = roomRepository.findByDrawDateLessThanEqual(sqlDate);
        for (RoomEntity room : roomsToDraw) {
            resultService.performDraw(room);
        }
    }

}
