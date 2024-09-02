package com.munsun.vacation_pay_service.services.impl;

import com.munsun.vacation_pay_service.services.VacationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DefaultVacationService implements VacationService {
    private static final BigDecimal AVERAGE_COUNT_DAYS_MONTH = new BigDecimal("29.3");
    private static final int COUNT_DIGITS_ROUND = 2;
    private static final BigDecimal TAX_PERCENT = new BigDecimal("0.13");

    public BigDecimal calculate(BigDecimal salary, Integer countDays) {
        BigDecimal hundredPercent = new BigDecimal(1);
        return salary
                .multiply(hundredPercent.add(TAX_PERCENT))
                .divide(AVERAGE_COUNT_DAYS_MONTH, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(countDays))
                .multiply(hundredPercent.subtract(TAX_PERCENT))
                .setScale(COUNT_DIGITS_ROUND, RoundingMode.HALF_EVEN);
    }
}
