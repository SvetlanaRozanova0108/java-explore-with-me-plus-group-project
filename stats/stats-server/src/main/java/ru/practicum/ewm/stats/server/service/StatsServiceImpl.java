package ru.practicum.ewm.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.server.mapper.DtoMapper;
import ru.practicum.ewm.stats.server.model.StatsRequest;
import ru.practicum.ewm.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;


    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<StatsRequest> statsRequests;
        if (!uris.isEmpty() && unique) {
            statsRequests = statsRepository.getStatsByUriWithUniqueIp(start, end, uris);
        } else if (uris.isEmpty() && unique) {
            statsRequests = statsRepository.getStatsWithUniqueIp(start, end);
        } else if (!uris.isEmpty()) {
            statsRequests = statsRepository.getStatsByUri(start, end, uris);
        } else {
            statsRequests = statsRepository.getStats(start, end);
        }
        return DtoMapper.toStatsDto(statsRequests);
    }

    @Transactional
    @Override
    public void saveHit(EndpointHitDto endpointHitDto) {
        statsRepository.save(DtoMapper.toEndpointHit(endpointHitDto));
    }
}
