package com.tuempresa.userapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhoneResponse {

    private String number;
    private String citycode;
    private String contrycode;
}