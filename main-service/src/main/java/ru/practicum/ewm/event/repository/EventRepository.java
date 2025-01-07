package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long userId, PageRequest pageRequest);

    Boolean existsByCategoryId(Long catId);

    List<Event> findAllByStateAndDescriptionLikeAndAnnotationLikeAndCreatedOnAfterOrderByCreatedOn(State state, String description, String annotation, LocalDateTime createdOn);

    List<Event> findAllByStateAndDescriptionLikeAndAnnotationLikeAndCreatedOnAfterAndCreatedOnBeforeOrderByCreatedOn(State state, String description, String annotation, LocalDateTime rangeStart, LocalDateTime rangeEnd);

    List<Event> findAllByInitiatorIdContainsAndStateContainsAndCategoryContainsAndCreatedOnAfterOrderByCreatedOn(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime createdOn, Pageable pageable);

    List<Event> findAllByInitiatorIdContainsAndStateContainsAndCategoryContainsAndCreatedOnAfterAndCreatedOnBeforeOrderByCreatedOn(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}

