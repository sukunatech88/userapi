package com.tuempresa.userapi.service;

import com.tuempresa.userapi.dto.request.UserRegisterRequest;
import com.tuempresa.userapi.dto.response.UserResponse;

public interface UserService {

    UserResponse register(UserRegisterRequest request);
}