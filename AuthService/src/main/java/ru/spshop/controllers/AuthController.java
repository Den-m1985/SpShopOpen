package ru.spshop.controllers;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spshop.dto.JwtRequest;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.RefreshJwtRequest;
import ru.spshop.services.AuthService;


//@CrossOrigin(origins = "http://85.159.231.215:3000")
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtAuthResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtAuthResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtAuthResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtAuthResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
