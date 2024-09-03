package com.munsun.vacation_pay_service.services;

import com.munsun.vacation_pay_service.services.providers.Calculator;
import com.munsun.vacation_pay_service.services.providers.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VacationServiceUnitTests {
    @MockBean
    private Calculator calculator;
    @MockBean
    private Calendar calendar;
    @Autowired
    private DefaultVacationService vacationService;

    @DisplayName("Test calculate pay excluding holidays")
    @Test
    public void given25DaysAnd22000Salary_whenCalculatePay_thenShouldCalculatePay() {
        BigDecimal salary = new BigDecimal("22000");
        int days = 25;
        BigDecimal expected = new BigDecimal("18454.00");
        when(calculator.calculate(salary, days))
                .thenReturn(expected);

        BigDecimal actual = vacationService.calculate(salary, days);

        verify(calculator, atLeastOnce()).calculate(salary, days);
        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }

    @DisplayName("Test calculate pay including holidays with invalid dates")
    @Test
    public void givenEndDateBeforeStartDate_whenCalculatePay_thenThrownIllegalArgumentException() {
        LocalDate startDate = LocalDate.of(2024, 9, 3);
        LocalDate endDate = startDate.minusDays(25);
        when(calendar.getCountHolidaysBetweenTwoDates(any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThatThrownBy( ()-> vacationService.calculate(BigDecimal.valueOf(22000), endDate, startDate))
                .isInstanceOf(IllegalArgumentException.class);
        verify(calculator, never()).calculate(any(), anyInt());
    }
}
