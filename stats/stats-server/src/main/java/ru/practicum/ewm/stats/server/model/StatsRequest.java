package ru.practicum.ewm.stats.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsRequest {
    private String app;
    private String uri;
    private long hits;
}
