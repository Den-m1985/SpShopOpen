package ru.spshop.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spshop.dto.JwtRegisterResponse;
import ru.spshop.model.User;
import ru.spshop.model.UserDTO;
import ru.spshop.services.RegisterService;
import ru.spshop.utils.JwtTokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reg")
@Validated
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        if (registerService.saveUser(newUser)) {
            log.info("user register");

            UserDetails userDetails = registerService.loadUserByUsername(newUser.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            //log.info("User: {}", newUser.getUsername());
            //log.info("Token: {}", token);

            // Создаем заголовок с токеном
            //HttpHeaders headers = new HttpHeaders();
            //headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            return ResponseEntity.ok(new JwtRegisterResponse(token));
        } else {
            log.info("BAD");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = registerService.findAll();
        return ResponseEntity.ok(users);
    }

}
