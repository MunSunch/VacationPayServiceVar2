package com.munsun.vacation_pay_service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Aspect
@Component
public class VacationServiceAspect {
    @Pointcut("execution(* com.munsun.vacation_pay_service.services.DefaultVacationService.calculate(java.math.BigDecimal, Integer))")
    public void executionCalculatePayMethod() {}

    @Before("executionCalculatePayMethod() && args(salary, days)")
    public void beforeCalculatePayMethodService(JoinPoint joinPoint, BigDecimal salary, Integer days){
        log.debug("execution={}, args{salary={}, days={}), calculation...", joinPoint.getSignature(), salary, days);
        log.info("args{salary={}, days={}, calculation...", salary, days);
    }

    @AfterReturning(value = "executionCalculatePayMethod() && args(salary, days)", returning = "result")
    public void afterCalculatePayMethodService(JoinPoint joinPoint, BigDecimal salary, Integer days, BigDecimal result) {
        log.debug("execution={}, args{salary={}, days={}), calculate is completed, result={}", joinPoint.getSignature(), salary, days, result);
        log.info("args{salary={}, days={}), calculate is completed, result={}", salary, days, result);
    }

    @AfterThrowing(value = "executionCalculatePayMethod() && args(salary, days)", throwing = "e")
    public void afterThrowExceptionCalculatePayMethodService(JoinPoint joinPoint, BigDecimal salary, Integer days, Throwable e) {
        log.debug("execution={}, args{salary={}, days={}), calculate is not completed, exception={}, message={}", joinPoint.getSignature(), salary, days, e.getClass().getSimpleName(), e.getMessage());
        log.warn("args{salary={}, days={}), calculate is not completed, exception={}, message={}", salary, days, e.getClass().getSimpleName(), e.getMessage());
    }
}