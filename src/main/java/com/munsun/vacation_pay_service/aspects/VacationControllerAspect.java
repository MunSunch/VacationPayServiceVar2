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

    @Before("executionCalculateVacationPayMethod() && args(salary, days)")
    public void beforeCalculatePayMethodController(JoinPoint joinPoint, BigDecimal salary, Integer days){
        log.debug("execution={}, Request: GET /v1/calculate?salary={}&days={}", joinPoint.getSignature(), salary, days);
        log.info("Request: GET /v1/calculate?salary={}&days={}", salary, days);
    }

    @Before("executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)")
    public void beforeCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate){
        log.debug("execution={}, Request: GET /v2/calculate?salary={}&startDate={}&endDate={}", joinPoint.getSignature(), salary, startDate, endDate);
        log.info("Request: GET /v2/calculate?salary={}&startDate={}&endDate={}", salary, startDate, endDate);
    }

    @AfterReturning(value = "executionCalculateVacationPayMethod() && args(salary, days)", returning = "result")
    public void afterCalculatePayMethodController(JoinPoint joinPoint, BigDecimal salary, Integer days, BigDecimal result) {
        log.debug("execution={}, Response: GET /v1/calculate?salary={}&days={}, response={}", joinPoint.getSignature(), salary, days, result);
        log.info("Response: GET /v1/calculate?salary={}&days={}, response={}", salary, days, result);
    }

    @AfterReturning(value = "executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)", returning = "result")
    public void afterCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate, BigDecimal result) {
        log.debug("execution={}, Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, response={}", joinPoint.getSignature(), salary, startDate, endDate, result);
        log.info("Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, response={}", salary, startDate, endDate, result);
    }

    @AfterThrowing(value = "executionCalculateVacationPayMethod() && args(salary, days)", throwing = "e")
    public void afterThrowExceptionCalculatePayMethodController(JoinPoint joinPoint, BigDecimal salary, Integer days, Throwable e) {
        log.debug("execution={}, Response: GET /v1/calculate?salary={}&days={}, exception={}, message={}", joinPoint.getSignature(), salary, days, e.getClass().getSimpleName(), e.getMessage());
        log.warn("Response: GET /v1/calculate?salary={}&days={}, exception={}, message={}", salary, days, e.getClass().getSimpleName(), e.getMessage());
    }

    @AfterThrowing(value = "executionCalculateVacationPayBetweenMethod() && args(salary, startDate, endDate)", throwing = "e")
    public void afterThrowExceptionCalculatePayBetweenMethodController(JoinPoint joinPoint, BigDecimal salary, LocalDate startDate, LocalDate endDate, Throwable e) {
        log.debug("execution={}, Response: GET /v2/calculate?salary={}&startDate={}&endDate={}, exception={}, message={}", joinPoint.getSignature(), salary, startDate, endDate, e.getClass().getSimpleName(), e.getMessage());
        log.warn("Response: GET /v1/calculate?salary={}&startDate={}&endDate={}, exception={}, message={}", salary, startDate, endDate, e.getClass().getSimpleName(), e.getMessage());
    }
}