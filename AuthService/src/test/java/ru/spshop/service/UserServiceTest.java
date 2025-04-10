package ru.spshop.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.dto.ChangeUserRoleDto;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.UserRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleSeeder roleSeeder;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Value("${supper_admin.email}")
    private String supperAdminEmail;
    @Value("${supper_admin.password}")
    private String supperAdminPassword;

    private User user;

    private final String email = "john.doe@example.com";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        Role role = roleSeeder.findRoleByName(RoleEnum.USER);
        user = new User();
        user.setFirstName("First name");
        user.setMiddleName("Middle name");
        user.setLastName("Last name");
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode("password"));
        user = userService.saveUser(user);
    }

    @Test
    public void saveWithoutEmail() {
        userRepository.deleteAll();
        user = null;
        assertThrows(DataIntegrityViolationException.class, () -> userService.saveUser(new User()));
    }

    @Test
    public void shouldReturnUserByEmail() {
        User resultUser = userService.getUserByEmail(email);
        assertEquals(email, resultUser.getEmail());
    }

    @Test
    void testGetAllUsers() {
        List<User> result = userService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnUserById() {
        User resultUser = userService.findUserById(user.getId());
        assertEquals(user.getId(), resultUser.getId());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        assertThatThrownBy(() -> userService.findUserById(user.getId() + 1))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void shouldChangeUserRole() {
        ChangeUserRoleDto dto = new ChangeUserRoleDto(email, RoleEnum.ADMIN.name());
        User updatedUser = userService.changeUserRole(dto);
        assertNotNull(updatedUser);
        assertEquals(RoleEnum.ADMIN, updatedUser.getRole().getName());
    }

    @Test
    public void shouldCreateUserWithSuperAdminRole() {
        Role superAdminRole = roleSeeder.findRoleByName(RoleEnum.SUPER_ADMIN);
        User superAdmin = new User();
        superAdmin.setEmail(supperAdminEmail);
        superAdmin.setPassword(passwordEncoder.encode(supperAdminPassword));
        superAdmin.setRole(superAdminRole);
        User saved = userService.saveUser(superAdmin);

        assertEquals(RoleEnum.SUPER_ADMIN, saved.getRole().getName());
    }
}
