package com.tuempresa.userapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneRequest {

    @NotBlank(message = "El número es obligatorio")
    private String number;

    @NotBlank(message = "El citycode es obligatorio")
    private String citycode;

    @NotBlank(message = "El contrycode es obligatorio")
    private String contrycode;
}