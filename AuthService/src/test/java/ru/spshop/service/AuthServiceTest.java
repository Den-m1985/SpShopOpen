package ru.spshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.UserDTO;
import ru.spshop.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterService registerService;

    private final String email = "john.doe@example.com";
    private final String password = "password";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserDTO request = new UserDTO(email, password);
        registerService.registerUser(request);
    }

    @Test
    public void shouldAuthenticateAndReturnTokens() {
        UserDTO loginDto = new UserDTO(email, password);
        JwtAuthResponse response = authService.login(loginDto);
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    public void shouldThrowExceptionWithWrongPassword() {
        UserDTO loginDto = new UserDTO(email, "wrongPassword");
        assertThrows(RuntimeException.class, () -> authService.login(loginDto));
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExist() {
        UserDTO loginDto = new UserDTO("nonexistent@example.com", "any");
        assertThrows(RuntimeException.class, () -> authService.login(loginDto));
    }
}
