package com.munsun.vacation_pay_service.services.providers;

import com.munsun.vacation_pay_service.dto.utils.CalendarDto;

import java.time.LocalDate;
import java.util.Optional;

public interface Calendar {
    Optional<CalendarDto> getHolidaysYear(int year);
    Integer getCountHolidaysBetweenTwoDates(LocalDate startDate, LocalDate endDate);
}
