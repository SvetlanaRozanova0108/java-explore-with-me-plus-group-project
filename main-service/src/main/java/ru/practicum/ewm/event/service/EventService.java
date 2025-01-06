package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.partrequest.dto.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    EventFullDto addEvent(NewEventDto eventDto, Long userId);

    List<EventShortDto> getEventsOfUser(Long userId, Integer from, Integer size);

    EventFullDto getEventOfUser(Long userId, Long eventId);

    EventFullDto updateEventOfUser(UpdateEventUserRequest updateRequest, Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsOfUserEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> updateRequestsStatus(EventRequestStatusUpdateRequest updateRequest, Long userId,
                                                       Long eventId);
}
