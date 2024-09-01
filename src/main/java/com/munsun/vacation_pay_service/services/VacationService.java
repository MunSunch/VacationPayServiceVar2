package com.munsun.vacation_pay_service.services;

import java.math.BigDecimal;

public interface VacationService {
    BigDecimal calculate(BigDecimal salary, Integer countDays);
}
