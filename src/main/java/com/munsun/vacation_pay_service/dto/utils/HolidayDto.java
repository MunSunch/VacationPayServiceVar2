package com.munsun.vacation_pay_service.dto.utils;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDto {
    private LocalDate date;
    private String name;
}
