package ru.spshop.controllers.inrerfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import ru.spshop.dto.AuthResponse;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.RefreshJwtRequest;
import ru.spshop.dto.UserDTO;
import ru.spshop.dto.UserInfoDto;

@Tag(name = "Authentication Controller")
public interface AuthApi {

    @Operation(summary = "User Registration")
    ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid UserDTO request, HttpServletResponse response);

    @Operation(summary = "User Login")
    ResponseEntity<JwtAuthResponse> login(@RequestBody @Valid UserDTO authRequest, HttpServletResponse response);

    @Operation(summary = "Get user info")
    ResponseEntity<UserInfoDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "User Logout")
    ResponseEntity<Void> logout(HttpServletResponse response);

    @Operation(summary = "Update access token")
    ResponseEntity<JwtAuthResponse> getNewAccessToken(@CookieValue(name = "refreshToken", required = false) String refreshToken);

    @Operation(summary = "Update refresh token")
    ResponseEntity<JwtAuthResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException;
}
