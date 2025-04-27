package ru.spshop.controllers.inrerfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import ru.spshop.dto.JwtAuthResponse;

@Tag(name = "JWT Token Controller")
public interface TokenApi {

    @Operation(
            summary = "Update access token",
            description = "Выдаёт новый access токен по действующему refresh token из cookies"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access токен успешно обновлён"),
            @ApiResponse(responseCode = "401",
                    description = "Refresh токен отсутствует в cookies или пользователь не авторизован",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.springframework.http.ProblemDetail.class)
                    )),
            @ApiResponse(responseCode = "403",
                    description = "Refresh токен невалиден или истёк",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.springframework.http.ProblemDetail.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid Token",
                                            summary = "Недействительный токен",
                                            value = """
                                {
                                    "type": "about:blank",
                                    "title": "Forbidden",
                                    "status": 403,
                                    "detail": "invalid token",
                                    "instance": "/ru-spshop-auth/v1/token/token",
                                    "description": "The JWT signature is invalid"
                                }
                            """
                                    )
                            }
                    ))
    })
    ResponseEntity<JwtAuthResponse> getNewAccessToken(@CookieValue(name = "refreshToken", required = false) String refreshToken);
}
