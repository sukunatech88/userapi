package com.tuempresa.userapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneRequest {

    @Schema(example = "1234567")
    @NotBlank(message = "El número es obligatorio")
    private String number;

    @Schema(example = "1")
    @NotBlank(message = "El citycode es obligatorio")
    private String citycode;

    @Schema(example = "57")
    @NotBlank(message = "El contrycode es obligatorio")
    private String contrycode;
}