package ru.spshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO with a token for the user.
 */
@Data
@AllArgsConstructor
public class JwtRegisterResponse {

    /**
     * Token to the user.
     */
    private String token;
}
