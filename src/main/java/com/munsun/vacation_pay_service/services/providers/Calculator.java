package com.munsun.vacation_pay_service.services.providers;

import java.math.BigDecimal;

public interface Calculator {
    BigDecimal calculate(BigDecimal salary, Integer countDays);
}
