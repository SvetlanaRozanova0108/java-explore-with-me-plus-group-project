package ru.practicum.ewm.stats.server.service;

import ru.practicum.ewm.stats.dto.StatsDto;

import java.time.Instant;
import java.util.List;

public interface RequestService {

    List<StatsDto> getStats(Instant start, Instant end, List<String> uris, boolean unique);
}
