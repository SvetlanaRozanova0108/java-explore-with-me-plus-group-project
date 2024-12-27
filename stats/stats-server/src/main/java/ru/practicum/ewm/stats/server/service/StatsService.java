package ru.practicum.ewm.stats.server.service;

import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.StatsDto;

import java.util.List;

public interface StatsService {

    List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique);

    void saveHit(EndpointHitDto endpointHitDto);
}
