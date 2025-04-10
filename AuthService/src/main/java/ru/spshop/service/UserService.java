package ru.spshop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.dto.ChangeUserRoleDto;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleSeeder roleSeeder;

    public User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
    }

    public User getUserByEmail(String userEmail) {
        return findUserByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with mail: " + userEmail + " not found"));
    }

    public Optional<User> findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User changeUserRole(ChangeUserRoleDto request) {
        Role role = roleSeeder.findRoleByName(RoleEnum.valueOf(request.role()));
        var user = getUserByEmail(request.email());
        user.setRole(role);
        return userRepository.save(user);
    }

}