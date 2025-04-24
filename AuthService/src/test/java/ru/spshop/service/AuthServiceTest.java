package ru.spshop.service;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.dto.JwtAuthResponse;
import ru.spshop.dto.UserDTO;
import ru.spshop.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterService registerService;

    private final HttpServletResponse servletResponse = mock(HttpServletResponse.class);

    private final String email = "john.doe@example.com";
    private final String password = "password";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserDTO request = new UserDTO(email, password);
        registerService.registerUser(request, servletResponse);
    }

    @Test
    public void shouldAuthenticateAndReturnTokens() {
        UserDTO loginDto = new UserDTO(email, password);
        JwtAuthResponse response = authService.login(loginDto, servletResponse);
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNull(response.getRefreshToken());
    }

    @Test
    public void shouldThrowExceptionWithWrongPassword() {
        UserDTO loginDto = new UserDTO(email, "wrongPassword");
        assertThrows(RuntimeException.class, () -> authService.login(loginDto, servletResponse));
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExist() {
        UserDTO loginDto = new UserDTO("nonexistent@example.com", "any");
        assertThrows(RuntimeException.class, () -> authService.login(loginDto, servletResponse));
    }
}
