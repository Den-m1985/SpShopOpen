package ru.spshop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.dto.ChangeUserRoleDto;
import ru.spshop.dto.UserBlockDto;
import ru.spshop.dto.UserInfoDto;
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

    public void blockUser(UserBlockDto userEmail) {
        User user = getUserByEmail(userEmail.email());
        user.setLocked(true);
        userRepository.save(user);
    }

    public void unblockUser(UserBlockDto userEmail) {
        User user = getUserByEmail(userEmail.email());
        user.setLocked(false);
        userRepository.save(user);
    }

    public UserInfoDto getUserInfo(String email) {
        User user = getUserByEmail(email);
        return new UserInfoDto(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender(),
                user.getRole().getName().toString()
        );
    }
}