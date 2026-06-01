package com.session15ex05.controller;

import com.session15ex05.dto.AuthRequest;
import com.session15ex05.dto.AuthResponse;
import com.session15ex05.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        boolean success = authService.register(request);

        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new AuthResponse("Username đã tồn tại"));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse("Đăng ký tài khoản thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        boolean success = authService.login(request);

        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Sai username hoặc password"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse("Đăng nhập thành công"));
    }
}
