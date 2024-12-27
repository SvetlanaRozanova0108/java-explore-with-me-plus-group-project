package ru.practicum.ewm.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.server.mapper.DtoMapper;
import ru.practicum.ewm.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(TIME_PATTERN));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(TIME_PATTERN));
        if (!uris.isEmpty() && unique) {
            return statsRepository.getStatsByUriWithUniqueIp(startTime, endTime, uris);
        } else if (uris.isEmpty() && unique) {
            return statsRepository.getStatsWithUniqueIp(startTime, endTime);
        } else if (!uris.isEmpty()) {
            return statsRepository.getStatsByUri(startTime, endTime, uris);
        } else {
            return statsRepository.getStats(startTime, endTime);
        }
    }

    @Transactional
    @Override
    public void saveHit(EndpointHitDto endpointHitDto) {
        statsRepository.save(DtoMapper.toEndpointHit(endpointHitDto));
    }
}
