package ru.spshop.controllers;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spshop.dto.AuthResponse;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.RefreshJwtRequest;
import ru.spshop.dto.UserDTO;
import ru.spshop.service.AuthService;
import ru.spshop.service.RegisterService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterService registerService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid UserDTO request) {
        return ResponseEntity.ok(registerService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody @Valid UserDTO authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(authRequest, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token")
    public ResponseEntity<JwtAuthResponse> getNewAccessToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        JwtAuthResponse response = authService.getAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthResponse token = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(token);
    }

}
