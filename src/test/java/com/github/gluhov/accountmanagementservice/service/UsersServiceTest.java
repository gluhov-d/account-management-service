package com.github.gluhov.accountmanagementservice.service;

import com.github.gluhov.accountmanagementservice.mapper.AddressesMapper;
import com.github.gluhov.accountmanagementservice.mapper.UsersMapper;
import com.github.gluhov.accountmanagementservice.model.Addresses;
import com.github.gluhov.accountmanagementservice.model.Users;
import com.github.gluhov.accountmanagementservice.repository.UsersRepository;
import com.github.gluhov.dto.UsersDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.gluhov.accountmanagementservice.service.AddressesData.addressMerchantTestData;
import static com.github.gluhov.accountmanagementservice.service.AddressesData.addressMerchantTestDataDto;
import static com.github.gluhov.accountmanagementservice.service.UsersData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UsersServiceTest {
    @InjectMocks
    private UsersService usersService;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private AddressesService addressesService;
    @Mock
    private AddressesMapper addressesMapper;
    @Mock
    private UsersMapper usersMapper;

    @Test
    @DisplayName("Test get by id functionality then success response")
    void givenId_whenGetById_thenSuccessResponse() {
        when(usersRepository.findById(USER_MERCHANT_UUID)).thenReturn(Mono.just(userMerchantTestData));
        when(addressesService.getById(userMerchantTestData.getAddressId())).thenReturn(Mono.just(addressMerchantTestData));
        when(addressesMapper.map(any(Addresses.class))).thenReturn(addressMerchantTestDataDto);
        when(usersMapper.map(any(Users.class))).thenReturn(userMerchantTestDataDto);

        Mono<UsersDto> result = usersService.getById(USER_MERCHANT_UUID);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getFirstName(), userMerchantTestDataDto.getFirstName());
                    assertEquals(r.getLastName(), userMerchantTestDataDto.getLastName());
                    assertEquals(r.getAddressId(), userMerchantTestDataDto.getAddressId());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test delete functionality then success response")
    void givenId_whenDeleteById_thenSuccessResponse() {
        when(addressesService.deleteById(userMerchantTestData.getAddressId())).thenReturn(Mono.empty());
        when(usersRepository.save(any(Users.class))).thenReturn(Mono.just(userMerchantTestData));
        when(usersRepository.findById(USER_MERCHANT_UUID)).thenReturn(Mono.just(userMerchantTestData));
        when(addressesService.getById(userMerchantTestData.getAddressId())).thenReturn(Mono.just(addressMerchantTestData));
        when(addressesMapper.map(any(Addresses.class))).thenReturn(addressMerchantTestDataDto);
        when(usersMapper.map(any(Users.class))).thenReturn(userMerchantTestDataDto);

        Mono<?> result = usersService.deleteById(USER_MERCHANT_UUID);
        StepVerifier.create(result)
                .expectNextCount(0).verifyComplete();
    }
    @Test
    @DisplayName("Test save functionality then success response")
    void givenUserData_whenSave_thenSuccessResponse() {
        when(usersMapper.map(any(Users.class))).thenReturn(userMerchantTestDataDto);
        when(usersMapper.map(any(UsersDto.class))).thenReturn(userMerchantTestData);
        when(usersRepository.save(any(Users.class))).thenReturn(Mono.just(userMerchantTestData));
        when(addressesService.save(any(Addresses.class))).thenReturn(Mono.just(addressMerchantTestData));
        when(addressesService.getById(userMerchantTestData.getAddressId())).thenReturn(Mono.just(addressMerchantTestData));
        when(addressesMapper.map(any(Addresses.class))).thenReturn(addressMerchantTestDataDto);

        Mono<UsersDto> result = usersService.save(userMerchantTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getFirstName(), userMerchantTestDataDto.getFirstName());
                    assertEquals(r.getLastName(), userMerchantTestDataDto.getLastName());
                    assertEquals(r.getAddressId(), userMerchantTestDataDto.getAddressId());
                }).verifyComplete();
    }
    @Test
    @DisplayName("Test update functionality then success response")
    void givenUserData_whenUpdate_thenSuccessResponse() {
        userMerchantTestDataDto.setFirstName("New name");
        when(usersMapper.map(any(UsersDto.class))).thenReturn(userMerchantTestData);
        when(usersRepository.findById(USER_MERCHANT_UUID)).thenReturn(Mono.just(userMerchantTestData));
        when(addressesService.update(any(Addresses.class))).thenReturn(Mono.just(addressMerchantTestData));
        when(usersRepository.save(any(Users.class))).thenReturn(Mono.just(userMerchantTestData));
        when(addressesService.getById(userMerchantTestData.getAddressId())).thenReturn(Mono.just(addressMerchantTestData));
        when(addressesMapper.map(any(Addresses.class))).thenReturn(addressMerchantTestDataDto);
        when(usersMapper.map(any(Users.class))).thenReturn(userMerchantTestDataDto);

        Mono<UsersDto> result = usersService.update(userMerchantTestDataDto);
        StepVerifier.create(result)
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(r.getFirstName(), userMerchantTestDataDto.getFirstName());
                    assertEquals(r.getLastName(), userMerchantTestDataDto.getLastName());
                    assertEquals(r.getAddressId(), userMerchantTestDataDto.getAddressId());
                }).verifyComplete();
    }
}