package ru.practicum.ewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.practicum.ewm.stats.client.StatClient;
import ru.practicum.ewm.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootApplication
@Slf4j
public class MainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        //checkStatClient(context);
    }

    private static void checkStatClient(ConfigurableApplicationContext context) {
        StatClient statClient = context.getBean(StatClient.class);

        var hit = EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .timestamp(LocalDateTime.now())
                .ip("192.163.0.1")
                .build();

        String str = statClient.saveHit(hit);
        log.info(str);

        var sut = statClient.getStats(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
                "/events/1", false);
        log.info("Размер списка: {}", sut.size());

        assertNotNull(sut);
    }
}
