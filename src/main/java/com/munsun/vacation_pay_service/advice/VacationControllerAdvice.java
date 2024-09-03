package com.munsun.vacation_pay_service.advice;

import com.munsun.vacation_pay_service.dto.response.ErrorResponse;
import com.munsun.vacation_pay_service.exceptions.CalculationArgumentException;
import com.munsun.vacation_pay_service.exceptions.CalendarArgumentException;
import com.munsun.vacation_pay_service.exceptions.CalendarNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class VacationControllerAdvice {
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception e) {
        log.error("Validation error DTO, message={}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), e.getClass().getName(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({CalculationArgumentException.class, CalendarArgumentException.class})
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Invalid arguments, message={}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), e.getClass().getName(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = ex.getName();
        String errorMessage = "Invalid data type for the parameter: '" + fieldName;
        errors.put(fieldName, errorMessage);
        String message = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("; "))
                .toString();
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(message, ex.getClass().getName(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        log.error("Server's error; message=", e.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse(e.getMessage(), e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(CalendarNotFoundException.class)
    public ResponseEntity<?> handleCalendarNotFoundException(RuntimeException e) {
        log.error("Calendar not found; message={}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), e.getClass().getName(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        String message = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("; "))
                .toString();
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(message, ex.getClass().getName(), HttpStatus.BAD_REQUEST.value()));
    }
}
