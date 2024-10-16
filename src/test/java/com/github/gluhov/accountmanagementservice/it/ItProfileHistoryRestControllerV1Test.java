package com.github.gluhov.accountmanagementservice.it;

import com.github.gluhov.accountmanagementservice.config.PostgreSqlTestContainerConfig;
import com.github.gluhov.accountmanagementservice.rest.ProfileHistoryRestControllerV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.gluhov.accountmanagementservice.service.ProfileHistoryData.*;
import static com.github.gluhov.accountmanagementservice.service.UsersData.USER_MERCHANT_UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import({PostgreSqlTestContainerConfig.class})
@ActiveProfiles("test")
public class ItProfileHistoryRestControllerV1Test extends AbstractRestControllerTest {
    private final String REST_URL = ProfileHistoryRestControllerV1.REST_URL;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Test get info functionality")
    void givenId_whenGetById_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/history/" + PROFILE_HISTORY_UUID + "/details")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.reason").isEqualTo(profileHistoryTestData.getReason())
                .jsonPath("$.body.comment").isEqualTo(profileHistoryTestData.getComment())
                .jsonPath("$.body.changed_values").isEqualTo(profileHistoryTestData.getChangedValues())
                .jsonPath("$.body.profile_type").isEqualTo(profileHistoryTestData.getProfileType())
                .jsonPath("$.body.profile_id").isEqualTo(profileHistoryTestData.getProfileId().toString());
    }

    @Test
    @DisplayName("Test get info functionality then not found")
    void givenId_whenGetById_thenNotFoundResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/history/" + PROFILE_HISTORY_NOT_FOUND_UUID + "/details")
                .exchange();

        resp.expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.errors[0].code").isEqualTo("AMS_PROFILE_HISTORY_NOT_FOUND")
                .jsonPath("$.errors[0].message").isEqualTo("Profile history not found");
    }

    @Test
    @DisplayName("Test get all profile history info by profile id functionality then success response")
    void givenProfileId_whenGetAllByProfileId_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL +"/" + USER_MERCHANT_UUID + "/history")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.profile_history_dto_list").isNotEmpty();
    }
}