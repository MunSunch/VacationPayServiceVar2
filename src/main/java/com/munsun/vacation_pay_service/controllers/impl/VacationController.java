package com.munsun.vacation_pay_service.controllers.impl;

import com.munsun.vacation_pay_service.controllers.VacationApi;
import com.munsun.vacation_pay_service.services.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculator")
public class VacationController implements VacationApi {
    private final VacationService service;

    @GetMapping("/v1/calculate")
    public BigDecimal calculateVacationPay(@RequestParam BigDecimal salary,
                                           @RequestParam Integer countDays)
    {
        return service.calculate(salary, countDays);
    }
}