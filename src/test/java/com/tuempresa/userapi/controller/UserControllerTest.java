package com.tuempresa.userapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.userapi.dto.request.PhoneRequest;
import com.tuempresa.userapi.dto.request.UserRegisterRequest;
import com.tuempresa.userapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UserRegisterRequest request = buildValidRequest("daniel@gallo.org", "Abcde12");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Daniel Gallo"))
                .andExpect(jsonPath("$.email").value("daniel@gallo.org"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.isactive").value(true))
                .andExpect(jsonPath("$.phones[0].number").value("1234567"));
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        UserRegisterRequest request = buildValidRequest("daniel@gallo.org", "Abcde12");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("El correo ya registrado"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailFormatIsInvalid() throws Exception {
        UserRegisterRequest request = buildValidRequest("daniel-gallo", "Abcde12");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("Formato de correo inválido"));
    }

    @Test
    void shouldReturnBadRequestWhenPasswordFormatIsInvalid() throws Exception {
        UserRegisterRequest request = buildValidRequest("daniel2@gallo.org", "abcdefg");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("La contraseña debe contener una mayúscula, letras minúsculas y al menos dos números"));
    }

    private UserRegisterRequest buildValidRequest(String email, String password) {
        PhoneRequest phone = new PhoneRequest();
        phone.setNumber("1234567");
        phone.setCitycode("1");
        phone.setContrycode("57");

        UserRegisterRequest request = new UserRegisterRequest();
        request.setName("Daniel Gallo");
        request.setEmail(email);
        request.setPassword(password);
        request.setPhones(List.of(phone));

        return request;
    }
}