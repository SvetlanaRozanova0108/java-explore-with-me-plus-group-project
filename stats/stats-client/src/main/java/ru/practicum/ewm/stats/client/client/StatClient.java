package ru.practicum.ewm.stats.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.stats.client.exceptions.RestClientRuntimeException;
import ru.practicum.ewm.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatClient {

    private final RestClient restClient;
    private final String serverHost;
    private final String shema;

    @Autowired
    public StatClient(@Value("${ewm-server.host}") String serverHost, @Value("${ewm-server.shema}") String serverShema) {
        this.serverHost = serverHost;
        this.shema = serverShema;
        this.restClient = RestClient.builder().build();
    }

    public void saveHit(Object requestBody) {

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("/hit")
                .scheme(shema)
                .host(serverHost)
                .build();

        restClient.post()
                .uri(uriComponents.toUriString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RestClientRuntimeException(response.getStatusCode(), response.getBody().toString());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RestClientRuntimeException(response.getStatusCode(), response.getBody().toString());
                });

    }

    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        Map<String, Object> var = Map.of("start", start, "end", end, "uris", uris, "unique", unique);
        var result = new ArrayList<StatsDto>();

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("/stats?start={start}&end={end}&uris={uris}&unique={unique}")
                .scheme(shema)
                .host(serverHost)
                .build();
        uriComponents = uriComponents.expand(var);

        result = restClient.get()
                .uri(uriComponents.toUriString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RestClientRuntimeException(response.getStatusCode(), response.getBody().toString());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RestClientRuntimeException(response.getStatusCode(), response.getBody().toString());
                })
                .body(result.getClass());
        return result;

    }


}
