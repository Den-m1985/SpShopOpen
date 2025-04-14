package ru.spshop.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.dto.UserBlockDto;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceBlockTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleSeeder roleSeeder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String email = "locked@example.com";
    private final String wrongEmail = "notfound@example.com";


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        Role role = roleSeeder.findRoleByName(RoleEnum.USER);
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(role);
        user.setLocked(false);
        userRepository.save(user);
    }

    @Test
    void shouldBlockUser() {
        UserBlockDto userBlockDto = new UserBlockDto(email);
        userService.blockUser(userBlockDto);
        User blockedUser = userService.getUserByEmail(email);
        assertTrue(blockedUser.getLocked(), "User should be locked");
    }

    @Test
    void shouldUnblockUser() {
        UserBlockDto userBlockDto = new UserBlockDto(email);
        // сначала блокируем
        userService.blockUser(userBlockDto);
        User blockedUser = userService.getUserByEmail(email);
        assertTrue(blockedUser.getLocked());

        // затем разблокируем
        userService.unblockUser(userBlockDto);
        User unblockedUser = userService.getUserByEmail(email);
        assertFalse(unblockedUser.getLocked(), "User should be unlocked");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnBlock() {
        UserBlockDto userBlockDto = new UserBlockDto(wrongEmail);
        assertThrows(EntityNotFoundException.class, () -> userService.blockUser(userBlockDto));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnUnblock() {
        UserBlockDto userBlockDto = new UserBlockDto(wrongEmail);
        assertThrows(EntityNotFoundException.class, () -> userService.unblockUser(userBlockDto));
    }
}
