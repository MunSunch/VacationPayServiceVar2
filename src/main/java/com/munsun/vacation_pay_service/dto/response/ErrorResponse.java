package com.munsun.vacation_pay_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация об ошибке")
public class ErrorResponse {
    @Schema(description = "Сообщение об ошибке")
    private String message;

    @Schema(description = "Тип ошибки")
    private String exception;

    @Schema(description = "Код ошибки")
    private Integer code;
}
