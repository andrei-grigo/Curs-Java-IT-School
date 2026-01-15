package com.reseatvation.service;

import com.reseatvation.config.CinemaRules;
import com.reseatvation.dto.NextSlotResult;
import com.reseatvation.entity.Movie;
import com.reseatvation.entity.Screening;
import com.reseatvation.exception.MovieNotFoundException;
import com.reseatvation.exception.RoomNotFoundException;
import com.reseatvation.repository.MovieRepository;
import com.reseatvation.repository.RoomRepository;
import com.reseatvation.repository.ScreeningRepository;
import com.reseatvation.util.TimeSlotUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningSchedulingService {

    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    public NextSlotResult findNextAvailableSlot(Long roomId, Long movieId, LocalDateTime requestedStart) {
        roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));

        LocalDateTime candidate = TimeSlotUtils.ceilToSlot(requestedStart);
        int durationWithBuffer = movie.getDurationMinutes() + CinemaRules.BUFFER_MINUTES;

        while (true) {
            LocalDate date = candidate.toLocalDate();

            LocalDateTime dayStart = TimeSlotUtils.openingOf(date);
            LocalDateTime dayEnd = TimeSlotUtils.closingOf(date);
            List<Screening> screenings = screeningRepository
                    .findByRoomIdAndStartTimeBetweenOrderByStartTimeAsc(roomId, dayStart, dayEnd);

            if (candidate.isBefore(TimeSlotUtils.openingOf(date))) {
                candidate = TimeSlotUtils.openingOf(date);
            }
            candidate = TimeSlotUtils.ceilToSlot(candidate);

            LocalDateTime candidateEnd = candidate.plusMinutes(durationWithBuffer);

            if (!TimeSlotUtils.fitsWithinOpeningHours(candidate, candidateEnd)) {
                candidate = TimeSlotUtils.openingOf(date.plusDays(1));
                continue;
            }

            Screening conflict = firstOverlap(candidate, candidateEnd, screenings);
            if (conflict == null) {
                return NextSlotResult.found(candidate, candidateEnd);
            }

            candidate = TimeSlotUtils.ceilToSlot(conflict.getEndTime());
        }
    }

    private Screening firstOverlap(LocalDateTime start, LocalDateTime end, List<Screening> list) {
        for (Screening s : list) {
            boolean overlap = s.getStartTime().isBefore(end) && s.getEndTime().isAfter(start);
            if (overlap) return s;
            if (!s.getStartTime().isBefore(end)) break;
        }
        return null;
    }
}
