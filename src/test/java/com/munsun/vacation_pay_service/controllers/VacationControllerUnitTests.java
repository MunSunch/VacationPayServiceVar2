package com.munsun.vacation_pay_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.vacation_pay_service.dto.response.ErrorResponse;
import com.munsun.vacation_pay_service.services.VacationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class VacationControllerUnitTests {
    @MockBean
    private VacationService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Given invalid parameters when send request GET /api/calculator/v1/calculate")
    @ParameterizedTest
    @CsvSource({
            "asas, asas",
            "10, -10",
            ",",
            "null, null",
            "asas, 10",
            "10, asas",
            "0,0"
    })
    public void givenInvalidParameters_whenSendRequestCalculate_thenReturnBadRequestAndErrorResponseDto(String salary, String days) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/calculator/v1/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .param("salary", salary)
                .param("countDays", days))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorResponse errorResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(errorResponse)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @DisplayName("Given valid parameters when send request GET /api/calculator/v1/calculate")
    @Test
    @Repeat(3)
    public void givenResultWorkService25DaysAnd22000Salary_whenSendRequestCalculate_thenReturnResultCode200() throws Exception {
        BigDecimal salary = new BigDecimal("22000");
        Integer days = 25;
        BigDecimal expected = new BigDecimal("18454.00");
        when(service.calculate(any(BigDecimal.class), any(Integer.class)))
                .thenReturn(expected);

        MvcResult result = mockMvc.perform(get("/api/calculator/v1/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .param("salary", String.valueOf(salary))
                .param("countDays", String.valueOf(days)))
                .andReturn();
        assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(String.valueOf(expected));
    }
}
