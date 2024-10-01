package com.munsun.vacation_pay_service.controllers.impl;

import com.munsun.vacation_pay_service.controllers.VacationApi;
import com.munsun.vacation_pay_service.services.DefaultVacationService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculator")
public class VacationController implements VacationApi {
    private final DefaultVacationService service;

    @GetMapping("/v1/calculate")
    public BigDecimal calculateVacationPay(@RequestParam BigDecimal salary,
                                           @RequestParam Integer countDaysVacation)
    {
        return service.calculate(salary, countDaysVacation);
    }

    @GetMapping("/v2/calculate")
    public BigDecimal calculateVacationPayWithHolidays(@RequestParam BigDecimal salary,
                                                       @RequestParam LocalDate startDate,
                                                       @RequestParam LocalDate endDate)
    {
        return service.calculate(salary, startDate, endDate);
    }
}