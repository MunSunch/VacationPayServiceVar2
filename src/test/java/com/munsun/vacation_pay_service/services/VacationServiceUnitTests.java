package com.munsun.vacation_pay_service.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VacationServiceUnitTests {
    @Autowired
    private VacationService vacationService;

    @DisplayName("Test calculate pay")
    @Test
    public void given() {

    }
}
