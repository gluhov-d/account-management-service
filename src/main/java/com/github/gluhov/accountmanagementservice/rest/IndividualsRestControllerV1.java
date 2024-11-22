package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.IndividualsService;
import com.github.gluhov.dto.IndividualsDto;
import com.github.gluhov.dto.IndividualsDtoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.rest.IndividualsRestControllerV1.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class IndividualsRestControllerV1 {
    public static final String REST_URL = "/api/v1/individuals";
    private final IndividualsService individualsService;

    @GetMapping(value = "/{id}/details")
    public Mono<?> getById(@PathVariable UUID id) {
        return individualsService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> delete(@PathVariable UUID id) {
        return individualsService.deleteById(id);
    }

    @PostMapping
    public Mono<?> create(@RequestBody IndividualsDto individualsDto) {
        return individualsService.save(individualsDto);
    }

    @PutMapping
    public Mono<?> update(@RequestBody IndividualsDto individualsDto) {
        return individualsService.update(individualsDto).map(individualDto -> ResponseEntity.ok().body(individualDto));
    }

    @GetMapping(value = "/list")
    public Mono<?> getAll() {
        return individualsService.getAll()
                .collectList()
                .map(individualsDtos -> ResponseEntity.ok().body(new IndividualsDtoListResponse(individualsDtos)));
    }
}