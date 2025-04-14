package ru.spshop.dto;

public record ChangeUserRoleDto(
        String email,
        String role
) {
}
