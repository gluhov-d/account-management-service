package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.MerchantsService;
import com.github.gluhov.dto.MerchantsDto;
import com.github.gluhov.dto.MerchantsDtoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.rest.MerchantsRestControllerV1.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantsRestControllerV1 {
    public static final String REST_URL = "/api/v1/merchants";
    private final MerchantsService merchantsService;

    @GetMapping(value = "/{id}/details")
    public Mono<?> getById(@PathVariable UUID id) {
        return merchantsService.getById(id).map(merchantDto -> ResponseEntity.ok().body(merchantDto));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> delete(@PathVariable UUID id) {
        return merchantsService.deleteById(id);
    }

    @PostMapping
    public Mono<?> create(@RequestBody MerchantsDto merchantsDto) {
        return merchantsService.save(merchantsDto).map(merchantDto -> ResponseEntity.ok().body(merchantDto));
    }

    @PutMapping
    public Mono<?> update(@RequestBody MerchantsDto merchantsDto) {
        return merchantsService.update(merchantsDto).map(merchantDto -> ResponseEntity.ok().body(merchantDto));
    }

    @GetMapping(value = "/list")
    public Mono<?> getAll() {
        return merchantsService.getAll()
                .collectList()
                .map(merchantsDtos -> ResponseEntity.ok().body(new MerchantsDtoListResponse(merchantsDtos)));
    }
}