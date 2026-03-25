package com.tuempresa.userapi.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterRequest {

    @Schema(example = "Daniel Gallo")
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Schema(example = "daniel@gallo.org")
    @NotBlank(message = "El correo es obligatorio")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Formato de correo inválido"
    )
    private String email;

    @Schema(example = "Abcde12")
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=(?:.*\\d){2,}).+$",
            message = "La contraseña debe contener una mayúscula, letras minúsculas y al menos dos números"
    )
    private String password;

    @Valid
    @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
    @ArraySchema(schema = @Schema(implementation = PhoneRequest.class))
    private List<PhoneRequest> phones;

}