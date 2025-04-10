package ru.spshop.service;

import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.dto.AuthResponse;
import ru.spshop.model.User;
import ru.spshop.dto.UserDTO;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private RoleSeeder roleSeeder;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private String email = "john.doe@example.com";
    private String password = "password";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldRegisterNewUserAndReturnJwtToken() {
        UserDTO request = new UserDTO(email, password);
        AuthResponse response = registerService.registerUser(request);

        assertNotNull(response);
        assertNotNull(response.accessToken());
        assertNotNull(response.userId());

        User registeredUser = userRepository.findByEmail(email).orElse(null);
        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.getEmail());
        assertTrue(passwordEncoder.matches(password, registeredUser.getPassword()));
        assertEquals(RoleEnum.USER, registeredUser.getRole().getName());
    }

    @Test
    public void shouldThrowExceptionIfUserAlreadyExists() {
        User existingUser = new User();
        existingUser.setEmail(email);
        existingUser.setPassword(passwordEncoder.encode(password));
        existingUser.setRole(roleSeeder.findRoleByName(RoleEnum.USER));
        userRepository.save(existingUser);

        UserDTO request = new UserDTO(email, password);

        assertThrows(EntityExistsException.class, () -> registerService.registerUser(request));
    }
}
