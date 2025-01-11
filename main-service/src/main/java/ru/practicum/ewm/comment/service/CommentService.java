package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.enums.SortType;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long eventId, Long userId, NewCommentDto newCommentDto);

    CommentDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto);

    void deleteComment(Long userId, Long commentId);

    List<CommentDto> getAllComments(Long eventId, SortType sortType, Integer from, Integer size);

    CommentDto addLike(Long userId, Long commentId);

    void deleteLike(Long userId, Long commentId);
}
