package com.munsun.vacation_pay_service.controllers;

import com.munsun.vacation_pay_service.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

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
            @Parameter(name = "countDays", description = "Кол-во дней", example = "28", required = true)
    })
    BigDecimal calculateVacationPay(@Min(1) @NotNull BigDecimal salary, @NotNull @Min(1) Integer countDays);
}