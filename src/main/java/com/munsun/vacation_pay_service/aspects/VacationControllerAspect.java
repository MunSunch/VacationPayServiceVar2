package com.munsun.vacation_pay_service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Aspect
@Component
public class VacationControllerAspect {
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
    }

    @AfterThrowing(value = "executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)", throwing = "e")
    public void afterThrowExceptionCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate, Throwable e) {
        log.debug("execution={}, Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, exception={}, message={}", joinPoint.getSignature(), salary, startDate, endDate, e.getClass().getSimpleName(), e.getMessage());
        log.warn("Response: GET /v1/calculate?salary={}&startDate={}&endDate={}, exception={}, message={}", salary, startDate, endDate, e.getClass().getSimpleName(), e.getMessage());
    }
}
