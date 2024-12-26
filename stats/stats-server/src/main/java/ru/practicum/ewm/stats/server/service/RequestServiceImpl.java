package ru.practicum.ewm.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.server.mapper.DtoMapper;
import ru.practicum.ewm.stats.server.model.StatsRequest;
import ru.practicum.ewm.stats.server.repository.RequestRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    @Override
    public List<StatsDto> getStats(Instant start, Instant end, List<String> uris, boolean unique) {
        List<StatsRequest> statsRequests;
        if (!uris.isEmpty() && unique) {
            statsRequests = requestRepository.getStatsByUriWithUniqueIp(start, end, uris);
        } else if (uris.isEmpty() && unique) {
            statsRequests = requestRepository.getStatsWithUniqueIp(start, end);
        } else if (!uris.isEmpty()) {
            statsRequests = requestRepository.getStatsByUri(start, end, uris);
        } else {
            statsRequests = requestRepository.getStats(start, end);
        }
        return DtoMapper.toStatsDto(statsRequests);
    }
}
