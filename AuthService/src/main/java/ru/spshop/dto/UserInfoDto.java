package ru.spshop.dto;

import ru.spshop.model.enums.Gender;

public record UserInfoDto(
        String firstName,
        String middleName,
        String lastName,
        String email,
        Gender gender,
        String role
) {
}
