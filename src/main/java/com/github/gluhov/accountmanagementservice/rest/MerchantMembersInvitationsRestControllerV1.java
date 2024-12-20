package com.github.gluhov.accountmanagementservice.rest;

import com.github.gluhov.accountmanagementservice.service.MerchantMembersInvitationsService;
import com.github.gluhov.dto.MerchantMembersInvitationsDto;
import com.github.gluhov.dto.MerchantMembersInvitationsDtoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.github.gluhov.accountmanagementservice.rest.MerchantMembersInvitationsRestControllerV1.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantMembersInvitationsRestControllerV1 {
    public static final String REST_URL = "/api/v1/merchant/member";
    private final MerchantMembersInvitationsService merchantMembersInvitationsService;

    @GetMapping(value = "/invitations/{id}/details")
    public Mono<?> getById(@PathVariable UUID id) {
        return merchantMembersInvitationsService.getById(id).map(merchantMembersInvitationsDto -> ResponseEntity.ok().body(merchantMembersInvitationsDto));
    }

    @GetMapping(value = "/{id}/list")
    public Mono<?> getAllByMerchantId(@PathVariable UUID id) {
        return merchantMembersInvitationsService.getAllByMerchantId(id)
                .collectList()
                .map(merchantMembersInvitationsDtos -> ResponseEntity.ok().body(new MerchantMembersInvitationsDtoListResponse(merchantMembersInvitationsDtos)));
    }

    @PostMapping(value = "/invitations")
    public Mono<?> create(@RequestBody MerchantMembersInvitationsDto merchantMembersInvitationsDto) {
        return merchantMembersInvitationsService.save(merchantMembersInvitationsDto).map(merchantMemberInvitationDto -> ResponseEntity.ok().body(merchantMemberInvitationDto));
    }

    @DeleteMapping(value = "/invitations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> delete(@PathVariable UUID id) {
        return merchantMembersInvitationsService.deleteById(id);
    }
}