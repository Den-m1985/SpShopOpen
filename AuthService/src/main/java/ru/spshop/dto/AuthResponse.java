package ru.spshop.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Integer userId
) {
}
