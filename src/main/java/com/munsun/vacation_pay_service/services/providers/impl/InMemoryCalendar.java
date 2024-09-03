package com.munsun.vacation_pay_service.services.providers.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.vacation_pay_service.dto.utils.CalendarDto;
import com.munsun.vacation_pay_service.dto.utils.HolidayDto;
import com.munsun.vacation_pay_service.exceptions.CalendarArgumentException;
import com.munsun.vacation_pay_service.exceptions.CalendarNotFoundException;
import com.munsun.vacation_pay_service.services.providers.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InMemoryCalendar implements Calendar {
    private final ObjectMapper objectMapper;
    private List<CalendarDto> calendarWithHolidays;

    @PostConstruct
    public void initCalendarDto() throws IOException {
        byte[] bytes = null;
        try (InputStream stream = InMemoryCalendar.class.getClassLoader().getResourceAsStream("calendar-holidays.json")) {
            bytes = stream.readAllBytes();
        } catch (IOException e) {
            throw new IOException("Can't read calendar-holidays.json");
        }
        this.calendarWithHolidays = objectMapper.readValue(bytes, new TypeReference<List<CalendarDto>>(){});
    }

    @Override
    public Integer getCountHolidaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        if(endDate.isBefore(startDate)) {
            throw new CalendarArgumentException("End date can't be after start date; start date: " + startDate + "; end date: " + endDate);
        }
        CalendarDto calendarDto = getHolidaysYear(startDate.getYear())
                .orElseThrow(() -> new CalendarNotFoundException("Can't get holidays for year: " + startDate.getYear()));
        return (int) calendarDto.getHolidays().stream()
                    .map(HolidayDto::getDate)
                    .filter(date -> (date.isAfter(startDate) || date.isEqual(startDate))
                                    && (date.isBefore(endDate) || date.isEqual(endDate)))
                    .count();
    }

    @Override
    public Optional<CalendarDto> getHolidaysYear(int year) {
        return calendarWithHolidays.stream()
                .filter(calendarDto -> calendarDto.getYear() == year)
                .findFirst();
    }
}
