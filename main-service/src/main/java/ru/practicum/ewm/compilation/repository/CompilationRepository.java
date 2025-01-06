package ru.practicum.ewm.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("select c from Compilation c join fetch events where c.pinned = :pinned")
    List<Compilation> findAllCompilationsByPinned(Pageable pageable, @Param("pinned") Boolean pinned);

    @Query("select c from Compilation c join fetch events")
    List<Compilation> findAllCompilations(Pageable pageable);

    @Query("select c from Compilation c join fetch events where c.id = :id")
    Optional<Compilation> findCompilationById(@Param("id") Long id);
}
