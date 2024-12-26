package ru.practicum.ewm.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.server.service.RequestService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/stats")
@Slf4j
public class StatsController {
    private final RequestService requestService;
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @GetMapping
    private List<StatsDto> getStats(@RequestParam @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime start,
                                    @RequestParam @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime end,
                                    @RequestParam(defaultValue = "") List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Поступил запрос на получение статистики запросов c параметрами start: {}, end {}, uris {}, unique {}",
                start, end, uris, unique);
        return requestService.getStats(start.atZone(ZoneId.systemDefault()).toInstant(),
                end.atZone(ZoneId.systemDefault()).toInstant(), uris, unique);
    }
}
