package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.UpdateObject;
import ru.practicum.ewm.event.enums.StateAction;
import ru.practicum.ewm.event.model.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventAdminRequest {

    @Size(min = 20, max = 2000, message = "Для описания требуется от 20 до 2000 символов.", groups = {UpdateObject.class})
    String annotation;

    Long category;

    @Size(min = 20, max = 7000, message = "Для описания требуется от 20 до 7000 символов.", groups = {UpdateObject.class})
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @Embedded
    Location location;

    Boolean paid;

    @PositiveOrZero
    Integer participantLimit;

    Boolean requestModeration;

    StateAction stateAction;

    @Size(min = 3, max = 120)
    String title;
}
