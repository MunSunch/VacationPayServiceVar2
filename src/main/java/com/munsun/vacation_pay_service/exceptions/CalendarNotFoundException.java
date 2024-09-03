package com.munsun.vacation_pay_service.exceptions;

public class CalendarNotFoundException extends RuntimeException{
    public CalendarNotFoundException(String s) {
        super(s);
    }
}
