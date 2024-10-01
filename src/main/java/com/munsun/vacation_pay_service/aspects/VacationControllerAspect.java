package com.munsun.vacation_pay_service.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class VacationControllerAspect {
    private final MeterRegistry meterRegistry;
    private AtomicInteger maxResultVacationPay;
    private AtomicInteger maxResultVacationDays;

    @PostConstruct
    public void init() {
        this.maxResultVacationPay = meterRegistry.gauge("vacation_pay_v2_last_max_result", new AtomicInteger(0));
        this.maxResultVacationDays = meterRegistry.gauge("vacation_pay_v2_last_max_days", new AtomicInteger(0));
    }

    @Pointcut("execution(* com.munsun.vacation_pay_service.controllers.VacationApi+.calculateVacationPay(java.math.BigDecimal, Integer))")
    public void executionCalculateVacationPayMethod() {}

    @Pointcut("execution(* com.munsun.vacation_pay_service.controllers.VacationApi+.calculateVacationPayWithHolidays(java.math.BigDecimal, java.time.LocalDate, java.time.LocalDate))")
    public void executionCalculateVacationPayBetweenMethod() {}

    @Before("executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)")
    public void beforeCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate){
        log.debug("execution={}, Request: GET /v2/calculate?salary={}&startDate={}&endDate={}", joinPoint.getSignature(), salary, startDate, endDate);
        log.info("Request: GET /v2/calculate?salary={}&startDate={}&endDate={}", salary, startDate, endDate);
    }

    @AfterReturning(value = "executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)", returning = "result")
    public void afterCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate, BigDecimal result) {
        log.debug("execution={}, Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, response={}", joinPoint.getSignature(), salary, startDate, endDate, result);
        log.info("Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, response={}", salary, startDate, endDate, result);
        if(result.intValue() > maxResultVacationPay.intValue()) {
            maxResultVacationPay.set(result.intValue());
        }
        int countDays = (int)(endDate.toEpochDay() - startDate.toEpochDay());
        if(countDays > maxResultVacationDays.intValue()) {
            maxResultVacationDays.set(countDays);
        }
    }

    @AfterThrowing(value = "executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)", throwing = "e")
    public void afterThrowExceptionCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate, Throwable e) {
        log.debug("execution={}, Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, exception={}, message={}", joinPoint.getSignature(), salary, startDate, endDate, e.getClass().getSimpleName(), e.getMessage());
        log.warn("Response: GET /v1/calculate?salary={}&startDate={}&endDate={}, exception={}, message={}", salary, startDate, endDate, e.getClass().getSimpleName(), e.getMessage());
    }
}