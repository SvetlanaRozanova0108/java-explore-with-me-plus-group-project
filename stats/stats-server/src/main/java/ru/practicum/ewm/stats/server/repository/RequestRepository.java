package ru.practicum.ewm.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.stats.server.model.Request;
import ru.practicum.ewm.stats.server.model.StatsRequest;

import java.time.Instant;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select new ru.practicum.ewm.stats.server.model.StatsRequest(r.app, r.uri, count(distinct(r.ip))) " +
            "from Request AS r " +
            "where r.createdDate >= :start AND r.createdDate <= :end AND uri in (:uris) " +
            "group by r.app, r.uri")
    List<StatsRequest> getStatsByUriWithUniqueIp(@Param("start") Instant start, @Param("end") Instant end,
                                                 @Param("uris") List<String> uris);


    @Query("select new ru.practicum.ewm.stats.server.model.StatsRequest(r.app, r.uri, count(distinct(r.ip))) " +
            "from Request AS r " +
            "where r.createdDate >= :start AND r.createdDate <= :end " +
            "group by r.app, r.uri")
    List<StatsRequest> getStatsWithUniqueIp(@Param("start") Instant start, @Param("end") Instant end);

    @Query("select new ru.practicum.ewm.stats.server.model.StatsRequest(r.app, r.uri, count(r)) " +
            "from Request AS r " +
            "where r.createdDate >= :start AND r.createdDate <= :end AND uri in (:uris)" +
            "group by r.app, r.uri")
    List<StatsRequest> getStatsByUri(@Param("start") Instant start, @Param("end") Instant end,
                                     @Param("uris") List<String> uris);

    @Query("select new ru.practicum.ewm.stats.server.model.StatsRequest(r.app, r.uri, count(r)) " +
            "from Request AS r " +
            "where r.createdDate >= :start AND r.createdDate <= :end " +
            "group by r.app, r.uri")
    List<StatsRequest> getStats(@Param("start") Instant start, @Param("end") Instant end);

}
