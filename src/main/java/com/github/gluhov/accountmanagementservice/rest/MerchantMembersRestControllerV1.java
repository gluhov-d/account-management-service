package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.MerchantMembersService;
import com.github.gluhov.dto.MerchantMembersDto;
import com.github.gluhov.dto.MerchantMembersDtoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.rest.MerchantMembersRestControllerV1.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantMembersRestControllerV1 {
    public static final String REST_URL = "/api/v1/merchant";
    private final MerchantMembersService merchantMembersService;

    @GetMapping(value = "/members/{id}/details")
    public Mono<?> getById(@PathVariable UUID id) {
        return merchantMembersService.getById(id).map(merchantMemberDto -> ResponseEntity.ok().body(merchantMemberDto));
    }

    @GetMapping(value = "/{id}/members/list")
    public Mono<?> getAllByMerchantId(@PathVariable UUID id) {
        return merchantMembersService.getAllByMerchantId(id)
                .collectList()
                .map(merchantMembersDtos -> ResponseEntity.ok().body(new MerchantMembersDtoListResponse(merchantMembersDtos)));
    }

    @DeleteMapping(value = "/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> delete(@PathVariable UUID id) { return merchantMembersService.deleteById(id);}

    @PostMapping(value = "/members")
    public Mono<?> create(@RequestBody MerchantMembersDto merchantMembersDto) {
        return merchantMembersService.save(merchantMembersDto).map(merchantMemberDto -> ResponseEntity.ok().body(merchantMemberDto));
    }

    @PutMapping(value = "/members")
    public Mono<?> update(@RequestBody MerchantMembersDto merchantMembersDto) {
        return merchantMembersService.update(merchantMembersDto).map(merchantMemberDto -> ResponseEntity.ok().body(merchantMemberDto));
    }
}