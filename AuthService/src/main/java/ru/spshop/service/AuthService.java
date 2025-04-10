package ru.spshop.service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.UserDTO;
import ru.spshop.model.AuthUser;
import ru.spshop.model.User;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public JwtAuthResponse login(UserDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()));
        User user = userService.getUserByEmail(request.email());
        UserDetails userDetail = new AuthUser(user);

        final String accessToken = jwtProvider.generateAccessToken(userDetail);
        final String refreshToken = jwtProvider.generateRefreshToken(userDetail);
        refreshStorage.put(user.getEmail(), refreshToken);
        return new JwtAuthResponse(accessToken, refreshToken);
    }


    public JwtAuthResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userService.getUserByEmail(login);
                UserDetails userDetail = new AuthUser(user);
                final String accessToken = jwtProvider.generateAccessToken(userDetail);
                return new JwtAuthResponse(accessToken, null);
            }
        }
        return new JwtAuthResponse(null, null);
    }


    public JwtAuthResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                User user = userService.getUserByEmail(login);
                UserDetails userDetail = new AuthUser(user);

                final String accessToken = jwtProvider.generateAccessToken(userDetail);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDetail);
                refreshStorage.put(userDetail.getUsername(), newRefreshToken);
                return new JwtAuthResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

}
