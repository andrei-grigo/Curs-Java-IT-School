package com.reseatvation.service;

import com.reseatvation.dto.CreateScreeningRequest;
import com.reseatvation.dto.NextSlotResult;
import com.reseatvation.dto.ScreeningResponse;
import com.reseatvation.entity.Movie;
import com.reseatvation.entity.Room;
import com.reseatvation.entity.Screening;
import com.reseatvation.exception.*;
import com.reseatvation.repository.MovieRepository;
import com.reseatvation.repository.RoomRepository;
import com.reseatvation.repository.ScreeningRepository;
import com.reseatvation.util.TimeSlotUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final ScreeningSchedulingService schedulingService;

    public ScreeningService(ScreeningRepository screeningRepository,
                            RoomRepository roomRepository,
                            MovieRepository movieRepository,
                            ScreeningSchedulingService schedulingService) {
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.schedulingService = schedulingService;
    }

    @Transactional
    public ScreeningResponse create(CreateScreeningRequest request) {
        LocalDateTime requestedStart = request.startTime();

        if (!TimeSlotUtils.isAlignedToSlot(requestedStart)) {
            throw new InvalidStartTimeException(requestedStart);
        }

        Room room = roomRepository.findById(request.roomId()).orElseThrow(() -> new RoomNotFoundException(request.roomId()));
        Movie movie = movieRepository.findById(request.movieId()).orElseThrow(() -> new MovieNotFoundException(request.movieId()));

        NextSlotResult next = schedulingService.findNextAvailableSlot(room.getId(), movie.getId(), requestedStart);

        if (!next.available() || !next.suggestedStart().equals(requestedStart.withSecond(0).withNano(0))) {
            throw new ScreeningConflictException("Slot not available", next);
        }

        Screening screening = new Screening(movie, room, next.suggestedStart(), next.suggestedEnd());
        Screening saved = screeningRepository.save(screening);

        return toResponse(saved);
    }

    public ScreeningResponse getById(Long id) {
        Screening s = screeningRepository.findById(id).orElseThrow(() -> new ScreeningNotFoundException(id));
        return toResponse(s);
    }

    public List<ScreeningResponse> getAll() {
        return screeningRepository.findAll().stream().map(this::toResponse).toList();
    }

    private ScreeningResponse toResponse(Screening s) {
        return new ScreeningResponse(
                s.getId(),
                s.getRoom().getId(),
                s.getRoom().getName(),
                s.getMovie().getId(),
                s.getMovie().getTitle(),
                s.getStartTime(),
                s.getEndTime()
        );

    }

    @Transactional
    public String delete(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException(id));

        screeningRepository.delete(screening);

        return screening.getMovie().getTitle()
                + " at "
                + screening.getStartTime();
    }

}