package ru.practicum.ewm.stats.server.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.server.model.EndpointHit;
import ru.practicum.ewm.stats.server.model.StatsRequest;

import java.util.List;

@UtilityClass
public class DtoMapper {

    public StatsDto toStatsDto(StatsRequest statsRequest) {
        return StatsDto.builder()
                .app(statsRequest.getApp())
                .uri(statsRequest.getUri())
                .hits((int) statsRequest.getHits()).build();
    }

    public List<StatsDto> toStatsDto(List<StatsRequest> stats) {
        return stats.stream().map(DtoMapper::toStatsDto).toList();
    }

    public EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .createdDate(endpointHitDto.getTimestamp())
                .build();
    }
}
