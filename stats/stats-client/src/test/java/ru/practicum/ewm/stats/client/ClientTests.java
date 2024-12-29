//package ru.practicum.ewm.stats.client;
//
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.practicum.ewm.stats.client.client.StatClientImpl;
//import ru.practicum.ewm.stats.dto.EndpointHitDto;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//
//@SpringBootTest
//@AllArgsConstructor(onConstructor_ = @Autowired)
//public class ClientTests {
//
//    private final StatClientImpl statClient;
//
//    @Test
//    public void saveHitTest() {
//        var hit = EndpointHitDto.builder()
//                .app("ewm-main-service")
//                .uri("/events/3")
//                .timestamp(LocalDateTime.now())
//                .ip("192.163.0.1")
//                .build();
//
//        assertDoesNotThrow(() -> statClient.saveHit(hit));
//    }
//
//    @Test
//    public void getStatsTest() {
//        var sut = statClient.getStats(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), List.of(""), false);
//        assertNotNull(sut);
//    }
//}
