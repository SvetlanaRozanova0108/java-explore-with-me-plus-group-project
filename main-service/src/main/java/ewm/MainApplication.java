package ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.practicum.ewm.stats.client.client.StatClientImpl;
import ru.practicum.ewm.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        checkStatClient(context);
    }

    private static void checkStatClient(ConfigurableApplicationContext context) {
        StatClientImpl statClient = context.getBean(StatClientImpl.class);

        var hit = EndpointHitDto.builder()
        .app("ewm-main-service")
        .uri("/events/3")
        .timestamp(LocalDateTime.now())
        .ip("192.163.0.1")
        .build();

        statClient.saveHit(hit);
        var sut = statClient.getStats(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), List.of(""), false);
        assertNotNull(sut);
    }
}
