package ru.practicum.ewm.stats.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class StatsDto {
     String app;
     String uri;
     int hits;
}
