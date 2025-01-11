package ru.practicum.ewm.comment.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommentDto {
    Long id;
    String text;
    Long eventId;
    String eventName;
    String authorName;
    Integer likes;
    LocalDateTime created;
}
