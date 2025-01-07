package ru.practicum.ewm.event.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventAdminFilter;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.InvalidDateTimeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public List<EventFullDto> getEventsForAdmin(@RequestParam(required = false) ArrayList<Integer> users,
                                                @RequestParam(required = false) ArrayList<String> states,
                                                @RequestParam(required = false) ArrayList<Integer> categories,
                                                @RequestParam(required = false) String rangeStart,
                                                @RequestParam(required = false) String rangeEnd,
                                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получение полной информации обо всех событиях подходящих под переданные условия.");

        var filter = EventAdminFilter
                .builder()
                .users(users)
                .states(states)
                .categories(categories)
                .from(from)
                .size(size)
                .build();

        if (rangeStart != null && rangeEnd != null) {
            filter.setRangeStart(LocalDateTime.parse(rangeStart, dateTimeFormatter));
            filter.setRangeEnd(LocalDateTime.parse(rangeEnd, dateTimeFormatter));
            if (!filter.getRangeStart().isBefore(filter.getRangeEnd())) {
                throw new InvalidDateTimeException("Дата окончания события не может быть раньше даты начала события.");
            }
        }

        return eventService.getEventsForAdmin(filter);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PositiveOrZero @PathVariable Long eventId,
                               @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Редактирование данных любого события администратором.");
        return eventService.updateEventAdmin(eventId, updateEventAdminRequest);
    }
}
