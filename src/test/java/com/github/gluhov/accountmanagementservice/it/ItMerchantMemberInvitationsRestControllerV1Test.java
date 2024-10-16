package com.github.gluhov.accountmanagementservice.it;

import com.github.gluhov.accountmanagementservice.config.PostgreSqlTestContainerConfig;
import com.github.gluhov.accountmanagementservice.rest.MerchantMembersInvitationsRestControllerV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.gluhov.accountmanagementservice.service.MerchantMembersInvitationsData.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import({PostgreSqlTestContainerConfig.class})
@ActiveProfiles("test")
public class ItMerchantMemberInvitationsRestControllerV1Test extends AbstractRestControllerTest{
    private final String REST_URL = MerchantMembersInvitationsRestControllerV1.REST_URL;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Test get info functionality")
    void givenId_whenGetById_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/invitations/" + MERCHANT_MEMBER_INVITATION_UUID + "/details")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.first_name").isEqualTo(merchantMemberInvitationTestDataDto.getFirstName())
                .jsonPath("$.body.last_name").isEqualTo(merchantMemberInvitationTestDataDto.getLastName())
                .jsonPath("$.body.email").isEqualTo(merchantMemberInvitationTestDataDto.getEmail());
    }

    @Test
    @DisplayName("Test get info functionality then not found")
    void givenId_whenGetById_thenNotFoundResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/invitations/" + MERCHANT_MEMBER_INVITATION_NOT_FOUND_UUID + "/details")
                .exchange();

        resp.expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.errors[0].code").isEqualTo("AMS_MERCHANT_MEMBER_INVITATION_NOT_FOUND")
                .jsonPath("$.errors[0].message").isEqualTo("Merchant member invitation not found");
    }

    @Test
    @DisplayName("Test create functionality then success")
    void givenMerchantMemberInvitationData_whenCreate_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.post()
                .uri(REST_URL + "/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newMerchantMemberInvitationTestDataDto)
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.id").isNotEmpty()
                .jsonPath("$.body.first_name").isEqualTo(newMerchantMemberInvitationTestDataDto.getFirstName())
                .jsonPath("$.body.last_name").isEqualTo(newMerchantMemberInvitationTestDataDto.getLastName())
                .jsonPath("$.body.email").isEqualTo(newMerchantMemberInvitationTestDataDto.getEmail());
    }

    @Test
    @DisplayName("Test delete functionality success response")
    void givenId_whenDelete_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.delete()
                .uri(REST_URL + "/invitations/" + MERCHANT_MEMBER_INVITATION_UUID)
                .exchange();

        resp.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}