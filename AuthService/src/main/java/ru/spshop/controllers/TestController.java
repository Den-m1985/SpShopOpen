package ru.spshop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spshop.dto.RefreshJwtRequest;
import ru.spshop.model.User;
import ru.spshop.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {
    final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        log.info("/TestController/getTest");
        return ResponseEntity.ok("Hello test");
    }

    /**
     * Selects all users.
     *
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/istokengood")
    public ResponseEntity<Boolean> isTokenGood(@RequestBody RefreshJwtRequest request) {
        log.info("/TestController/isTokenGood");
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
