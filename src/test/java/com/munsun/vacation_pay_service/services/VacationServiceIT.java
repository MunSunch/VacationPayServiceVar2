package com.munsun.vacation_pay_service.services;

import com.munsun.vacation_pay_service.exceptions.CalendarNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class VacationServiceIT {
    @Autowired
    private DefaultVacationService service;

    @DisplayName("Test calculate")
    @Test
    public void given25DaysAnd22000Salary_whenCalculatePay_thenShouldCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        int days = 25;
        BigDecimal expected = new BigDecimal("18454.00");

        BigDecimal actual = service.calculate(salary, days);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }

    @DisplayName("Test calculate vacation pay two dates")
    @Test
    public void givenTwoDated_whenCalculatePay_thenShouldCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 3);
        BigDecimal expected = new BigDecimal("18454.00");

        BigDecimal actual = service.calculate(salary, startDate, endDate);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }

    @DisplayName("Test get count holidays between invalid two dates")
    @Test
    public void givenInvalidTwoDates_whenGetCountHolidaysBetweenTwoDates_thenThrownIllegalArgumentException() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);

        assertThatThrownBy(() -> service.calculate(BigDecimal.valueOf(22000), endDate, startDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Test get count holidays between two dates for a non-existent year")
    @Test
    public void givenNonExistentYearTwoDates_whenGetCountHolidaysBetweenTwoDates_thenThrownIllegalArgumentException() {
        LocalDate startDate = LocalDate.of(4042, 1, 1);
        LocalDate endDate = LocalDate.of(4042, 1, 31);

        assertThatThrownBy(() -> service.calculate(BigDecimal.valueOf(22000), startDate, endDate))
                .isInstanceOf(CalendarNotFoundException.class);
    }

    @DisplayName("Test calculate vacation pay with invalid salary")
    @ParameterizedTest
    @CsvSource({"0,-1"})
    public void givenInvalidSalaryAndCountDays_whenCalculatePay_thenThrownIllegalArgumentException(BigDecimal salary) {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);

        assertThatThrownBy(() -> service.calculate(salary, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}