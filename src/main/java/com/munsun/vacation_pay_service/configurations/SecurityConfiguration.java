package com.munsun.vacation_pay_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(requests ->
                        requests.mvcMatchers("/actuator/**").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .httpBasic()
                .and().build();
    }
}
