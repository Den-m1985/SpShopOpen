package ru.spshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spshop.dto.ChangeUserRoleDto;
import ru.spshop.dto.UserBlockDto;
import ru.spshop.service.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/block")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> blockUser(@RequestBody UserBlockDto email) {
        userService.blockUser(email);
        return ResponseEntity.ok("User blocked: " + email);
    }

    @PostMapping("/unblock")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> unblockUser(@RequestBody UserBlockDto email) {
        userService.unblockUser(email);
        return ResponseEntity.ok("User unblocked: " + email);
    }

    @PostMapping("/changerole")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<String> changeRole(@RequestBody ChangeUserRoleDto changeUserRoleDto) {
        userService.changeUserRole(changeUserRoleDto);
        return ResponseEntity.ok("Changed role user: " + changeUserRoleDto.email());
    }

}
