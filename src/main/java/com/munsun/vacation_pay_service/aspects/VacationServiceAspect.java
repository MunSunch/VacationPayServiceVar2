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
    @Pointcut("execution(* com.munsun.vacation_pay_service.services.VacationService.calculate(..))")
    public void executionCalculatePayMethod() {}

//    @Before("executionCalculatePayMethod() && args(info)")
//    public void beforeCalculatePayMethod(JoinPoint joinPoint, VacationShortInfoDto info) {
//        log.debug("execution={}, info={}, calculation in progress", joinPoint.getSignature(), info);
//        log.info("info={}, calculation in progress", info);
//    }
//
//    @AfterReturning(value = "executionCalculatePayMethod() && args(info)", returning = "result")
//    public void afterCalculatePayMethod(JoinPoint joinPoint, VacationShortInfoDto info, BigDecimal result) {
//        log.debug("execution={}, info={}, calculate is completed, result={}", joinPoint.getSignature(), info, result);
//        log.info("info={}, calculate is completed, result={}", info, result);
//    }
//
//    @AfterThrowing(value = "executionCalculatePayMethod() && args(info)", throwing = "e")
//    public void afterThrowExceptionCalculatePayMethod(JoinPoint joinPoint, VacationShortInfoDto info, Throwable e) {
//        log.debug("execution={}, info={}, calculate is not completed, exception={}, message={}", joinPoint.getSignature(), info, e.getClass().getSimpleName(), e.getMessage());
//        log.warn("info={}, calculate is not completed, exception={}, message={}", info, e.getClass().getSimpleName(), e.getMessage());
//    }
}