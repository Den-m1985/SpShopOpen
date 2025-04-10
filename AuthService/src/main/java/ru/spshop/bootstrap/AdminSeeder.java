package ru.spshop.bootstrap;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.RoleRepository;
import ru.spshop.repositories.UserRepository;
import ru.spshop.service.UserService;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Value("${supper_admin.email}")
    private String supperAdminEmail;
    @Value("${supper_admin.password}")
    private String supperAdminPassword;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    public Role findRoleByName(RoleEnum name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role with name: " + name.name() + " not found"));
    }

    private void createSuperAdministrator() {
        Role role = findRoleByName(RoleEnum.SUPER_ADMIN);
        User user;
        try {
            user = userService.getUserByEmail(supperAdminEmail);
        } catch (EntityNotFoundException e) {
            user = new User();
            user.setFirstName("Super Admin");
            user.setEmail(supperAdminEmail);
            user.setPassword(passwordEncoder.encode(supperAdminPassword));
            user.setRole(role);
            userRepository.save(user);
        }

    }

}
