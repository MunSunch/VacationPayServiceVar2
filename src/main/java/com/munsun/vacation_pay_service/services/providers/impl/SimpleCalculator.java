package com.munsun.vacation_pay_service.services.providers.impl;

import com.munsun.vacation_pay_service.exceptions.CalculationArgumentException;
import com.munsun.vacation_pay_service.services.providers.Calculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RefreshScope
public class SimpleCalculator implements Calculator {
    @Value("${calculation.average_count_days_month}")
    private BigDecimal averageCountDaysMonth;
    @Value("${calculation.count_digits_rounding}")
    private int countDigitsRound;
    @Value("${calculation.tax_percentage}")
    private BigDecimal taxPercent;

    @Override
    public BigDecimal calculate(BigDecimal salary, Integer countDays) {
        if(salary == null || countDays == null || countDays < 0 || salary.compareTo(BigDecimal.ZERO) == 0) {
            throw new CalculationArgumentException("Salary or count days must be greater than 0");
        }
        BigDecimal hundredPercent = new BigDecimal(1);
        return salary
                .multiply(hundredPercent.add(taxPercent))
                .divide(averageCountDaysMonth, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(countDays))
                .multiply(hundredPercent.subtract(taxPercent))
                .setScale(countDigitsRound, RoundingMode.HALF_EVEN);
    }
}
