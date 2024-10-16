package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.ProfileHistoryService;
import com.github.gluhov.dto.ProfileHistoryDtoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.rest.ProfileHistoryRestControllerV1.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileHistoryRestControllerV1 {
    public static final String REST_URL = "/api/v1/profile";
    private final ProfileHistoryService profileHistoryService;

    @GetMapping(value = "/history/{id}/details")
    public Mono<?> getById(@PathVariable UUID id) {
        return profileHistoryService.getById(id).map(profileHistoryDto -> ResponseEntity.ok().body(profileHistoryDto));
    }

    @GetMapping(value = "/{id}/history")
    public Mono<?> getAllByProfileId(@PathVariable UUID id) {
        return profileHistoryService.getAllByProfileId(id)
                .collectList()
                .map(profileHistoryDtos -> ResponseEntity.ok().body(new ProfileHistoryDtoListResponse(profileHistoryDtos)));
    }
}