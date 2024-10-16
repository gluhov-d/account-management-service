package com.github.gluhov.accountmanagementservice.it;

import com.github.gluhov.accountmanagementservice.config.PostgreSqlTestContainerConfig;
import com.github.gluhov.accountmanagementservice.model.Role;
import com.github.gluhov.accountmanagementservice.rest.MerchantMembersRestControllerV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.gluhov.accountmanagementservice.service.MerchantMembersData.*;
import static com.github.gluhov.accountmanagementservice.service.MerchantsData.MERCHANT_UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import({PostgreSqlTestContainerConfig.class})
@ActiveProfiles("test")
public class MerchantMembersRestControllerV1Test extends AbstractRestControllerTest {
    private final String REST_URL = MerchantMembersRestControllerV1.REST_URL;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Test get info functionality")
    void givenId_whenGetById_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/members/" + MERCHANT_MEMBER_UUID + "/details")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.member_role").isEqualTo("MERCHANT_ADMIN")
                .jsonPath("$.body.user_id").isEqualTo(merchantMemberTestData.getUserId().toString())
                .jsonPath("$.body.merchant_id").isEqualTo(newMerchantMemberTestDataDto.getMerchantId().toString());
    }

    @Test
    @DisplayName("Test get info functionality then not found")
    void givenId_whenGetById_thenNotFoundResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/members/" + MERCHANT_MEMBER_NOT_FOUND_UUID + "/details")
                .exchange();

        resp.expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.errors[0].code").isEqualTo("AMS_MERCHANT_MEMBER_NOT_FOUND")
                .jsonPath("$.errors[0].message").isEqualTo("Merchant member not found");
    }

    @Test
    @DisplayName("Test create functionality then success response")
    void givenIndividualData_whenCreate_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.post()
                .uri(REST_URL + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newMerchantMemberTestDataDto)
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.member_role").isEqualTo(newMerchantMemberTestDataDto.getMemberRole())
                .jsonPath("$.body.merchant_id").isEqualTo(merchantMemberTestDataDto.getMerchantId().toString());
    }

    @Test
    @DisplayName("Test update functionality success response")
    void givenIndividualData_whenUpdate_thenSuccessResponse() {
        merchantMemberTestDataDto.setMemberRole(Role.MERCHANT_USER.name());
        WebTestClient.ResponseSpec resp = webTestClient.put()
                .uri(REST_URL + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(merchantMemberTestDataDto)
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.member_role").isEqualTo(merchantMemberTestDataDto.getMemberRole())
                .jsonPath("$.body.merchant_id").isEqualTo(merchantMemberTestDataDto.getMerchantId().toString());
    }

    @Test
    @DisplayName("Test delete functionality success response")
    void givenId_whenDelete_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.delete()
                .uri(REST_URL + "/members/" + MERCHANT_MEMBER_UUID)
                .exchange();

        resp.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("Test get all merchant members by merchant id functionality then success response")
    void givenMerchantId_whenGetAllByMerchantId_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/" + MERCHANT_UUID +  "/members/list")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.merchants_members_dto_list").isNotEmpty();
    }
}