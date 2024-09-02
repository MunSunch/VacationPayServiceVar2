package com.munsun.vacation_pay_service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Aspect
@Component
public class VacationControllerAspect {
    @Pointcut("execution(* com.munsun.vacation_pay_service.controllers.VacationApi+.calculateVacationPay(java.math.BigDecimal, Integer))")
    public void executionCalculateVacationPayMethod() {}

    @Before("executionCalculateVacationPayMethod() && args(salary, days)")
    public void beforeCalculatePayMethodController(JoinPoint joinPoint, BigDecimal salary, Integer days){
        log.debug("execution={}, Request: GET /v1/calculate?salary={}&days={}", joinPoint.getSignature(), salary, days);
        log.info("Request: GET /v1/calculate?salary={}&days={}", salary, days);
    }

    @AfterReturning(value = "executionCalculateVacationPayMethod() && args(salary, days)", returning = "result")
    public void afterCalculatePayMethodController(JoinPoint joinPoint, BigDecimal salary, Integer days, BigDecimal result) {
        log.debug("execution={}, Response: GET /v1/calculate?salary={}&days={}, response={}", joinPoint.getSignature(), salary, days, result);
        log.info("Response: GET /v1/calculate?salary={}&days={}, response={}", salary, days, result);
    }

    @AfterThrowing(value = "executionCalculateVacationPayMethod() && args(salary, days)", throwing = "e")
    public void afterThrowExceptionCalculatePayMethodController(JoinPoint joinPoint, BigDecimal salary, Integer days, Throwable e) {
        log.debug("execution={}, Response: GET /v1/calculate?salary={}&days={}, exception={}, message={}", joinPoint.getSignature(), salary, days, e.getClass().getSimpleName(), e.getMessage());
        log.warn("Response: GET /v1/calculate?salary={}&days={}, exception={}, message={}", salary, days, e.getClass().getSimpleName(), e.getMessage());
    }
}
