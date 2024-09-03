package com.munsun.vacation_pay_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class VacationPayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(VacationPayServiceApplication.class, args);
	}
}
