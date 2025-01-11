package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.enums.SortType;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.user.dto.UserDtoForAdmin;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDto createComment(Long eventId, Long userId, NewCommentDto newCommentDto) {
        Event event = checkEvent(eventId);
        User user = checkUser(userId);
        if (user.getForbiddenCommentEvents().contains(event)) {
            throw new ValidationException("Для данного пользователя стоит запрет на комментирование данной вещи");
        }
        if (!event.getCommenting()) {
            throw new ValidationException("Данное событие нельзя комментировать");
        }
        Comment comment = CommentMapper.toComment(newCommentDto, event, user);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        User user = checkUser(userId);
        Comment comment = checkComment(commentId);
        if (user.getId().equals(comment.getAuthor().getId())) {
            comment.setText(newCommentDto.getText());
        } else {
            throw new ValidationException("Пользователь не оставлял комментарий с указанным Id " + commentId);
        }
        return CommentMapper.toCommentDto(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long userId, Long commentId) {
        User user = checkUser(userId);
        Comment comment = checkComment(commentId);
        if (user.getId().equals(comment.getAuthor().getId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ValidationException("Пользователь не оставлял комментарий с указанным Id " + commentId);
        }
    }

    @Override
    public List<CommentDto> getAllComments(Long eventId, SortType sortType, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<CommentDto> comments = commentRepository.findAllByEvent_Id(eventId, pageable)
                .stream()
                .map(CommentMapper::toCommentDto)
                .toList();
        if (sortType == SortType.LIKES) {
            return comments.stream().sorted(Comparator.comparing(CommentDto::getLikes).reversed()).toList();
        } else {
            return comments.stream().sorted(Comparator.comparing(CommentDto::getCreated).reversed()).toList();
        }
    }

    @Transactional
    @Override
    public CommentDto addLike(Long userId, Long commentId) {
        User commentator = checkUser(userId);
        Comment comment = checkComment(commentId);
        if (comment.getAuthor().getId().equals(userId)) {
            throw new ValidationException("Пользователь не может лайкать свой комментарий");
        }
        if (comment.getLikes().stream().anyMatch(user -> user.getId().equals(userId))) {
            throw new ValidationException("Пользователь уже пролайкал комментарий с id: " + commentId);
        }
        comment.getLikes().add(commentator);
        return CommentMapper.toCommentDto(comment);
    }

    @Transactional
    @Override
    public void deleteLike(Long userId, Long commentId) {
        User commentator = checkUser(userId);
        Comment comment = checkComment(commentId);
        if (!comment.getLikes().remove(commentator)) {
            throw new NotFoundException("Пользователь не лайкал комментарий с id: " + commentId);
        }
    }

    @Transactional
    @Override
    public UserDtoForAdmin addBanCommited(Long userId, Long eventId) {
        User user = checkUser(userId);
        Event forbidEvent = checkEvent(eventId);
        if (user.getForbiddenCommentEvents().stream().anyMatch(event -> event.getId().equals(eventId))) {
            throw new ValidationException("Уже добавлен такой запрет на комментирование");
        }
        user.getForbiddenCommentEvents().add(forbidEvent);
        return UserMapper.toUserDtoForAdmin(user);
    }

    @Transactional
    @Override
    public void deleteBanCommited(Long userId, Long eventId) {
        User user = checkUser(userId);
        Event forbidEvent = checkEvent(eventId);
        if (!user.getForbiddenCommentEvents().remove(forbidEvent)) {
            throw new NotFoundException("Такого запрета на комментирование не найдено");
        }

    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private Event checkEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден"));
    }

}
