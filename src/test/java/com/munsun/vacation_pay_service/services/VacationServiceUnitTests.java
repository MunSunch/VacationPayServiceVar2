package com.munsun.vacation_pay_service.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VacationServiceUnitTests {
    @Autowired
    private VacationService vacationService;

    @DisplayName("Test calculate pay")
    @Test
    public void given25DaysAnd22000Salary_whenCalculatePay_thenShouldCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        int days = 25;
        BigDecimal expected = new BigDecimal("18454.00");

        BigDecimal actual = vacationService.calculate(salary, days);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }
}
