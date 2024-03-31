package org.example.secretsanta.service.scheduler;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.example.secretsanta.service.ResultService;
import org.example.secretsanta.service.RoomService;
import org.example.secretsanta.service.impl.ResultServiceImpl;
import org.example.secretsanta.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrawingSchedulerImplTest {

    @Mock
    private RoomService roomService;

    @Mock
    private ResultService resultService;

    @InjectMocks
    private DrawingSchedulerImpl drawingSchedulerImpl;

    @Test
    void testScheduleDrawings() {
        RoomDTO room1 = new RoomDTO();
        RoomDTO room2 = new RoomDTO();
        List<RoomDTO> roomsToDraw = Arrays.asList(room1, room2);
        when(roomService.getByDrawDateLessThanEqual(any())).thenReturn(roomsToDraw);

        drawingSchedulerImpl.scheduleDrawings();

        verify(resultService, times(roomsToDraw.size())).performDraw(any(RoomDTO.class));
    }
}
