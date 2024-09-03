package com.munsun.vacation_pay_service.services;

import com.munsun.vacation_pay_service.services.providers.Calculator;
import com.munsun.vacation_pay_service.services.providers.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class DefaultVacationService {
    private final Calculator calculator;
    private final Calendar calendar;

    public BigDecimal calculate(BigDecimal salary, Integer countDays) {
        return calculator.calculate(salary, countDays);
    }

    public BigDecimal calculate(BigDecimal salary, LocalDate startDate, LocalDate endDate) {
        int countHolidaysBetween = calendar.getCountHolidaysBetweenTwoDates(startDate, endDate);
        int countDaysVacation = (int) ChronoUnit.DAYS.between(startDate, endDate);
        countDaysVacation -= countHolidaysBetween;
        return calculate(salary, countDaysVacation);
    }
}
