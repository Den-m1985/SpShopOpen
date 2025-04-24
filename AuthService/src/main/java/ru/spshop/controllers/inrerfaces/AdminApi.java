package ru.spshop.controllers.inrerfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.spshop.dto.ChangeUserRoleDto;
import ru.spshop.dto.UserBlockDto;

@Tag(name = "Admin Controller")
public interface AdminApi {

    @Operation(summary = "User Block")
    ResponseEntity<String> blockUser(@RequestBody UserBlockDto email);

    @Operation(summary = "User Unblock")
    ResponseEntity<String> unblockUser(@RequestBody UserBlockDto email);

    @Operation(summary = "User change role")
    ResponseEntity<String> changeRole(@RequestBody ChangeUserRoleDto changeUserRoleDto);
}
