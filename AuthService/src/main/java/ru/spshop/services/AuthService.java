package ru.spshop.services;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.spshop.dto.JwtRequest;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.model.JwtAuthentication;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserService userService;
    private Map<String, String> refreshStorage = new HashMap<>();
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    //private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public JwtAuthResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthException("Incorrect username or password");
        }
        UserDetails userDetail = userService.loadUserByUsername(authRequest.getUsername());

        final String accessToken = jwtProvider.generateAccessToken(userDetail);
        //log.info("accessToken------" + accessToken);
        final String refreshToken = jwtProvider.generateRefreshToken(userDetail);
        //log.info("refreshToken------" + refreshToken);
        refreshStorage.put(userDetail.getUsername(), refreshToken);
        return new JwtAuthResponse(accessToken, refreshToken);
    }


    public JwtAuthResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                UserDetails userDetail = userService.loadUserByUsername(login);

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

                UserDetails userDetail = userService.loadUserByUsername(login);

                final String accessToken = jwtProvider.generateAccessToken(userDetail);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDetail);
                refreshStorage.put(userDetail.getUsername(), newRefreshToken);
                return new JwtAuthResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }


    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
