package org.example.secretsanta.service.scheduler;

import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.example.secretsanta.service.ResultService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrawingSchedulerTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ResultService resultService;

    @InjectMocks
    private DrawingScheduler drawingScheduler;

    @Test
    public void testScheduleDrawings() {
        RoomEntity room1 = new RoomEntity();
        RoomEntity room2 = new RoomEntity();
        List<RoomEntity> roomsToDraw = Arrays.asList(room1, room2);

        when(roomRepository.findByDrawDateLessThanEqual(any(Date.class))).thenReturn(roomsToDraw);

        drawingScheduler.scheduleDrawings();

        verify(resultService, times(roomsToDraw.size())).performDraw(any(RoomEntity.class));
    }
}