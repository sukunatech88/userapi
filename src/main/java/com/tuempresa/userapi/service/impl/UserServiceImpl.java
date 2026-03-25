package com.tuempresa.userapi.service.impl;

import com.tuempresa.userapi.dto.request.PhoneRequest;
import com.tuempresa.userapi.dto.request.UserRegisterRequest;
import com.tuempresa.userapi.dto.response.PhoneResponse;
import com.tuempresa.userapi.dto.response.UserResponse;
import com.tuempresa.userapi.entity.Phone;
import com.tuempresa.userapi.entity.User;
import com.tuempresa.userapi.exception.EmailAlreadyExistsException;
import com.tuempresa.userapi.repository.UserRepository;
import com.tuempresa.userapi.service.UserService;
import com.tuempresa.userapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRegisterRequest request) {
        validateEmailUniqueness(request.getEmail());

        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        String token = jwtUtil.generateToken(userId, request.getEmail());

        User user = buildUser(request, userId, now, token);
        List<Phone> phones = mapPhones(request.getPhones(), user);
        user.setPhones(phones);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("El correo ya registrado");
        }
    }

    private User buildUser(UserRegisterRequest request, UUID userId, LocalDateTime now, String token) {
        return User.builder()
                .id(userId)
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .created(now)
                .modified(now)
                .lastLogin(now)
                .token(token)
                .isActive(true)
                .build();
    }

    private List<Phone> mapPhones(List<PhoneRequest> phoneRequests, User user) {
        return phoneRequests.stream()
                .map(phoneRequest -> Phone.builder()
                        .number(phoneRequest.getNumber())
                        .citycode(phoneRequest.getCitycode())
                        .contrycode(phoneRequest.getContrycode())
                        .user(user)
                        .build())
                .toList();
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created(user.getCreated())
                .modified(user.getModified())
                .last_login(user.getLastLogin())
                .token(user.getToken())
                .isactive(user.getIsActive())
                .phones(user.getPhones().stream()
                        .map(phone -> PhoneResponse.builder()
                                .number(phone.getNumber())
                                .citycode(phone.getCitycode())
                                .contrycode(phone.getContrycode())
                                .build())
                        .toList())
                .build();
    }
}