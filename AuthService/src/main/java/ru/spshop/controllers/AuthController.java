package ru.spshop.controllers;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spshop.controllers.inrerfaces.AuthApi;
import ru.spshop.dto.AuthResponse;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.RefreshJwtRequest;
import ru.spshop.dto.UserDTO;
import ru.spshop.dto.UserInfoDto;
import ru.spshop.service.AuthService;
import ru.spshop.service.RegisterService;
import ru.spshop.service.UserService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final RegisterService registerService;
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid UserDTO request, HttpServletResponse response) {
        return ResponseEntity.ok(registerService.registerUser(request, response));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody @Valid UserDTO authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(authRequest, response));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Вытаскиваешь текущего пользователя из Spring Security
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getUserInfo(userDetails.getUsername()));
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
