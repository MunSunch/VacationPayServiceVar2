package com.munsun.vacation_pay_service.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class VacationControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Test calculate vacation pay")
    @Test
    public void given25DaysAnd22000Salary_whenSendRequestCalculatePay_thenReturnResponseCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        int days = 25;
        BigDecimal expected = new BigDecimal("18454.00");

        webTestClient.get().uri(uri -> uri.path("/api/calculator/v1/calculate")
                        .queryParam("salary", salary)
                        .queryParam("countDaysVacation", days)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectHeader().contentType(MediaType.APPLICATION_JSON),
                        spec -> spec.expectBody(BigDecimal.class).isEqualTo(expected)
                );
    }

    @DisplayName("Test calculate vacation pay two dates")
    @Test
    public void givenSalaryAndTwoDates_whenSendRequestCalculatePay_thenReturnResponseCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 3);
        BigDecimal expected = new BigDecimal("18454.00");

        webTestClient.get().uri(uri -> uri.path("/api/calculator/v2/calculate")
                        .queryParam("salary", salary)
                        .queryParam("startDate", startDate)
                        .queryParam("endDate", endDate)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectHeader().contentType(MediaType.APPLICATION_JSON),
                        spec -> spec.expectBody(BigDecimal.class).isEqualTo(expected)
                );
    }

    @DisplayName("Test calculate vacation pay invalid dates")
    @Test
    public void givenInvalidTwoDates_whenSendRequestCalculatePay_thenThrownCalendarArgumentException() {
        BigDecimal salary = new BigDecimal("22000");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 3);

        webTestClient.get().uri(uri -> uri.path("/api/calculator/v2/calculate")
                        .queryParam("salary", salary)
                        .queryParam("startDate", endDate)
                        .queryParam("endDate", startDate)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isBadRequest(),
                        spec -> spec.expectHeader().contentType(MediaType.APPLICATION_JSON),
                        spec -> spec.expectBody()
                                .jsonPath("$.code").isEqualTo(400)
                );
    }

    @DisplayName("Test calculate vacation pay dates for a non-existent year")
    @Test
    public void givenTwoDatesNonExistsYear_whenSendRequestCalculatePay_thenThrownCalendarNotFoundException() {
        BigDecimal salary = new BigDecimal("22000");
        LocalDate startDate = LocalDate.of(4042, 1, 1);
        LocalDate endDate = LocalDate.of(4042, 2, 3);

        webTestClient.get().uri(uri -> uri.path("/api/calculator/v2/calculate")
                        .queryParam("salary", salary)
                        .queryParam("startDate", startDate)
                        .queryParam("endDate", endDate)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectAll(
                        spec -> spec.expectStatus().isNotFound(),
                        spec -> spec.expectHeader().contentType(MediaType.APPLICATION_JSON),
                        spec -> spec.expectBody()
                                .jsonPath("$.code").isEqualTo(404)
                );
    }
}
