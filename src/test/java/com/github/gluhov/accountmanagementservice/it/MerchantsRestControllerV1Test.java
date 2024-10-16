package com.github.gluhov.accountmanagementservice.it;

import com.github.gluhov.accountmanagementservice.config.PostgreSqlTestContainerConfig;
import com.github.gluhov.accountmanagementservice.rest.MerchantsRestControllerV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.gluhov.accountmanagementservice.service.MerchantsData.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import({PostgreSqlTestContainerConfig.class})
@ActiveProfiles("test")
public class MerchantsRestControllerV1Test extends AbstractRestControllerTest {
    private final String REST_URL = MerchantsRestControllerV1.REST_URL;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Test get info functionality")
    void givenId_whenGetById_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/" + MERCHANT_UUID + "/details")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.company_name").isEqualTo(merchantTestData.getCompanyName())
                .jsonPath("$.body.phone_number").isEqualTo(merchantTestData.getPhoneNumber())
                .jsonPath("$.body.email").isEqualTo(merchantTestData.getEmail());
    }

    @Test
    @DisplayName("Test get info functionality then not found")
    void givenId_whenGetById_thenNotFoundResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/" + MERCHANT_UUID_NOT_FOUND + "/details")
                .exchange();

        resp.expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.errors[0].code").isEqualTo("AMS_MERCHANT_NOT_FOUND")
                .jsonPath("$.errors[0].message").isEqualTo("Merchant not found");
    }

    @Test
    @DisplayName("Test create functionality then success response")
    void givenIndividualData_whenCreate_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.post()
                .uri(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newMerchantTestDataDto)
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.company_name").isEqualTo(newMerchantTestDataDto.getCompanyName())
                .jsonPath("$.body.phone_number").isEqualTo(newMerchantTestDataDto.getPhoneNumber())
                .jsonPath("$.body.email").isEqualTo(newMerchantTestDataDto.getEmail());
    }

    @Test
    @DisplayName("Test update functionality success response")
    void givenIndividualData_whenUpdate_thenSuccessResponse() {
        merchantsTestDataDto.setEmail("new.email@example.com");
        WebTestClient.ResponseSpec resp = webTestClient.put()
                .uri(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(merchantsTestDataDto)
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.company_name").isEqualTo(merchantsTestDataDto.getCompanyName())
                .jsonPath("$.body.phone_number").isEqualTo(merchantsTestDataDto.getPhoneNumber())
                .jsonPath("$.body.email").isEqualTo(merchantsTestDataDto.getEmail());
    }

    @Test
    @DisplayName("Test delete functionality success response")
    void givenId_whenDelete_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.delete()
                .uri(REST_URL + "/" + MERCHANT_UUID)
                .exchange();

        resp.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    @DisplayName("Test get all merchants functionality then success response")
    void givenEmptyRequest_whenGetAll_thenSuccessResponse() {
        WebTestClient.ResponseSpec resp = webTestClient.get()
                .uri(REST_URL + "/list")
                .exchange();

        resp.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.body.merchants_dto_list").isNotEmpty();
    }
}