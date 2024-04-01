package ru.sberschool.secretsanta.service.scheduler;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.model.entity.RoomEntity;
import ru.sberschool.secretsanta.repository.RoomRepository;
import ru.sberschool.secretsanta.service.ResultService;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.impl.ResultServiceImpl;
import ru.sberschool.secretsanta.utils.DateUtils;
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
