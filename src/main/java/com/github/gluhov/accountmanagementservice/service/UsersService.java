package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.exception.EntityNotFoundException;
import com.github.gluhov.accountmanagementservice.mapper.AddressesMapper;
import com.github.gluhov.accountmanagementservice.mapper.UsersMapper;
import com.github.gluhov.accountmanagementservice.model.Status;
import com.github.gluhov.accountmanagementservice.model.Users;
import com.github.gluhov.accountmanagementservice.repository.UsersRepository;
import com.github.gluhov.dto.AddressesDto;
import com.github.gluhov.dto.UsersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final AddressesService addressesService;
    private final AddressesMapper addressesMapper;
    private final UsersMapper usersMapper;

    public Mono<UsersDto> getById(UUID uuid) {
        return usersRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found", "AMS_USER_NOT_FOUND")))
                .flatMap(this::constructUsersDto);
    }
    public Mono<UsersDto> save(UsersDto usersDto) {
        Users users = usersMapper.map(usersDto);
        return addressesService.save(users.getAddresses())
                .flatMap(savedAddress -> usersRepository.save(
                        Users.builder()
                                .addressId(savedAddress.getId())
                                .addresses(savedAddress)
                                .filled(users.isFilled())
                                .firstName(users.getFirstName())
                                .lastName(users.getLastName())
                                .secretKey(users.getSecretKey())
                                .build())
                        .doOnSuccess(u -> log.info("In save - user: {} saved", u))
                        .flatMap(this::constructUsersDto)
                );
    }

    private Mono<UsersDto> constructUsersDto(Users users) {
        return addressesService.getById(users.getAddressId())
                .flatMap(address -> {
                    UsersDto usersDto = usersMapper.map(users);
                    AddressesDto addressesDto = addressesMapper.map(address);
                    usersDto.setAddresses(addressesDto);
                    return Mono.just(usersDto);
                });
    }

    public Mono<UsersDto> update(UsersDto usersDto) {
        Users users = usersMapper.map(usersDto);
        return usersRepository.findById(usersDto.getId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found", "AMD_USER_NOT_FOUND")))
                .flatMap(savedUser -> addressesService.update(users.getAddresses())
                        .flatMap(updatedAddress -> usersRepository.save(
                                        Users.builder()
                                                .id(savedUser.getId())
                                                .updated(LocalDateTime.now())
                                                .lastName(users.getLastName())
                                                .firstName(users.getFirstName())
                                                .secretKey(users.getSecretKey())
                                                .addresses(updatedAddress)
                                                .filled(users.isFilled())
                                                .secretKey(users.getSecretKey())
                                                .build())
                                .doOnSuccess(u -> log.info("In update - user: {} updated", u))
                                .flatMap(this::constructUsersDto)
                        ));
    }

    public Mono<Void> deleteById(UUID uuid) {
        return getById(uuid)
                .flatMap(savedUserDto -> addressesService.deleteById(savedUserDto.getAddresses().getId())
                        .flatMap(updatedAddress -> usersRepository.save(
                                Users.builder()
                                        .id(savedUserDto.getId())
                                        .status(Status.ARCHIVED)
                                        .archivedAt(LocalDateTime.now())
                                        .build())
                                .doOnSuccess(u -> log.info("In delete - user: {} deleted", u))
                        )).then();
    }
}