package com.reseatvation.repository;

import com.reseatvation.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByRoomIdAndStartTimeBetweenOrderByStartTimeAsc(
            Long roomId, LocalDateTime from, LocalDateTime to
    );

    boolean existsByMovieId(Long movieId);

    boolean existsByRoomId(Long roomId);
}
