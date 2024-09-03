package com.munsun.vacation_pay_service.services.providers;

import com.munsun.vacation_pay_service.exceptions.CalendarNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CalendarUnitTests {
    @Autowired
    private Calendar calendar;

    @DisplayName("Test init calendar from file with resource")
    @Test
    public void initCalendar() {
        assertThat(calendar.getHolidaysYear(2024))
                .isPresent();
    }

    @DisplayName("Test get holidays for a non-existent year")
    @Test
    public void givenNonExistentYear_whenGetVacation_thenReturnEmpty() {
        assertThat(calendar.getHolidaysYear(4042))
                .isEmpty();
    }

    @DisplayName("Test get count holidays between two dates for a non-existent year")
    @Test
    public void givenNonExistentYearTwoDates_whenGetCountHolidaysBetweenTwoDates_thenThrownIllegalArgumentException() {
        LocalDate startDate = LocalDate.of(4042, 1, 1);
        LocalDate endDate = LocalDate.of(4042, 1, 31);

        assertThatThrownBy(() -> calendar.getCountHolidaysBetweenTwoDates(startDate, endDate))
                .isInstanceOf(CalendarNotFoundException.class);
    }

    @DisplayName("Test get count holidays between invalid two dates in january")
    @Test
    public void givenInvalidTwoDates_whenGetCountHolidaysBetweenTwoDates_thenThrownIllegalArgumentException() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);

        assertThatThrownBy(() -> calendar.getCountHolidaysBetweenTwoDates(endDate, startDate))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("Test get count holidays between two dates in january-february")
    @Test
    public void givenTwoDatesInJanuaryAndFebruary_whenGetCountHolidaysBetweenTwoDates_thenReturnCorrectResult9() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 28);
        int expectedCountHolidays = 9;

        int actualCountHolidays = calendar.getCountHolidaysBetweenTwoDates(startDate, endDate);

        assertThat(actualCountHolidays)
                .isEqualTo(expectedCountHolidays);
    }
}
