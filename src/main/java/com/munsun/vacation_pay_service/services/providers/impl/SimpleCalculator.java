package com.munsun.vacation_pay_service.services.providers.impl;

import com.munsun.vacation_pay_service.exceptions.CalculationArgumentException;
import com.munsun.vacation_pay_service.services.providers.Calculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class SimpleCalculator implements Calculator {
    private static final BigDecimal AVERAGE_COUNT_DAYS_MONTH = new BigDecimal("29.3");
    private static final int COUNT_DIGITS_ROUND = 2;
    private static final BigDecimal TAX_PERCENT = new BigDecimal("0.13");

    @Override
    public BigDecimal calculate(BigDecimal salary, Integer countDays) {
        if(salary == null || countDays == null || countDays <= 0 || salary.compareTo(BigDecimal.ZERO) == 0) {
            throw new CalculationArgumentException("Salary or count days must be greater than 0");
        }
        BigDecimal hundredPercent = new BigDecimal(1);
        return salary
                .multiply(hundredPercent.add(TAX_PERCENT))
                .divide(AVERAGE_COUNT_DAYS_MONTH, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(countDays))
                .multiply(hundredPercent.subtract(TAX_PERCENT))
                .setScale(COUNT_DIGITS_ROUND, RoundingMode.HALF_EVEN);
    }
}
