package com.reseatvation.controller;

import com.reseatvation.dto.CreateScreeningRequest;
import com.reseatvation.dto.DeleteResponse;
import com.reseatvation.dto.ScreeningResponse;
import com.reseatvation.service.ScreeningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScreeningResponse create(@Valid @RequestBody CreateScreeningRequest request) {
        return screeningService.create(request);
    }

    @GetMapping("/{id}")
    public ScreeningResponse getById(@PathVariable Long id) {
        return screeningService.getById(id);
    }

    @GetMapping
    public List<ScreeningResponse> getAll() {
        return screeningService.getAll();
    }

    @DeleteMapping("/{id}")
    public DeleteResponse delete(@PathVariable Long id) {
        String info = screeningService.delete(id);

        return new DeleteResponse(
                "Screening (" + info + ") was deleted successfully",
                Instant.now()
        );
    }

}