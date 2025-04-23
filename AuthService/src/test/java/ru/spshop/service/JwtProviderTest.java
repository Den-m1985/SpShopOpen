package ru.spshop.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.model.AuthUser;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.UserRepository;
import ru.spshop.service.jwt.JwtProvider;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class JwtProviderTest {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleSeeder roleSeeder;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private final String email = "john.doe@example.com";
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        Role role = roleSeeder.findRoleByName(RoleEnum.USER);
        User user = new User();
        user.setFirstName("First name");
        user.setMiddleName("Middle name");
        user.setLastName("Last name");
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode("password"));
        user = userService.saveUser(user);
        authUser = new AuthUser(user);
    }

    @Test
    void generateAccessToken_ShouldReturnValidToken() {
        String accessToken = jwtProvider.generateAccessToken(authUser);

        assertNotNull(accessToken);
        assertFalse(accessToken.isEmpty());

        String extractUsername = jwtProvider.extractUsername(accessToken);
        assertEquals(email, extractUsername);

        Date expiration = jwtProvider.extractExpiration(accessToken);
        assertTrue(expiration.after(new Date()), "Токен должен иметь срок действия в будущем");
    }

    @Test
    void generateRefreshToken_ShouldReturnValidToken() {
        String token = jwtProvider.generateRefreshToken(authUser);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String extractUsername = jwtProvider.extractUsername(token);
        assertEquals(email, extractUsername);

        Date expiration = jwtProvider.extractExpiration(token);
        assertTrue(expiration.after(new Date()), "Токен должен иметь срок действия в будущем");
    }

    @Test
    void validateAccessToken_WithValidToken_ShouldReturnTrue() {
        String accessToken = jwtProvider.generateAccessToken(authUser);
        boolean isValid = jwtProvider.validateAccessToken(accessToken);
        assertTrue(isValid);
    }

    @Test
    void validateAccessToken_WithInvalidToken_ShouldReturnFalse() {
        String accessToken = jwtProvider.generateAccessToken(authUser);
        String corruptedToken = accessToken.substring(0, accessToken.length() - 5) + "abcde";
        boolean isValid = jwtProvider.validateAccessToken(corruptedToken);
        assertFalse(isValid);
    }

    @Test
    void validateAccessToken_WithEmptyToken_ShouldReturnFalse() {
        boolean isValid = jwtProvider.validateAccessToken("");
        assertFalse(isValid);
    }

    @Test
    void validateAccessToken_() {
        assertThrows(NullPointerException.class, () -> jwtProvider.parseClaimToken(null));
    }

    @Test
    void validateAccessToken_2() {
        assertThrows(IllegalArgumentException.class, () -> jwtProvider.parseClaimToken(""));
    }

    @Test
    void validateAccessToken_3() {
        String accessToken = jwtProvider.generateAccessToken(authUser);
        String corruptedToken = accessToken.substring(0, accessToken.length() - 5) + "abcde";
        assertThrows(SignatureException.class, () -> jwtProvider.parseClaimToken(corruptedToken));
    }

    @Test
    void validateAccessToken_4() {
        String accessToken = jwtProvider.generateTestToken(authUser);
        try {
            Thread.sleep(1100);
        } catch (InterruptedException ignored) {
        }
        assertThrows(ExpiredJwtException.class, () -> jwtProvider.parseClaimToken(accessToken));
    }
}
