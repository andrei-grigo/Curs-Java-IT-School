package com.reseatvation.service;

import com.reseatvation.dto.CreateRoomRequest;
import com.reseatvation.dto.RoomResponse;
import com.reseatvation.entity.Room;
import com.reseatvation.exception.DuplicateRoomNameException;
import com.reseatvation.exception.RoomHasScreeningsException;
import com.reseatvation.exception.RoomNotFoundException;
import com.reseatvation.repository.RoomRepository;
import com.reseatvation.repository.ScreeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    public RoomService(RoomRepository roomRepository, ScreeningRepository screeningRepository) {
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return toResponse(room);
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RoomResponse createRoom(CreateRoomRequest request) {
        roomRepository.findByName(request.name())
                .ifPresent(r -> {
                    throw new DuplicateRoomNameException(request.name());
                });

        Room room = new Room(request.name(), request.rowsCount(), request.seatsPerRow());
        Room saved = roomRepository.save(room);

        return toResponse(saved);
    }

    @Transactional
    public String deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        if (screeningRepository.existsByRoomId(id)) {
            throw new RoomHasScreeningsException(id);
        }

        roomRepository.delete(room);
        return room.getName();
    }


    private RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getRowsCount(),
                room.getSeatsPerRow()
        );
    }
}
