package ru.spshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spshop.controllers.inrerfaces.TokenApi;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.service.AuthService;
import ru.spshop.service.UserService;

@RestController
@RequestMapping("/v1/tokens")
@RequiredArgsConstructor
public class TokenController implements TokenApi {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/token")
    public ResponseEntity<JwtAuthResponse> getNewAccessToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(authService.getAccessToken(refreshToken));
    }

}
