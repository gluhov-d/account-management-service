package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.ProfileHistoryMapper;
import com.github.gluhov.accountmanagementservice.model.ProfileHistory;
import com.github.gluhov.accountmanagementservice.repository.ProfileHistoryRepository;
import com.github.gluhov.dto.ProfileHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileHistoryService {
    private final ProfileHistoryRepository profileHistoryRepository;
    private final ProfileHistoryMapper profileHistoryMapper;
    private final UsersService usersService;

    public Mono<ProfileHistoryDto> save(ProfileHistory profileHistory) {
        return profileHistoryRepository.save(profileHistory)
                .flatMap(this::constructProfileHistoryDto)
                .doOnSuccess(p -> log.info("In save - profile history: {} saved", p));
    }

    public Mono<ProfileHistoryDto> getById(UUID uuid) {
        return profileHistoryRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Profile history not found", "AMS_PROFILE_HISTORY_NOT_FOUND")))
                .flatMap(this::constructProfileHistoryDto);
    }

    private Mono<ProfileHistoryDto> constructProfileHistoryDto(ProfileHistory profileHistory) {
        return usersService.getById(profileHistory.getProfileId())
                .flatMap(savedUserDto -> {
                    ProfileHistoryDto profileHistoryDto = profileHistoryMapper.map(profileHistory);
                    profileHistoryDto.setUser(savedUserDto);
                    return Mono.just(profileHistoryDto);
                });
    }

    public Flux<ProfileHistoryDto> getAllByProfileId(UUID uuid) {
        return profileHistoryRepository.getAllByProfileId(uuid)
                .flatMap(this::constructProfileHistoryDto);
    }
}