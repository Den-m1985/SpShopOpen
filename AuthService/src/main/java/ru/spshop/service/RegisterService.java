package ru.spshop.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.dto.AuthResponse;
import ru.spshop.dto.UserDTO;
import ru.spshop.model.AuthUser;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.service.jwt.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final Logger log = LoggerFactory.getLogger(RegisterService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleSeeder roleSeeder;
    private final JwtService jwtService;
    private final JwtProvider jwtProvider;


    public AuthResponse registerUser(UserDTO request) {
        Role role = roleSeeder.findRoleByName(RoleEnum.USER);
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);

        Optional<User> existingUserByEmail = userService.findUserByEmail(user.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new EntityExistsException("User already exist");
        }

        user = userService.saveUser(user);
        log.info("User registered with id: {}", user.getId());
        AuthUser authUser = new AuthUser(user);
//        String jwtToken = jwtService.generateToken(authUser); // TODO access and refresh tokens need
        final String accessToken = jwtProvider.generateAccessToken(authUser);
        return new AuthResponse(
                accessToken,
                null,
                user.getId()
        );
    }

}
