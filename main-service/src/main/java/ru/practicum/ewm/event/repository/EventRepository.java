package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(Long userId, PageRequest pageRequest);

    Boolean existsByCategoryId(Long catId);

    List<Event> findAllByIdIn(List<Long> eventIds);

    List<Event> findAllByStateAndDescriptionContainsOrAnnotationContainsAndEventDateAfterAndPaidAndParticipantLimitGreaterThanOrderByCreatedOn(State state, String description, String annotation, LocalDateTime createdOn, Boolean paid, Integer ParticipantLimit);

    @Query("select e FROM Event e " +
            " where e.state ='PUBLISHED' " +
            "     and (lower(e.description) like :text or lower(e.annotation) like :text)" +
            "     and e.eventDate between :start and :end" +
            "     and e.paid = :paid" +
            "     and e.participantLimit > :participantLimit " +
            "     order by e.createdOn")
    List<Event> getPublicEventsByFilter(String text, LocalDateTime start, LocalDateTime end, Boolean paid, Integer participantLimit);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndCreatedOnAfterOrderByCreatedOn(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime createdOn, Pageable pageable);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndCreatedOnAfterAndCreatedOnBeforeOrderByCreatedOn(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}

