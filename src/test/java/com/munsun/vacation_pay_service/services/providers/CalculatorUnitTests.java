package com.munsun.vacation_pay_service.services.providers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CalculatorUnitTests {
    @Autowired
    private Calculator calculator;

    @DisplayName("Test calculate vacation pay with invalid arguments")
    @ParameterizedTest
    @CsvSource({
            "0, 25",
            "22000, 0",
            "22000, -25",
            "0, -25",
            "0, 0"
    })
    public void givenInvalidSalaryAndCountDays_whenCalculatePay_thenThrownIllegalArgumentException(BigDecimal salary, int days) {
        assertThatThrownBy(() -> calculator.calculate(salary, days))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Test calculate vacation pay")
    @Test
    public void given25DaysAnd22000Salary_whenCalculatePay_thenShouldCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        int days = 25;
        BigDecimal expected = new BigDecimal("18454.00");

        BigDecimal actual = calculator.calculate(salary, days);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test calculate vacation pay where startDate-endDate=0")
    @Test
    public void givenStartAndEndDates_whenCalculatePay_thenReturnZero() {
        BigDecimal expected = new BigDecimal("0.00");
        BigDecimal salary = new BigDecimal("22000");

        BigDecimal actual = calculator.calculate(salary, 0);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
