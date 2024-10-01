package com.munsun.vacation_pay_service.controllers;

import com.munsun.vacation_pay_service.dto.response.ErrorResponse;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Validated
@Tag(name = "контроллер для расчета отпускных")
public interface VacationApi {
    @Operation(summary = "расчёт без учёта выходных и праздников")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Вычисление выполнено успешно",
                    content = @Content(
                            schema = @Schema(implementation = BigDecimal.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "400",
                    description = "Неверные данные запроса",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @Parameters({
            @Parameter(name = "salary", description = "Средняя зарплата за 12 месяцев", example = "12500", required = true),
            @Parameter(name = "countDaysVacation", description = "Кол-во дней отпуска", example = "28", required = true)
    })
    BigDecimal calculateVacationPay(@Min(1) @NotNull BigDecimal salary, @NotNull @Min(1) Integer countDaysVacation);



    @Operation(summary = "расчёт с учётом выходных и праздников")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Вычисление выполнено успешно",
                    content = @Content(
                            schema = @Schema(implementation = BigDecimal.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "400",
                    description = "Неверные данные запроса",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @Parameters({
            @Parameter(name = "salary", description = "Средняя зарплата за 12 месяцев", example = "12500", required = true),
            @Parameter(name = "startDate", description = "Дата начала отпуска", example = "2024-01-01", required = true),
            @Parameter(name = "endDate", description = "Дата окончания отпуска", example = "2024-01-30", required = true)
    })
    BigDecimal calculateVacationPayWithHolidays(@Min(1) @NotNull BigDecimal salary,
                                    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);

}