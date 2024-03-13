package org.example.secretsanta.service.scheduler;

import org.example.secretsanta.convertor.DateConvertor;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.example.secretsanta.service.ResultService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DrawingScheduler {

    private final RoomRepository roomRepository;
    private final ResultService resultService;

    public DrawingScheduler(RoomRepository roomRepository, ResultService resultService) {
        this.roomRepository = roomRepository;
        this.resultService = resultService;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void scheduleDrawings() {
        List<RoomEntity> roomsToDraw = roomRepository.
                findByDrawDateLessThanEqual(DateConvertor.convertDateToSqlDate(LocalDateTime.now()));
        for (RoomEntity room : roomsToDraw) {
            resultService.performDraw(RoomMapper.toRoomDTO(room));
        }
    }

}
